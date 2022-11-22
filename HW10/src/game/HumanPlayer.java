package game;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final Scanner in, final PrintStream out) {
        this.out = out;
        this.in = in;
    }

    @Override
    public Move move(GameStateForPlayer gameState) {
        for (int iter = 0; ; iter++) {
            if (iter == 0) {
                out.println("Enter row and column or -1 to surrender:");
            } else {
                out.println("Oh, sorry, incorrect move:(\nTry entering row and column again (or -1 to surrender):");
            }

            if (!in.hasNextInt()) {
                in.nextLine();
                continue;
            }
            int firstInput = in.nextInt();
            if (firstInput == -1) {
                return new Move(-1, -1, MoveType.SURRENDER);
            }
            if (!in.hasNextInt()) {
                in.nextLine();
                continue;
            }
            Move move = new Move(firstInput - 1, in.nextInt() - 1, MoveType.NORMAL);
            in.nextLine();
            if (gameState.isValidMove(move)) {
                return move;
            }
        }
    }
}
