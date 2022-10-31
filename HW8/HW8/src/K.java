import java.io.*;
import java.util.*;


public class K {
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

    private static int getLettersCount(int x, int y, int wLeft, int wRight, int hTop, int hBottom, int[][] pref) {
        int x1 = x - wLeft + 1, x2 = x + wRight - 1;
        int y1 = y - hBottom + 1, y2 = y + hTop - 1;
        return pref[y2 + 1][x2 + 1] - pref[y1][x2 + 1] - pref[y2 + 1][x1] + pref[y1][x1];
    }

    private static int getPart(int y, int y1, int y2) {
        if (y < y1) {
            return 1;
        } else if (y > y2) {
            return 2;
        } else {
            return 3;
        }
    }

    private static int binaryHeight(int n, int c1, int c2, int w1, int w2, int[][] pref, int type) {
        int l = 1, r = (type == 1 ? c2 + 2 : n - c2 + 1);
        while (r - l > 1) {
            int mid = (r + l) / 2;
            if (getLettersCount(c1, c2, w1, w2, (type == 1 ? 1 : mid), (type == 1 ? mid : 1), pref) == 1) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return l;
    }

    public static void main(String[] args) {
        try {

            OlympScanner sc = new OlympScanner();
            int n = sc.nextInt(), m = sc.nextInt();
            char[][] kingdom = new char[n][m];
            int[][] letters = new int[26][];
            for (int i = 0; i < n; i++) {
                String line = sc.next();
                for (int j = 0; j < m; j++) {
                    kingdom[i][j] = line.charAt(j);
                    if (kingdom[i][j] != '.') {
                        letters[kingdom[i][j] - 'A'] = new int[]{i, j};
                    }
                }
            }

            int[][] pref = new int[n + 1][m + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    pref[i + 1][j + 1] = pref[i][j + 1] + pref[i + 1][j] - pref[i][j];
                    if (kingdom[i][j] != '.') {
                        pref[i + 1][j + 1]++;
                    }
                }
            }

            int[] maxRectParams = new int[5];
            int aX = letters[0][1], aY = letters[0][0];
            for (int wRight = 1; wRight < m - aX + 1; wRight++) {
                for (int wLeft = 1; wLeft < aX + 2; wLeft++) {
                    int leftBound = aX - wLeft + 1;
                    int rightBound = aX + wRight - 1;

                    int bottomBound = aY + 1 - binaryHeight(n, aX, aY, wLeft, wRight, pref, 1);
                    int topBound = aY - 1 + binaryHeight(n, aX, aY, wLeft, wRight, pref, 2);

                    int square = (rightBound - leftBound + 1) * (topBound - bottomBound + 1);
                    if (getLettersCount(aX, aY, wLeft, wRight,
                            topBound - aY + 1, aY - bottomBound + 1, pref) == 1 &&
                            square > maxRectParams[0]) {
                        maxRectParams = new int[]{square, leftBound, rightBound, bottomBound, topBound};
                    }
                }
            }

            for (int i = maxRectParams[3]; i < maxRectParams[4] + 1; i++) {
                for (int j = maxRectParams[1]; j < maxRectParams[2] + 1; j++) {
                    if (kingdom[i][j] == '.') {
                        kingdom[i][j] = 'a';
                    }
                }
            }

            for (int letter = 0; letter < 26; letter++) {
                if (letters[letter] != null) {

                    int i = letters[letter][0];
                    int j = letters[letter][1];
                    int i1 = i, i2 = i;
                    while (i1 > 0 && kingdom[i1 - 1][j] == '.' && getPart(i, maxRectParams[3], maxRectParams[4]) ==
                            getPart(i1 - 1, maxRectParams[3], maxRectParams[4])) {
                        i1--;
                    }
                    while (i2 < n - 1 && kingdom[i2 + 1][j] == '.' && getPart(i, maxRectParams[3], maxRectParams[4]) ==
                            getPart(i2 + 1, maxRectParams[3], maxRectParams[4])) {
                        i2++;
                    }

                    for (int k = i1; k < i2 + 1; k++) {
                        kingdom[k][j] = (char) ('a' + letter);
                    }
                }
            }

            char[] firstLetter = new char[n];
            char[] lastLetter = new char[n];

            for (int i = 0; i < n; i++) {
                char ls = '.';
                int dJ = 1;
                int j = 0;
                while ((dJ == 1 && j < m) || (dJ == -1 && j >= 0)) {
                    if (kingdom[i][j] == 'a' || kingdom[i][j] == 'A') {
                        if (dJ == -1) {
                            break;
                        }
                        dJ = -1;
                        j = m - 1;
                        ls = '.';
                    }
                    if (kingdom[i][j] != '.') {
                        if (ls == '.') {
                            (dJ == 1 ? firstLetter : lastLetter)[i] = kingdom[i][j];
                        }
                        ls = kingdom[i][j];
                    } else {
                        if (ls != '.') {
                            kingdom[i][j] = ls;
                        }
                    }
                    j += dJ;
                }
            }

            for (int letter = 0; letter < 26; letter++) {
                if (letters[letter] != null) {
                    kingdom[letters[letter][0]][letters[letter][1]] = (char) (letter + 'A');
                }
            }

            StringBuilder out = new StringBuilder();

            for (int i = 0; i < n; i++) {
                boolean letterOccurred = false;
                for (int j = 0; j < m; j++) {
                    if (kingdom[i][j] == '.') {
                        if (letterOccurred) {
                            out.append(lastLetter[i]);
                        } else {
                            out.append(firstLetter[i]);
                        }
                    } else {
                        letterOccurred = true;
                        out.append(kingdom[i][j]);
                    }
                }
                out.append(System.lineSeparator());
            }
            System.out.println(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
