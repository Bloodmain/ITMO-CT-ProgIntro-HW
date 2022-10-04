import java.io.IOException;
import java.util.Arrays;

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

    public static int parseOct(String number) {
        int res = 0;
        int base = 1;
        for (int i = number.length() - 1; i > -1; --i) {
            res += base * Character.getNumericValue(number.charAt(i));
            base *= 8;
        }
        return res;
    }

    public static void main(String[] args) {
        final int START_ARRAY_SIZE = 2;

        int[] data = new int[START_ARRAY_SIZE];
        int[] digitsOnLine = new int[START_ARRAY_SIZE];
        MyScannerLite scanner = new MyScannerLite(System.in);
        scanner.setCorrectLetter(chr -> !Character.isWhitespace(chr));
        int linesNumber = 0;
        int digitsCount = 0;

        try {
            while (scanner.hasNext()) {
                while (scanner.hasNextEmptyLine()) {
                    scanner.next();
                    linesNumber++;
                }
                digitsOnLine = checkAndAmortizeSize(digitsOnLine, linesNumber);
                if (scanner.hasNext()) {
                    data = checkAndAmortizeSize(data, digitsCount);

                    Pair token = scanner.next();
                    if (token.getFirst().toLowerCase().endsWith("o")) {
                        data[digitsCount] = parseOct(token.getFirst().substring(0, token.getFirst().length() - 1));
                    } else {
                        data[digitsCount] = Integer.parseInt(toStandardForm(token.getFirst()));
                    }

                    digitsOnLine[linesNumber]++;
                    digitsCount++;

                    if (token.getSecond()) {
                        linesNumber++;
                    }
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("I/O exception occurred: " + e.getMessage());
        }

        int currentDigitToPrint = digitsCount - 1;
        for (int line = linesNumber - 1; line >= 0; --line) {
            for (int digit = digitsOnLine[line] - 1; digit >= 0; --digit) {
                System.out.print(data[currentDigitToPrint] + " ");
                currentDigitToPrint--;
            }
            System.out.println();
        }
    }
}
