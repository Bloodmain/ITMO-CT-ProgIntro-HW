import java.io.IOException;
import java.util.Arrays;

public class ReverseAvg {

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
        int maxColumn = 0;

        // Считываем данные и запоминаем кол-во чисел на строках
        try {

            while (scanner.hasNextLine()) {
                digitsOnLine = checkAndAmortizeSize(digitsOnLine, linesNumber);

                MyScannerLite lineScanner = new MyScannerLite(scanner.nextLine());
                int column = 0;

                while (lineScanner.hasNextInt()) {
                    data = checkAndAmortizeSize(data, digitsCount);

                    data[digitsCount] = lineScanner.nextInt();
                    digitsOnLine[linesNumber]++;
                    digitsCount++;
                    column++;
                }

                maxColumn = Integer.max(maxColumn, column);

                lineScanner.close();
                linesNumber++;
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("I/O exception occurred: " + e.getMessage());
        }



        int nowDigit = 0;
        long[] sumOnCol = new long[maxColumn];
        int[] digitsOnColCount = new int[maxColumn];

        // Считаем сумму и кол-во чисел в столбцах
        for (int i = 0; i < linesNumber; i++) {
            for (int j = 0; j < digitsOnLine[i]; j++) {
                sumOnCol[j] += data[nowDigit];
                nowDigit++;
                digitsOnColCount[j]++;
            }
        }
        
        nowDigit = 0;
        for (int i = 0; i < linesNumber; i++) {
            // Считаем сумму на этой строке
            long sumOnThisLine = 0;
            for (int j = 0; j < digitsOnLine[i]; ++j) {
                sumOnThisLine += data[nowDigit];
                nowDigit++;
            }
            nowDigit -= digitsOnLine[i];

            // Выводим ответ
            for (int j = 0; j < digitsOnLine[i]; ++j) {
                System.out.print((sumOnThisLine + sumOnCol[j] - data[nowDigit]) / (digitsOnLine[i] + digitsOnColCount[j] - 1) + " ");
                nowDigit++;
            }
            System.out.println();
        }
    }
}
