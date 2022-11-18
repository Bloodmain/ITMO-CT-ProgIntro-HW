import java.util.Arrays;
import java.util.function.Predicate;

public class MnkBoard implements Board {
    private final int n;
    private final int m;
    private final int k;
    private final Cell[][] field;
    private Cell turn;
    private int emptyCells;


    public MnkBoard(int m, int n, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.field = new Cell[n][m];
        for (Cell[] row : this.field) {
            Arrays.fill(row, Cell.E);
        }
        this.turn = Cell.X;
        this.emptyCells = m * n;
    }

    private final Predicate<Move> moveChecker = new Predicate<>() {
        @Override
        public boolean test(Move move) {
            return 0 <= move.getRow() && move.getRow() < field.length &&
                    0 <= move.getCol() && move.getCol() < field[move.getRow()].length &&
                    field[move.getRow()][move.getCol()] == Cell.E;
        }
    };

    @Override
    public GameStateForPlayer getGameState(Player player) {
        return new MnkState(field, moveChecker);
    }

    @Override
    public Result makeMove(Move move) {
        if (!moveChecker.test(move)) {
            return Result.LOSE;
        }

        field[move.getRow()][move.getCol()] = turn;
        int inACol = 1 + countInARowWithDeltas(move, 1, 0) + countInARowWithDeltas(move, -1, 0);
        int inARow = 1 + countInARowWithDeltas(move, 0, 1) + countInARowWithDeltas(move, 0, -1);
        int inAMainDiag = 1 + countInARowWithDeltas(move, 1, 1) + countInARowWithDeltas(move, -1, -1);
        int inASecondaryDiag = 1 + countInARowWithDeltas(move, 1, -1) + countInARowWithDeltas(move, -1, 1);

        if (inACol >= k || inARow >= k || inAMainDiag >= k || inASecondaryDiag >= k) {
            return Result.WIN;
        }

        if (--emptyCells == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNFINISHED;
    }

    private int countInARowWithDeltas(Move move, int di, int dj) {
        int count = 0;
        int i = move.getRow();
        int j = move.getCol();
        int i1 = i + di;
        int j1 = j + dj;

        while (0 <= i1 && i1 < n && 0 <= j1 && j1 < m && field[i1][j1] == field[i][j]) {
            count++;
            i1 += di;
            j1 += dj;
        }

        return count;
    }
}
