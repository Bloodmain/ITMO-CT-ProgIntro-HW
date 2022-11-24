package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Championship {
    private final List<Player> players;
    private final int[] scores;
    private final Cell[][] gamesResults;
    private final boolean logging;
    private final Map<Cell, String> cellMapping = Map.of(
            Cell.P1, Colors.ANSI_RED + "X" + Colors.ANSI_RESET,
            Cell.P2, Colors.ANSI_BLUE + "O" + Colors.ANSI_RESET,
            Cell.B, Colors.ANSI_BLACK + "D" + Colors.ANSI_RESET,
            Cell.E, Colors.ANSI_WHITE + "." + Colors.ANSI_RESET
    );

    public Championship(List<Player> players, boolean logging) {
        this.players = new ArrayList<>(players);
        this.scores = new int[players.size()];
        this.logging = logging;
        this.gamesResults = new Cell[players.size()][players.size()];
        for (Cell[] row : gamesResults) {
            Arrays.fill(row, Cell.E);
        }
    }

    public void play(Board board) {
        for (int p1 = 0; p1 < players.size(); ++p1) {
            for (int p2 = 0; p2 < players.size(); ++p2) {
                if (p1 != p2) {
                    if (logging) {
                        System.out.printf("\nGame: Player %d (X) vs Player %d (O)\n%n", p1 + 1, p2 + 1);
                    }
                    int res = new Game(players.get(p1), players.get(p2), logging).play(board);
                    switch (res) {
                        case 0 -> {
                            scores[p1]++;
                            scores[p2]++;
                            gamesResults[p1][p2] = Cell.B;
                        }
                        case 1 -> {
                            scores[p1] += 3;
                            gamesResults[p1][p2] = Cell.P1;
                        }
                        case 2 -> {
                            scores[p2] += 3;
                            gamesResults[p1][p2] = Cell.P2;
                        }
                    }
                    board.clearBoard();
                }
            }
        }
    }

    public void printScores() {
        StringBuilder res = new StringBuilder();
        res.append("\nFinal championship results:\n");
        final int COLUMN_WIDTH = String.valueOf(players.size()).length() + 2;
        final int ROW_WIDTH = String.valueOf(players.size()).length() + 1;

        res.append(" ".repeat(ROW_WIDTH + 1));
        for (int i = 1; i < players.size() + 1; ++i) {
            res.append(cellMapping.get(Cell.P2)).append(i)
                    .append(" ".repeat(COLUMN_WIDTH - String.valueOf(i).length() - 1));
        }

        res.append(System.lineSeparator())
                .append(" ".repeat(ROW_WIDTH))
                .append("+")
                .append("-".repeat(players.size() * COLUMN_WIDTH - 1))
                .append(System.lineSeparator());

        for (int i = 0; i < players.size(); ++i) {
            res.append(" ".repeat(ROW_WIDTH - String.valueOf(i + 1).length() - 1))
                    .append(cellMapping.get(Cell.P1)).append(i + 1).append("|");
            for (int j = 0; j < players.size(); ++j) {
                res.append(cellMapping.get(gamesResults[i][j]))
                        .append(" ".repeat(COLUMN_WIDTH - 1));
            }
            res.append(System.lineSeparator());
        }
        res.append("\nScores:");
        for (int playersIndex = 0; playersIndex < players.size(); ++playersIndex) {
            res.append(String.format("\nPlayer %d: %d scores", playersIndex + 1, scores[playersIndex]));
        }
        System.out.println(res);
    }
}
