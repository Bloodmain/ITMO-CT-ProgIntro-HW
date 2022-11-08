package md2html;
import markup.*;
import utils.MyScannerLite;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

public class Md2Html {
    private static final List<String> FORMATS = List.of("*", "_", "**", "__", "--", "`", "[", "]", "(", ")");
    private static final Predicate<Character> IS_FORMAT = o -> "_*-`[]()".indexOf(o) != -1;
    private static final Predicate<Character> IS_SPEC_HTML_SYMBOL = o -> "\\<>&".indexOf(o) != -1;

    private static int checkForHeader(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != '#' || i > 5) {
                return (line.charAt(i) == ' ' ? i : 0);
            }
        }
        return 0;
    }

    private static String rawText2Html(String line) {
        StringBuilder res = new StringBuilder();
        int lastBound = 0;
        for (int i = 0; i < line.length(); i++) {
            if (Md2Html.IS_SPEC_HTML_SYMBOL.test(line.charAt(i))) {
                res.append(line, lastBound, i);
                switch (line.charAt(i)) {
                    case '>' -> {
                        res.append("&gt;");
                    }
                    case '<' -> {
                        res.append("&lt;");
                    }
                    case '&' -> {
                        res.append("&amp;");
                    }
                }
                lastBound = i + 1;
            }
        }
        res.append(line, lastBound, line.length());
        return res.toString();
    }

    private static String getFormatFrom(String line, int i) {
        String res = null;
        if (i > 0 && line.charAt(i - 1) == '\\') {
            return null;
        }

        if (Md2Html.IS_FORMAT.test(line.charAt(i))) {
            if (line.charAt(i) != '`' && i < line.length() - 1 && line.charAt(i + 1) == line.charAt(i)) {
                res = line.substring(i, i + 2);
            } else if (line.charAt(i) != '-') {
                res = line.substring(i, i + 1);
            }
        }
        return res;
    }

    private static List<Paragraphable> extractElements(String line, int start, int end, int[] formatsIndexes) {
        List<Paragraphable> res = new ArrayList<>();
        int lastBound = start;
        while (start < end) {
            while (start < end && formatsIndexes[start] == -1) {
                start++;
            }
            if (lastBound != start) {
                res.add(new Text(rawText2Html(line.substring(lastBound, start))));
            }

            if (start < end) {
                String format = getFormatFrom(line, start);
                switch (format) {
                    case "*", "_" -> {
                        res.add(new Emphasis(extractElements(line, start + 1,
                                formatsIndexes[start], formatsIndexes)));
                        start = formatsIndexes[start] + 1;
                    }
                    case "**", "__" -> {
                        res.add(new Strong(extractElements(line, start + 2,
                                formatsIndexes[start], formatsIndexes)));
                        start = formatsIndexes[start] + 2;
                    }
                    case "--" -> {
                        res.add(new Strikeout(extractElements(line, start + 2,
                                formatsIndexes[start], formatsIndexes)));
                        start = formatsIndexes[start] + 2;
                    }
                    case "`" -> {
                        res.add(new Code(extractElements(line, start + 1,
                                formatsIndexes[start], formatsIndexes)));
                        start = formatsIndexes[start] + 1;
                    }
                    case "[" -> {
                        int hrefStart = formatsIndexes[start] + 1;
                        if (hrefStart > line.length() || line.charAt(hrefStart) != '(') {
                            formatsIndexes[formatsIndexes[start]] = -1;
                            formatsIndexes[start] = -1;
                            lastBound = start;
                            continue;
                        }
                        String href = line.substring(hrefStart + 1, formatsIndexes[hrefStart]);
                        res.add(new Link(extractElements(line, start + 1,
                                hrefStart - 1, formatsIndexes), href));
                        start = formatsIndexes[hrefStart] + 1;
                    }
                }
            }
            lastBound = start;
        }
        return res;
    }

    private static String getPairedFormat(String format) {
        switch (format) {
            case "[" -> {
                return "]";
            }
            case "(" -> {
                return ")";
            }
            case ")" -> {
                return "(";
            }
            case "]" -> {
                return "[";
            }
            default -> {
                return format;
            }
        }
    }

    private static List<Paragraphable> proceedHighlighting(String line) {
        Map<String, Integer> formatLastIndex = new HashMap<>();
        for (String format : Md2Html.FORMATS) {
            formatLastIndex.put(format, -1);
        }

        int[] indexOfPairFormat = new int[line.length()];
        Arrays.fill(indexOfPairFormat, -1);

        for (int pairClose = 0; pairClose < line.length(); pairClose++) {
            String format = getFormatFrom(line, pairClose);
            if (format != null) {
                if (format.equals("(") && (pairClose == 0 || line.charAt(pairClose - 1) != ']')) {
                    continue;
                }
                int pairOpen = formatLastIndex.get(getPairedFormat(format));
                if (pairOpen != -1) {
                    indexOfPairFormat[pairOpen] = pairClose;
                    indexOfPairFormat[pairClose] = line.length();
                    for (String el : Md2Html.FORMATS) {
                        if (formatLastIndex.get(el) >= pairOpen) {
                            formatLastIndex.put(el, -1);
                        }
                    }
                } else {
                    formatLastIndex.put(format, pairClose);
                }
                if (format.length() > 1) {
                    pairClose++;
                }
            }
        }

        return extractElements(line, 0, line.length(), indexOfPairFormat);
    }
    private static MarkupElement highlight(String line) {
        int headerLevel = checkForHeader(line);
        if (headerLevel > 0) {
            return new Header(proceedHighlighting(line.substring(headerLevel + 1)), headerLevel);
        } else {
            return new Paragraph(proceedHighlighting(line));
        }
    }

    public static void main(String[] args) {
        StringBuilder buffer = new StringBuilder();
        StringBuilder result = new StringBuilder();

        try (MyScannerLite scanner = new MyScannerLite(new FileInputStream(args[0]))) {
            scanner.setCorrectLetter(o -> !o.equals('\n') && !o.equals('\r'));

            String token = scanner.next();
            do {
                buffer.append(token);
                token = (scanner.hasNext() ? scanner.next() : null);
                if (token == null || scanner.getTokenSkippedLine() > 1) {
                    highlight(buffer.toString()).toHtml(result);
                    result.append(System.lineSeparator());
                    buffer.setLength(0);
                } else {
                    buffer.append(System.lineSeparator());
                }
            } while (token != null);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("You haven't pass the name of an input file: " + e.getMessage());
            System.exit(-1);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("I/O exception occurred: " + e.getMessage());
            System.exit(-1);
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]),
                StandardCharsets.UTF_8))) {
            writer.write(result.toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("You haven't pass the name of an output file: " + e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("I/O exception occurred: " + e.getMessage());
            System.exit(-1);
        }
    }
}
