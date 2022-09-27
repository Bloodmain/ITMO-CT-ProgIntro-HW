import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(args[0]),
                "utf-8"
            ));
            
            try {
                String line = reader.readLine();
                while (line != null) {
                    
                    for (int j = 0; j < line.length(); ++j) {
                        if (isWordSymbol(line.charAt(j))) {
                            wordsCounter++;
                            if (wordsCounter >= allWordsRaw.length) {
                                allWordsRaw = Arrays.copyOf(allWordsRaw, allWordsRaw.length * 2);
                            }

                            int startFrom = j;
                            while (j < line.length() - 1 && isWordSymbol(line.charAt(j + 1))) {
                                j++;
                            }

                            allWordsRaw[wordsCounter - 1] = line.substring(startFrom, j + 1).toLowerCase();
                        }
                    }

                    line = reader.readLine();
                }

            } finally {
                reader.close();
            }

        } catch (IOException e) {
            System.out.println("Input file not found: " + e.getMessage());
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
