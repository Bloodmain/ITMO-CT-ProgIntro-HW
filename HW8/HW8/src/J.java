import java.io.*;
import java.util.*;


public class J {
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
            int[][] orig = new int[n][n], adjacence = new int[n][n];

            for (int i = 0; i < n; i++) {
                String token = sc.next();
                for (int j = 0; j < n; j++) {
                    orig[i][j] = Character.getNumericValue(token.charAt(j));
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (orig[i][j] == 1) {
                        adjacence[i][j] = 1;
                        for (int k = j + 1; k < n; k++) {
                            orig[i][k] -= orig[j][k];
                            if (orig[i][k] < 0) {
                                orig[i][k] += 10;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(adjacence[i][j]);
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
