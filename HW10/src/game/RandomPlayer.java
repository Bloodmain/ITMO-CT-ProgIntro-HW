package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;
    private final int boundRow;
    private final int boundColumn;
    public RandomPlayer(int boundRow, int boundColumn) {
        this.random = new Random();
        this.boundRow = boundRow;
        this.boundColumn = boundColumn;
    }

    @Override
    public Move move(final GameStateForPlayer position) {
        while (true) {
            int r = random.nextInt(boundRow);
            int c = random.nextInt(boundColumn);
            final Move move = new Move(r, c, MoveType.NORMAL);
            if (position.isValidMove(move)) {
                return move;
            }
        }
    }
}
