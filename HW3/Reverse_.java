import java.util.Arrays;
import java.util.Scanner;

public class Reverse_ {
    public static void main(String[] args) {
        int[][] data = new int[2][];
        int[] digitsOnLine = new int[2];
        Scanner scanner = new Scanner(System.in);
        int linesNumber = 0;

        while (scanner.hasNextLine()) {
            if (linesNumber >= data.length) {
                data = Arrays.copyOf(data, data.length * 2);
                digitsOnLine = Arrays.copyOf(digitsOnLine, digitsOnLine.length * 2);
            }

            Scanner lineScanner = new Scanner(scanner.nextLine());
            data[linesNumber] = new int[2];

            while (lineScanner.hasNextInt()) {
                if (digitsOnLine[linesNumber] >= data[linesNumber].length) {
                    data[linesNumber] = Arrays.copyOf(data[linesNumber], data[linesNumber].length * 2);
                }

                data[linesNumber][digitsOnLine[linesNumber]] = lineScanner.nextInt();
                digitsOnLine[linesNumber]++;
            }

            lineScanner.close();
            linesNumber++;
        }

        scanner.close();

        for (int line = linesNumber - 1; line >= 0; --line) {
            for (int digit = digitsOnLine[line] - 1; digit >= 0; --digit) {
                System.out.print(data[line][digit] + " ");
            }
            System.out.println();
        }
    }
}
