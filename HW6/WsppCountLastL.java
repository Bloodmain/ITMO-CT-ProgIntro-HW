import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppCountLastL {
    public static boolean isCorrectSymbol(char letter) {
        return Character.getType(letter) == Character.DASH_PUNCTUATION || letter == '\'' || Character.isLetter(letter);
    }

    public static void main(String[] args) {
        Map<String, IntList> wordsLastIndexesOnLine  = new LinkedHashMap<>();
        Map<String, Integer> wordsAllIndexesCount = new HashMap<>();

        try (MyScannerLite scanner = new MyScannerLite(new FileInputStream(args[0]))) {
            scanner.setCorrectLetter(WsppCountLastL::isCorrectSymbol);
            Map<String, Integer> wordsOnThisLine = new LinkedHashMap<>();
            int wordIndex = 1;

            while (scanner.hasNext()) {
                String token = scanner.next().toLowerCase();
                if (scanner.getTokenSkippedLine() != 0) {
                    wordIndex = 1;
                }

                wordsAllIndexesCount.put(token, wordsAllIndexesCount.getOrDefault(token, 0) + 1);

                wordsOnThisLine.put(token, wordIndex);
                wordIndex++;

                if (!scanner.hasNext() || scanner.getTokenSkippedLine() != 0) {
                    for (Map.Entry<String, Integer> token_data : wordsOnThisLine.entrySet()) {
                        if (wordsLastIndexesOnLine.containsKey(token_data.getKey())) {
                            wordsLastIndexesOnLine.get(token_data.getKey()).add(token_data.getValue());
                        } else {
                            IntList data = new IntList();
                            data.add(token_data.getValue());
                            wordsLastIndexesOnLine.put(token_data.getKey(), data);
                        }
                    }
                    wordsOnThisLine.clear();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception occurred: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You haven't pass the name of an input file: " + e.getMessage());
        }

        List<Map.Entry<String, IntList>> dataList = new ArrayList<>(wordsLastIndexesOnLine.entrySet());
        dataList.sort(Comparator.comparingInt(o -> wordsAllIndexesCount.get(o.getKey())));

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]),
                StandardCharsets.UTF_8))) {

            for (Map.Entry<String, IntList> wordData : dataList) {
                writer.write(
                        String.format("%s %d %s", wordData.getKey(), wordsAllIndexesCount.get(wordData.getKey()),
                                wordData.getValue())
                );
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Output file error: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You haven't pass the name of an output file: " + e.getMessage());
        }
    }
}
