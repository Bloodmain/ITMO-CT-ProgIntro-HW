package game;

import java.util.Map;
import java.util.function.Predicate;

public class MnkState implements GameStateForPlayer {
    private final Cell[][] field;
    private final Predicate<Move> moveChecker;

    private final Map<Cell, String> cellMapping = Map.of(
            Cell.P1, Colors.ANSI_RED + "X" + Colors.ANSI_RESET,
            Cell.P2, Colors.ANSI_BLUE + "O" + Colors.ANSI_RESET,
            Cell.P3, Colors.ANSI_GREEN + "-" + Colors.ANSI_RESET,
            Cell.P4, Colors.ANSI_YELLOW + "|" + Colors.ANSI_RESET,
            Cell.E, Colors.ANSI_WHITE + "." + Colors.ANSI_RESET,
            Cell.B, Colors.ANSI_BLACK + "B" + Colors.ANSI_RESET
    );

    public MnkState(final Cell[][] field, final Predicate<Move> moveChecker) {
        this.field = field;
        this.moveChecker = moveChecker;
    }

    @Override
    public boolean isValidMove(Move move) {
        return moveChecker.test(move);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        final int COLUMN_WIDTH = String.valueOf(field[0].length).length() + 1;
        final int ROW_WIDTH = String.valueOf(field.length).length();

        res.append(" ".repeat(ROW_WIDTH + 1));
        for (int i = 1; i < field[0].length + 1; ++i) {
            res.append(i).append(" ".repeat(COLUMN_WIDTH - String.valueOf(i).length()));
        }

        res.append(System.lineSeparator())
                .append(" ".repeat(ROW_WIDTH))
                .append("+")
                .append("-".repeat(field[0].length * COLUMN_WIDTH - 1))
                .append(System.lineSeparator());

        for (int i = 1; i < field.length + 1; ++i) {
            res.append(" ".repeat(ROW_WIDTH - String.valueOf(i).length()))
                    .append(i).append("|");
            for (int j = 1; j < field[i - 1].length + 1; ++j) {
                res.append(cellMapping.get(field[i - 1][j - 1]))
                        .append(" ".repeat(COLUMN_WIDTH - 1));
            }
            res.append(System.lineSeparator());
        }
        return res.toString();
    }
}
