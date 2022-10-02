import java.io.IOException;
import java.util.Arrays;

public class Reverse {

    public static int[] checkAndAmortizeSize(int[] arr, int len) {
        if (len >= arr.length) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] data = new int[2];
        int[] digitsOnLine = new int[2];
        MyScannerLite scanner = new MyScannerLite(System.in);
        int linesNumber = 0;
        int digitsCount = 0;

        try {
            while (scanner.hasNextLine()) {
                digitsOnLine = checkAndAmortizeSize(digitsOnLine, linesNumber);

                MyScannerLite lineScanner = new MyScannerLite(scanner.nextLine());

                while (lineScanner.hasNext()) {
                    data = checkAndAmortizeSize(data, digitsCount);

                    data[digitsCount] = Integer.parseInt(lineScanner.next());
                    digitsOnLine[linesNumber]++;
                    digitsCount++;
                }

                lineScanner.close();
                linesNumber++;
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
