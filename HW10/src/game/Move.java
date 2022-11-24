package game;

import java.util.Objects;

public class Move {
    private final int row;
    private final int col;
    private final MoveType type;

    public Move(int row, int col, MoveType type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public MoveType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Move at (row=%d, column=%d)", row + 1, col + 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move move) {
            return move.row == this.row && move.col == this.col && move.type == this.type;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (Integer.hashCode(row) * 17 + Integer.hashCode(col)) * 17 + Objects.hashCode(type);
    }
}
