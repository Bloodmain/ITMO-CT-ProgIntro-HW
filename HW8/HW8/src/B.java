import java.io.*;
import java.util.*;


public class B {
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
            int n = sc.nextInt();
            int ls = 2147470274;
            double lssin = Math.sin(ls);
            List<Integer> ans = new ArrayList<>();

            for (int i = 0; i < (n + 1) / 2; ++i) {
                for (int x = ls - 1 ;; --x) {
                    double xs = Math.sin(x);
                    if (xs < lssin && xs > lssin - 9e-5 && xs >= 0) {
                        ans.add(x);
                        ls = x;
                        lssin = xs;
                        break;
                    }
                }
            }
            for (int el : ans) {
                System.out.println(el);
            }
            for (int i = n / 2 - 1; i >= 0; --i) {
                System.out.println(-ans.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
