package game;

public interface Board {
    GameStateForPlayer getGameState(Player player);
    Result makeMove(Move move);
}
