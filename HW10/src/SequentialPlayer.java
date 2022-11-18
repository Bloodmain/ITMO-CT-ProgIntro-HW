public class SequentialPlayer implements Player {
    @Override
    public Move move(final GameStateForPlayer position) {
        for (int r = 0; r < 30; r++) {
            for (int c = 0; c < 30; c++) {
                final Move move = new Move(r, c, MoveType.NORMAL);
                if (position.isValidMove(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
