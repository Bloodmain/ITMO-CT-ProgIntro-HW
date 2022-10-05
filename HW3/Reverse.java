import java.util.Arrays;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        int linesNumber = 0;
        int digitsCount = 0;

        while (scanner.hasNextLine()) {
            digitsOnLine = checkAndAmortizeSize(digitsOnLine, linesNumber);
z
            Scanner lineScanner = new Scanner(scanner.nextLine());

            while (lineScanner.hasNextInt()) {
                data = checkAndAmortizeSize(data, digitsCount);

                data[digitsCount] = lineScanner.nextInt();
                digitsOnLine[linesNumber]++;
                digitsCount++;
            }

            lineScanner.close();
            linesNumber++;
        }

        scanner.close();
        
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
