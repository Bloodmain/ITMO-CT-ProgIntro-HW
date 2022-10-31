import java.io.*;
import java.util.*;


public class I {
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
            int leftX = Integer.MAX_VALUE, rightX = Integer.MIN_VALUE,
                    bottomY = Integer.MAX_VALUE, topY = Integer.MIN_VALUE;

            List<List<Integer>> obs = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int x = sc.nextInt(), y = sc.nextInt(), h = sc.nextInt();
                obs.add(List.of(x, y, h));
                leftX = Math.min(leftX, x - h);
                rightX = Math.max(rightX, x + h);
                bottomY = Math.min(bottomY, y - h);
                topY = Math.max(topY, y + h);
            }

            int cx = (rightX + leftX) / 2, cy = (bottomY + topY) / 2;
            long l = 0L, r = 1000_000_000_000_000_000L;
            while (r - l > 1) {
                long m = (r + l) / 2;

                boolean ok = true;
                for (int i = 0; i < n; i++) {
                    int x1 = obs.get(i).get(0), y1 = obs.get(i).get(1), h = obs.get(i).get(2);
                    if (x1 < cx) {
                        x1 += 2 * (cx - x1);
                    }
                    if (y1 < cy) {
                        y1 += 2 * (cy - y1);
                    }
                    x1 -= cx;
                    y1 -= cy;
                    if (m - Math.max(x1, y1) < h) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
                    r = m;
                } else {
                    l = m;
                }
            }
            System.out.printf("%d %d %d%n", cx, cy, r);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
