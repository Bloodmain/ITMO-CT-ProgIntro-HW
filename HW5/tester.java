import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class tester {
    public static void main(String[] args) {
        try {
            MyScanner s = new MyScanner("input.txt", "utf-8");

            try {
                int i = 1;
                while (s.hasNextLine()) {
                    System.out.println(i + ") " + s.nextLine());
                    i++;
                }
            } catch (IOException | IllegalStateException | NoSuchElementException e) {
                System.out.println(e.getMessage());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        Scanner s = new Scanner("\n\n\n12");
//        System.out.println(s.hasNextInt());
//        System.out.println(s.nextLine());
    }

}
