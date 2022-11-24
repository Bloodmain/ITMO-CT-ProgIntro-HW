package game;

public class Game {
    private final Player player1;
    private final Player player2;
    private static final int playersCount = 2;
    private final boolean logging;

    public Game(Player player1, Player player2, boolean logging) {
        this.player1 = player1;
        this.player2 = player2;
        this.logging = logging;
    }

    public int play(Board board) {
        while (true) {
            int moveResult = move(board, player1, 1);
            if (moveResult >= 0) {
                return moveResult;
            }

            int moveResult2 = move(board, player2, 2);
            if (moveResult2 >= 0) {
                return moveResult2;
            }
        }
    }

    private int move(Board board, Player player, int playerNumber) {
        GameStateForPlayer state = board.getGameState(player);
        int oppositePlayer = playersCount + 1 - playerNumber;

        log(String.format("It's player %d 's turn!", playerNumber));
        log(String.format("Current game state:\n%s", state));

        Move move;
        try {
            move = player.move(state);
        } catch (Exception e) {
            log(String.format("Player %d failed to move.\nPlayer %d won!", playerNumber, oppositePlayer));
            return oppositePlayer;
        }

        if (move.getType() == MoveType.SURRENDER) {
            log(String.format("Player %d surrendered.\nPlayer %d won!", playerNumber, oppositePlayer));
            return oppositePlayer;
        }
        log(String.format("%s\n", move));
        Result res = board.makeMove(move, playerNumber == 1 ? Cell.P1 : Cell.P2);
        if (res == Result.UNFINISHED) {
            return -1;
        } else {
            log(String.format("Final game state:\n%s\n", board.getGameState(player)));
            switch (res) {
                case WIN -> {
                    log(String.format("Player %d won!", playerNumber));
                    return playerNumber;
                }
                case LOSE -> {
                    log(String.format("Player %d won!", oppositePlayer));
                    return oppositePlayer;
                }
                case DRAW -> {
                    log("It's a draw.");
                    return 0;
                }
                default -> {
                    return -1;
                }
            }
        }
    }

    private void log(String msg) {
        if (logging) {
            System.out.println(msg);
        }
    }

}
