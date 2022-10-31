import java.io.*;
import java.util.*;


public class H {
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
            int[] a = new int[n];
            int s = 0;
            int mx = -1;
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
                mx = Math.max(mx, a[i]);
                s += a[i];
            }

            int[] prfsum = new int[n + 1];
            for (int i = 1; i < n + 1; i++) {
                prfsum[i] = prfsum[i - 1] + a[i - 1];
            }

            int[] f = new int[s + 1];
            int now = a[0];
            int tri = 1;
            for (int i = 1; i < s + 1; i++) {
                if (i > now) {
                    now += a[tri++];
                }
                f[i] = tri;
            }

            int[] ans = new int[s + 1];
            for (int t = mx; t < s + 1; t++) {
                int transCount = 0;
                int nowQuery = 0;

                while (nowQuery <= s) {
                    nowQuery += t;
                    transCount++;
                    if (nowQuery >= s) {
                        break;
                    }
                    if (f[nowQuery + 1] == f[nowQuery]) {
                        nowQuery = prfsum[f[nowQuery] - 1];
                    }
                }

                ans[t] = transCount;
            }
            
            int q = sc.nextInt();
            for (int i = 0; i < q; i++) {
                int t = sc.nextInt();
                if (t < mx) {
                    System.out.println("Impossible");
                } else {
                    System.out.println(ans[t]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
