import java.io.IOException;
import java.util.Arrays;

public class Reverse {

    public static int[] checkAndAmortizeSize(int[] arr, int len) {
        if (len >= arr.length) {
            arr = Arrays.copyOf(arr, len * 2);
        }
        return arr;
    }

    public static void main(String[] args) {
        final int START_ARRAY_SIZE = 2;
        int[] data = new int[START_ARRAY_SIZE];
        int[] digitsOnLine = new int[START_ARRAY_SIZE];
        MyScannerLite scanner = new MyScannerLite(System.in);
        scanner.setCorrectLetter(chr -> Character.isDigit(chr) || chr == '-' || chr == '+');
        int linesNumber = 0;
        int digitsCount = 0;

        try {
            while (scanner.hasNext() || scanner.hasNextEmptyLine()) {
                while (scanner.hasNextEmptyLine()) {
                    scanner.skipEmptyLine();
                    linesNumber++;
                }
                digitsOnLine = checkAndAmortizeSize(digitsOnLine, linesNumber);
                if (scanner.hasNext()) {
                    data = checkAndAmortizeSize(data, digitsCount);

                    String token = scanner.next();
                    data[digitsCount] = Integer.parseInt(token);
                    digitsOnLine[linesNumber]++;
                    digitsCount++;

                    if (scanner.wasLastTokenAtEOF()) {
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
