package game;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerGame {
    private final List<Player> players;
    private final int[] lostPlayers;
    private final boolean logging;
    private final Cell[] playersPictures;
    private int alivePlayers;

    public MultiplayerGame(List<Player> players, boolean logging) {
        this.players = new ArrayList<>(players);
        this.lostPlayers = new int[players.size()];
        this.logging = logging;
        this.playersPictures = new Cell[players.size()];
        this.alivePlayers = players.size();

        for (int i = 0; i < players.size(); ++i) {
            playersPictures[i] = Cell.valueOf("P" + (i + 1));
        }
    }

    public int play(Board board) {
        int playerIndex = 0;
        while (true) {
            if (lostPlayers[playerIndex] == 0) {
                if (alivePlayers == 1) {
                    log(String.format("Player %d won!", playerIndex + 1));
                    return playerIndex + 1;
                }

                MoveResult moveResult = move(board, players.get(playerIndex), playerIndex + 1);

                switch (moveResult) {
                    case PLAYER_LOST -> {
                        lostPlayers[playerIndex] = 1;
                        alivePlayers--;
                    }
                    case PLAYER_WIN -> {
                        return playerIndex + 1;
                    }
                    case DRAW -> {
                        return 0;
                    }
                }

                if (alivePlayers == 0) {
                    log("It's a draw.");
                    return 0;
                }
            }

            playerIndex = (playerIndex + 1) % players.size();
        }
    }

    private MoveResult move(Board board, Player player, int playerNumber) {
        GameStateForPlayer state = board.getGameState(player);
        log(String.format("It's player %d 's turn!", playerNumber));
        log(String.format("Current game state:\n%s", state));

        Move move;
        try {
            move = player.move(state);
        } catch (Exception e) {
            log(String.format("Player %d failed to move.", playerNumber));
            return MoveResult.PLAYER_LOST;
        }

        if (move.getType() == MoveType.SURRENDER) {
            log(String.format("Player %d surrendered.", playerNumber));
            return MoveResult.PLAYER_LOST;
        }
        log(String.format("%s\n", move));
        Result res = board.makeMove(move, playersPictures[playerNumber - 1]);
        if (res == Result.UNFINISHED) {
            return MoveResult.CONTINUE;
        } else {
            log(String.format("Final game state:\n%s\n", board.getGameState(player)));
            switch (res) {
                case WIN -> {
                    log(String.format("Player %d won!", playerNumber));
                    return MoveResult.PLAYER_WIN;
                }
                case LOSE -> {
                    log(String.format("Player %d loose!", playerNumber));
                    return MoveResult.PLAYER_LOST;
                }
                case DRAW -> {
                    log("It's a draw.");
                    return MoveResult.DRAW;
                }
                default -> {
                    return MoveResult.CONTINUE;
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
