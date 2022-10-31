import java.io.*;
import java.util.*;


public class E {
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
            int n = sc.nextInt(), m = sc.nextInt();
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
            }
            for (int i = 0; i < n - 1; i++) {
                int u = sc.nextInt(), v = sc.nextInt();
                graph.get(--u).add(--v);
                graph.get(v).add(u);
            }

            int[] c = new int[m];
            for (int i = 0; i < m; i++) {
                c[i] = sc.nextInt() - 1;
            }

            int[] marks = new int[n];
            for (int city : c) {
                marks[city] = 1;
            }

            int[] usedOnIteration = new int[n];

            Queue<List<Integer>> q = new LinkedList<>();
            for (int i : c) {
                q.add(List.of(i, 0));
            }

            while (!q.isEmpty()) {
                int u = q.element().get(0);
                int iter = q.element().get(1);
                q.poll();

                for (int v : graph.get(u)) {
                    if (marks[v] == 0 || usedOnIteration[v] == iter + 1) {
                        if (marks[v] == 0) {
                            q.offer(List.of(v, iter + 1));
                        }
                        marks[v] += marks[u];
                        usedOnIteration[v] = iter + 1;
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                if (marks[i] == m) {
                    System.out.println("YES");
                    System.out.println(i + 1);
                    return;
                }
            }

            System.out.println("NO");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
