package game;

import java.util.List;

public class MnkBoardWithBarriers extends MnkBoard {
    private final int barriersNumber;
    public MnkBoardWithBarriers(int m, int n, int k, List<Move> barriers) {
        super(m, n, k);
        for (Move move : barriers) {
            this.field[move.getRow()][move.getCol()] = Cell.B;
        }
        barriersNumber = barriers.size();
        emptyCells -= barriersNumber;
    }

    @Override
    public void clearBoard() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (field[i][j] != Cell.B) {
                    field[i][j] = Cell.E;
                }
            }
        }
        emptyCells = m * n - barriersNumber;
    }
}
