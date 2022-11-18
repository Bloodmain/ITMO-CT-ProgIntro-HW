public class Move {
    private final int row;
    private final int col;
    private final boolean surrender;
    private final boolean integrity;

    public Move(int row, int col, boolean surrender, boolean integrity) {
        this.row = row;
        this.col = col;
        this.surrender = surrender;
        this.integrity = integrity;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isSurrender() {
        return surrender;
    }

    public boolean isIntegral() {
        return integrity;
    }
}
