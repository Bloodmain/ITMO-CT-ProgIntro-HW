import java.io.*;
import java.util.StringTokenizer;



public class A {
    static class OlympScanner {
        BufferedReader reader;
        StringTokenizer tokenizer;

        OlympScanner() {
            this.reader = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            if (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) {
        try {

            OlympScanner sc = new OlympScanner();
            int a = sc.nextInt(), b = sc.nextInt(), n = sc.nextInt();
            System.out.println(((n - a - 1) / (b - a)) * 2 + 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
