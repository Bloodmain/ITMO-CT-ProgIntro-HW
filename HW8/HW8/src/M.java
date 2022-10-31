import java.io.*;
import java.util.*;


public class M {
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
            int t = sc.nextInt();
            while (t-- > 0) {

                int n = sc.nextInt();
                List<Integer> a = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    a.add(sc.nextInt());
                }

                int r = 0;
                HashMap<Integer, Integer> d = new HashMap<>();
                for (int i = 0; i < n; i++) {
                    for (int k = i + 1; k < n; k++) {
                        int s = a.get(k) + a.get(i);
                        r += d.getOrDefault(s, 0);
                        d.put(a.get(k) * 2, d.getOrDefault(a.get(k) * 2, 0) + 1);
                    }
                    d.clear();
                }
                System.out.println(r);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
