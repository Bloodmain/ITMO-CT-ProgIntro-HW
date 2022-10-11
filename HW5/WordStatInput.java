import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class WordStatInput {

    public static boolean isWordSymbol(char letter) {
        return Character.getType(letter) == Character.DASH_PUNCTUATION || letter == '\'' || Character.isLetter(letter);
    }

    public static void main(String[] args) {
        String[] allWordsRaw = new String[2];
        int wordsCounter = 0;

        // Reading data

        try (MyScannerLite scanner = new MyScannerLite(new FileInputStream(args[0]))) {

            scanner.setCorrectLetter(WordStatInput::isWordSymbol);
            while (scanner.hasNext()) {
                String token = scanner.next();
                allWordsRaw[wordsCounter] = token;
                wordsCounter++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception occurred: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You haven't pass the name of an input file: " + e.getMessage());
        }

        // Creating array of the indexes of the words and sort it by its words' lexicographically order

        Integer[] indArray = new Integer[wordsCounter];
        for (int i = 0; i < wordsCounter; ++i) {
            indArray[i] = i;
        }

        String[] allWords = Arrays.copyOf(allWordsRaw, wordsCounter);

        Arrays.sort(indArray, 0, wordsCounter, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return allWords[a].compareTo(allWords[b]);
            }
        });

        int[][] uniqueInd = new int[wordsCounter][2];
        int uniqueCounter = 0;

        for (int i = 0; i < wordsCounter; ++i) {
            int wordOccurrences = 1;
            int start = i;

            while (i < wordsCounter - 1 && allWords[indArray[i]].equals(allWords[indArray[i + 1]])) {
                wordOccurrences++;
                i++;
            }

            uniqueInd[uniqueCounter][0] = indArray[start];
            uniqueInd[uniqueCounter][1] = wordOccurrences;
            uniqueCounter++;
        }

        // Sorting indexes by the index of the first occurence ascending

        Arrays.sort(uniqueInd, 0, uniqueCounter, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });

        // Outputting the answer

        try {
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(
                new FileOutputStream(args[1]),
                "utf-8"
            ));

            try {
                for (int i = 0; i < uniqueCounter; ++i) {
                    System.out.println(allWords[uniqueInd[i][0]] + " " + uniqueInd[i][1]);
                    writer.write(allWords[uniqueInd[i][0]] + " " + uniqueInd[i][1]);
                    writer.newLine();
                }

            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Output file error: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You haven't pass the name of an output file: " + e.getMessage());
        }
    }

}
