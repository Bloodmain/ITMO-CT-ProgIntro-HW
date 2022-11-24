package game;

import java.util.*;

public class Main {
    private static int askForInt(final Scanner sc, final int leftBound,
                                 final int rightBound, final String errorMessage) {
        if (!sc.hasNextInt()) {
            System.err.println("Bad input.");
            System.exit(-1);
        }
        int res = sc.nextInt();
        if (!(leftBound <= res && res <= rightBound)) {
            System.err.println(errorMessage);
            System.exit(-1);
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter m, n, k:");
        int m = askForInt(sc, 0, Integer.MAX_VALUE, "Bad parameter.");
        int n = askForInt(sc, 0, Integer.MAX_VALUE, "Bad parameter.");
        int k = askForInt(sc, 0, Integer.MAX_VALUE, "Bad parameter.");

        System.out.println("""
                Enter mode:
                1 - normal game
                2 - championship
                """);
        int mode = askForInt(sc, 1, 2, "Bad mode.");

        System.out.println("""
                Enter board, on which you'll be playing:
                1 - normal board
                2 - board with barriers
                """);
        int boardType = askForInt(sc, 1, 2, "Bad board type.");

        Board board;
        if (boardType == 1) {
            board = new MnkBoard(m, n, k);
        } else {
            System.out.println("""
                    Do you want to enter barriers manually or leave barriers on diagonals (only if board is a square):
                    1 - manually
                    2 - leave on diagonals
                    """);
            int enterBarriers = askForInt(sc, 1, 2, "Bad barriers' input choice.");
            Set<Move> barriers = new HashSet<>();
            if (enterBarriers == 2) {
                if (n != m) {
                    System.err.println("Board is not a square!");
                    System.exit(-1);
                }
                for (int i = 0; i < n; ++i) {
                    barriers.add(new Move(i, i, MoveType.NORMAL));
                    barriers.add(new Move(i, m - i - 1, MoveType.NORMAL));
                }
            } else {
                System.out.println("Enter barriers number:");
                int barriersNumber = askForInt(sc, 0, Integer.MAX_VALUE, "Bad barriers' number.");
                for (int i = 0; i < barriersNumber; ++i) {
                    System.out.println("Enter " + (i + 1) + " barrier's row and column:");
                    int row = askForInt(sc, 0, n, "Bad barriers' coords.") - 1;
                    int col = askForInt(sc, 0, m, "Bad barriers' coords.") - 1;
                    barriers.add(new Move(row, col, MoveType.NORMAL));
                }
            }
            board = new MnkBoardWithBarriers(m, n, k, List.copyOf(barriers));
        }

        System.out.println("Enter number of players:");
        int playersNumber = askForInt(sc, 2, (mode == 1 ? 4 : Integer.MAX_VALUE),
                "Bad players' number.");

        System.out.println("Enter number of human players:");
        int humanNumbers = askForInt(sc, 0, playersNumber,"Bad human players' number.");

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < humanNumbers; ++i) {
            players.add(new HumanPlayer(sc, System.out));
        }
        for (int i = 0; i < playersNumber - humanNumbers; ++i) {
            players.add(new RandomPlayer(n, m));
        }

        System.out.println("""
                Do you want to see logs?
                1 - yes
                2 - no
                """);
        int logging = askForInt(sc, 1, 2,"Bad logging choice.");

        sc.nextLine();
        if (mode == 1) {
            MultiplayerGame game = new MultiplayerGame(players, logging == 1);
            game.play(board);
        } else {
            Championship championship = new Championship(players, logging == 1);
            championship.play(board);
            championship.printScores();
        }

        sc.close();
    }
}
