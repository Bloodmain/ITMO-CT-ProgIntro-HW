import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ReverseOctAbc {
    public static int[] checkAndAmortizeSize(int[] arr, int len) {
        if (len >= arr.length) {
            arr = Arrays.copyOf(arr, len * 2);
        }
        return arr;
    }

    public static String toStandardForm(String number) {
        char[] res = new char[number.length()];
        for (int i = 0; i < number.length(); ++i) {
            char letter = number.charAt(i);
            if (Character.isLetter(letter)) {
                res[i] = (char) (Character.toLowerCase(letter) - 'a' + '0');
            } else {
                res[i] = letter;
            }
        }

        return String.valueOf(res);
    }

    public static void main(String[] args) {
        final int START_ARRAY_SIZE = 2;

        int[] data = new int[START_ARRAY_SIZE];
        int[] numbersOnLine = new int[START_ARRAY_SIZE];
        MyScannerLite scanner = new MyScannerLite(System.in);
        scanner.setCorrectLetter(chr -> !Character.isWhitespace(chr));
        int linesNumber = 0;
        int numbersCount = 0;

        try {
            while (scanner.hasNext()) {
                linesNumber += scanner.getTokenSkippedLine();
                numbersOnLine = checkAndAmortizeSize(numbersOnLine, linesNumber);

                data = checkAndAmortizeSize(data, numbersCount);

                String token = scanner.next().toLowerCase();
                if (token.endsWith("o")) {
                    data[numbersCount] = Integer.parseUnsignedInt(token.substring(0, token.length() - 1), 8);
                } else {
                    data[numbersCount] = Integer.parseInt(toStandardForm(token));
                }

                numbersOnLine[linesNumber]++;
                numbersCount++;
            }

            linesNumber += Integer.max(scanner.getTokenSkippedLine(), 1);
            numbersOnLine = checkAndAmortizeSize(numbersOnLine, linesNumber);
            scanner.close();

        } catch (IllegalStateException e) {
            System.out.println("Trying to use closed scanner: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("The input is exhausted: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception occurred: " + e.getMessage());
        }

        int currentNumberToPrint = numbersCount - 1;
        for (int line = linesNumber - 1; line >= 0; --line) {
            for (int number = numbersOnLine[line] - 1; number >= 0; --number) {
                System.out.print(data[currentNumberToPrint--] + " ");
            }
            System.out.println();
        }
    }
}
