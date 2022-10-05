import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {
    public static boolean isCorrectSymbol(char letter) {
        return Character.getType(letter) == Character.DASH_PUNCTUATION || letter == '\'' || Character.isLetter(letter);
    }

    public static void main(String[] args) {
        Map<String, IntList> words  = new TreeMap<>();
        int wordsCount = 0;

        try (MyScannerLite scanner = new MyScannerLite(new FileInputStream(args[0]))) {
            scanner.setCorrectLetter(Wspp::isCorrectSymbol);

            while (scanner.hasNext()) {
                String token = scanner.next().toLowerCase();
                if (words.containsKey(token)) {
                    words.get(token).add(wordsCount + 1);
                } else {
                    IntList tokenValues = new IntList();
                    tokenValues.add(wordsCount + 1);
                    words.put(token, tokenValues);
                }
                wordsCount++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception occurred: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You haven't pass the name of an input file: " + e.getMessage());
        }

        List<Map.Entry<String, IntList>> dataList = new ArrayList<>(words.entrySet());
        dataList.sort(Comparator.comparingInt(o -> o.getValue().get(0)));

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]),
                StandardCharsets.UTF_8))) {

            for (Map.Entry<String, IntList> wordData : dataList) {
                writer.write(
                        String.format("%s %d %s", wordData.getKey(), wordData.getValue().length(), wordData.getValue())
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
