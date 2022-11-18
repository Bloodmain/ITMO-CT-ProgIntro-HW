import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter m, n, k:");
        int m = sc.nextInt();
        int n = sc.nextInt();
        int k = sc.nextInt();
        Game game = new Game(new HumanPlayer(sc), new HumanPlayer(sc), true);
        game.play(new MnkBoard(m, n, k));
    }
}
