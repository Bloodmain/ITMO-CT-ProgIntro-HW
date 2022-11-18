import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;
    HumanPlayer(final Scanner in) {
        this.in = in;
    }

    @Override
    public Move move(GameStateForPlayer gameState, int surrenderCommand) {

        if (!in.hasNextInt()) {
            return new Move(-1, -1, false,false);
        }

        int firstInput = in.nextInt();
        if (firstInput == surrenderCommand) {
            return new Move(-1, -1, true, true);
        }

        if (!in.hasNextInt()) {
            return new Move(-1, -1, false,false);
        }
        int secondInput = in.nextInt();
        return new Move(firstInput - 1, secondInput - 1, false, true);
    }
}
