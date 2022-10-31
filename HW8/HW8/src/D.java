import java.io.*;
import java.util.*;


public class D {
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
            int n = sc.nextInt(), k = sc.nextInt();
            final int MOD = 998_244_353;

            long[] kPowers = new long[n + 1];
            kPowers[0] = 1;
            for (int i = 1; i < n + 1; i++) {
                kPowers[i] = kPowers[i - 1] * k % MOD;
            }

            long[] repeatablePalindromes = new long[n + 1];
            for (int i = 1; i < n + 1; i++) {
                if (i % 2 == 0) {
                    repeatablePalindromes[i] += (i / 2) * kPowers[i / 2] * (k + 1);
                    repeatablePalindromes[i] %= MOD;
                } else {
                    repeatablePalindromes[i] = i * kPowers[(i + 1) / 2] % MOD;
                }
            }

            List<List<Integer>> divisors = new ArrayList<>();
            divisors.add(new ArrayList<>());
            divisors.add(new ArrayList<>());
            for (int i = 2; i < n + 1; i++) {
                List<Integer> res = new ArrayList<>(List.of(1));
                int x = 2;
                while (x * x <= i) {
                    if (i % x == 0) {
                        res.add(x);
                        if (i / x != x) {
                            res.add(i / x);
                        }
                    }
                    x++;
                }
                divisors.add(res);
            }

            long[] doublePalindromes = new long[n + 1];
            for (int n1 = 1; n1 < n + 1; n1++) {
                doublePalindromes[n1] = repeatablePalindromes[n1];
                for (int div : divisors.get(n1)) {
                    doublePalindromes[n1] -= (n1 / div) * doublePalindromes[div] % MOD;
                    if (doublePalindromes[n1] < 0) {
                        doublePalindromes[n1] += MOD;
                    }
                }
            }

            long res = 0;
            for (int i = 1; i < n + 1; i++) {
                for (int div : divisors.get(i)) {
                    res += doublePalindromes[div];
                    if (res >= MOD) {
                        res -= MOD;
                    }
                }
                res += doublePalindromes[i];
                if (res >= MOD) {
                    res -= MOD;
                }
            }

            System.out.println(res);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
