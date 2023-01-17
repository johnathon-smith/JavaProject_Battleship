import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Create new game
        Game game = new Game();

        //Print the initial field of play
        game.printPlayField();

        //Create scanner to receive user input
        Scanner scanner = new Scanner(System.in);

        //Start game. Allow player to place their ships
        game.placePlayerShips(scanner);

        System.out.println();
        System.out.println("You did it! Stage 1 complete!");


    }

}
