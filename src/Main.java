import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Create scanner to receive user input
        Scanner scanner = new Scanner(System.in);

        //print out the main menu
        Game.printMenu();

        //Get player choice
        int playerChoice = scanner.nextInt();

        if (playerChoice == 1) {
            //Start single player game (Easy)
            //Future addition
            return;
        } else if (playerChoice == 2) {
            //Start single player game (Hard)
            //Future addition
            return;
        } else if (playerChoice == 3) {
            //Start local multiplayer game
            //Create both players
            Player playerOne = new Player();
            Player playerTwo = new Player();

            //Place player one ships
            System.out.println("Player One, please place your ships");
            playerOne.printPlayField();
            playerOne.placeShips(scanner);

            //Pass the turn to player 2
            System.out.println("\nPass the move to Player Two.");
            System.out.println("...");
            System.out.println("Player Two, press enter to continue:");
            Game.pressEnterToContinue(scanner);

            //Place player two ships
            System.out.println("Player Two, please place your ships");
            playerTwo.printPlayField();
            playerTwo.placeShips(scanner);

            //Pass the turn to player 1 to start the game
            System.out.println("\nPass the move to Player One.");
            System.out.println("...");
            System.out.println("Player One, press enter to continue:");
            Game.pressEnterToContinue(scanner);

            //Allow the player to enter coordinates to begin attacking ships
            boolean allShipsSunk = false;

            while (!allShipsSunk) {
                //Print player 1 game board and leave player 1 message
                playerOne.printGameBoard();
                //print the player start message
                System.out.println("\nPlayer 1, it's your turn:\n");

                //Let player 1 take their turn
                allShipsSunk = playerOne.takeTurn(scanner, playerTwo.getPlayField(), playerTwo.getShips());

                //If player 1 did not sink all player 2 ships, print player 2 game board and message, and let player 2 take their turn
                if (!allShipsSunk) {
                    //print the game board
                    playerTwo.printGameBoard();
                    //print the player start message
                    System.out.println("\nPlayer 2, it's your turn:\n");
                    allShipsSunk = playerTwo.takeTurn(scanner, playerOne.getPlayField(), playerOne.getShips());
                }
            }
        } else if (playerChoice == 4) {
            //Start online multiplayer game
            //Future addition
            return;
        }


    }

}
