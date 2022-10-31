import java.io.*;
import java.util.*;


public class C {
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

    private static class Vertex {
        int i, j;

        Vertex(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Vertex other)) {
                return false;
            }
            return this.i == other.i && this.j == other.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }

        @Override
        public String toString() {
            return String.format("%d %d", j, i);
        }
    }

    private static final List<Map<Vertex, List<Vertex>>> graph = List.of(new HashMap<>(), new HashMap<>());
    private static final List<Vertex> ans = new ArrayList<>();

    static void addEdge(Vertex v, Vertex u, int side) {
        if (!graph.get(side).containsKey(v)) {
            graph.get(side).put(v, new ArrayList<>());
        }
        if (!graph.get(side).containsKey(u)) {
            graph.get(side).put(u, new ArrayList<>());
        }
        graph.get(side).get(v).add(u);
        graph.get(side).get(u).add(v);
    }

    static void findEulerTour(Vertex v, int side) {
        List<Vertex> connectedWith = graph.get(side).get(v);

        while (!connectedWith.isEmpty()) {
            Vertex u = connectedWith.remove(connectedWith.size() - 1);
            graph.get(side).get(u).remove(v);
            findEulerTour(u, 1 - side);
        }

        ans.add(v);
    }

    public static void main(String[] args) {
        try {

            OlympScanner sc = new OlympScanner();
            int w = sc.nextInt(), h = sc.nextInt();
            Vertex lastVertex = null;

            for (int i = 0; i < h; i++) {
                String line = sc.next();
                for (int j = 0; j < w; j++) {
                    if (line.charAt(j) == 'X') {

                        Vertex a1 = new Vertex(i, j),
                                a2 = new Vertex(i, j + 1),
                                a3 = new Vertex(i + 1, j),
                                a4 = new Vertex(i + 1, j + 1);

                        addEdge(a1, a4, 0);
                        addEdge(a2, a3, 0);
                        addEdge(a1, a3, 1);
                        addEdge(a2, a4, 1);
                        lastVertex = a1;
                    }
                }
            }

            findEulerTour(lastVertex, 1);
            ans.remove(ans.size() - 1);
            StringBuilder out = new StringBuilder();
            out.append(ans.size() - 1);
            for (Vertex v : ans) {
                out.append(System.lineSeparator());
                out.append(v);
            }
            System.out.println(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
