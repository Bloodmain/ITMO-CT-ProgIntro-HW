package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final GameStateForPlayer position) {
        while (true) {
            int r = random.nextInt(30);
            int c = random.nextInt(30);
            final Move move = new Move(r, c, MoveType.NORMAL);
            if (position.isValidMove(move)) {
                return move;
            }
        }
    }
}
