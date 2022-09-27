import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class WordStatInputHash {
    
    public static boolean isWordSymbol(char letter) {
        return Character.getType(letter) == Character.DASH_PUNCTUATION || letter == '\'' || Character.isLetter(letter);
    }

    public static void main(String[] args) {
        String[] allWords = new String[2];
        int wordsCounter = 0;
        int maxLen = 0;

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
                            if (wordsCounter >= allWords.length) {
                                allWords = Arrays.copyOf(allWords, allWords.length * 2);
                            }

                            int startFrom = j;
                            while (j < line.length() - 1 && isWordSymbol(line.charAt(j + 1))) {
                                j++;
                            }

                            allWords[wordsCounter - 1] = line.substring(startFrom, j + 1).toLowerCase();
                            maxLen = Integer.max(maxLen, j - startFrom + 1);
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
            System.out.println("You haven't pass the name of the input file: " + e.getMessage());
        }

        // Computing hash codes

        int[] powers = new int[maxLen];
        powers[0] = 1;

        final int BASE = 104891;
        final int MOD = 1_000_000_000 + 7;
       
        for (int i = 1; i < maxLen; ++i) {
            powers[i] = powers[i - 1] * BASE % MOD;
        }

        int[] hashCodes = new int[wordsCounter];
        for (int i = 0; i < wordsCounter; ++i) {
            int result = 0;

            for (int j = 0; j < allWords[i].length(); ++j) {
                result += allWords[i].charAt(j) * powers[maxLen - j - 1];
                if (result >= MOD) {
                    result -= MOD;
                }
            }
            hashCodes[i] = result;
        }

        // Creating array of the indexes of the words sorted by their hash code 

        Integer[] indArray = new Integer[wordsCounter];
        for (int i = 0; i < wordsCounter; ++i) {
            indArray[i] = i;
        }

        Arrays.sort(indArray, 0, wordsCounter, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return Integer.compare(hashCodes[a], hashCodes[b]);
            }
        });

        // Leaving only indexes of the unique words (leaving the index of the first occurrence)

        int[][] uniqueInd = new int[wordsCounter][2];
        int uniqueCounter = 0;

        for (int i = 0; i < wordsCounter; ++i) {
            int wordOccurrences = 1;
            int start = i;

            while (i < wordsCounter - 1 && hashCodes[indArray[i]] == hashCodes[indArray[i + 1]]) {
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
                    //System.err.println(allWords[uniqueInd[i][0]] + " " + uniqueInd[i][1]);
                    writer.write(allWords[uniqueInd[i][0]] + " " + uniqueInd[i][1]);
                    writer.newLine();
                }

            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Output file error: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You haven't pass the name of the output file: " + e.getMessage());
        }
    }

}
