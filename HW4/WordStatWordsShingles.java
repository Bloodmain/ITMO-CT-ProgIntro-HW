import java.io.*;
import java.lang.Thread.State;
import java.util.Arrays;

public class WordStatWordsShingles {

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
                            int startFrom = j;
                            while (j < line.length() - 1 && isWordSymbol(line.charAt(j + 1))) {
                                j++;
                            }
                            
                            for (int i = 0; i < j - startFrom + 1; ++i) {
                                wordsCounter++;
                                if (wordsCounter >= allWordsRaw.length) {
                                    allWordsRaw = Arrays.copyOf(allWordsRaw, allWordsRaw.length * 2);
                                }

                                if (i + 3 > j - startFrom + 1) {
                                    if (j - startFrom + 1 < 3) {
                                        allWordsRaw[wordsCounter - 1] = line.substring(startFrom + i, j + 1).toLowerCase();
                                    }  else {
                                        wordsCounter--;
                                    }
                                    break;
                                }

                                allWordsRaw[wordsCounter - 1] = line.substring(startFrom + i, startFrom + i + 3).toLowerCase();
                            }
                            
                        }
                    }

                    line = reader.readLine();
                }

            } finally {
                reader.close();
            }

        } catch (FileNotFoundException) {
            System.out.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception occurred: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You haven't pass the name of an input file: " + e.getMessage());
        }

        Arrays.sort(allWordsRaw, 0, wordsCounter);

        String[] uniqueWords = new String[wordsCounter];
        int[] wordsOccurrences = new int[wordsCounter];
        int uniqueCounter = 0;

        for (int i = 0; i < wordsCounter; ++i) {
            int start = i;
            wordsOccurrences[uniqueCounter] = 1;

            while (i < wordsCounter - 1 && allWordsRaw[i].equals(allWordsRaw[i + 1])) {
                wordsOccurrences[uniqueCounter] += 1;
                i++;
            }

            uniqueWords[uniqueCounter] = allWordsRaw[start];
            uniqueCounter++;
        }

        try {
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(
                new FileOutputStream(args[1]),
                "utf-8"
            ));

            try {
                for (int i = 0; i < uniqueCounter; ++i) {
                    //System.out.println(uniqueWords[i] + " " + wordsOccurrences[i]);
                    writer.write(uniqueWords[i] + " " + wordsOccurrences[i]);
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
