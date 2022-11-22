package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter m, n, k:");
        int m = sc.nextInt();
        int n = sc.nextInt();
        int k = sc.nextInt();
        if (m <= 0 || n <= 0 || k <= 0) {
            throw new IllegalArgumentException("Bad parameters");
        }
        sc.nextLine();
        Game game = new Game(new HumanPlayer(sc, System.out), new HumanPlayer(sc, System.out), true);
        //Game game = new Game(new RandomPlayer(), new RandomPlayer(), true);
        game.play(new MnkBoard(m, n, k));
    }
}
