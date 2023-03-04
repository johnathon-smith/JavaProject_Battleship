import java.util.Scanner;

public class Game {

    public static void printMenu() {
        System.out.println("\n_________________________\n");
        System.out.println("Welcome to Battleship!\n");
        System.out.println("_________________________\n");
        System.out.println("How would you like to play? Choose an option from the list below:\n");
        System.out.println("1 - Single Player (Easy)");
        System.out.println("2 - Single Player (Hard)");
        System.out.println("3 - Local Multiplayer");
        System.out.println("4 - Online Multiplayer");
    }

    public static boolean isUserInputValid(String coordinate){
        try {
            char Row = coordinate.charAt(0);
            int Column = convertStringToInt(coordinate.substring(1));

            if (coordinate.length() == 1 || coordinate.length() > 3) {
                System.out.println("Error! Invalid input. Please try again.");
                return false;
            } else if (Character.isDigit(coordinate.charAt(0))) {
                System.out.println("Error! Invalid input. Please try again.");
                return false;
            } else if (Character.isLetter(coordinate.charAt(1))) {
                System.out.println("Error! Invalid input. Please try again.");
                return false;
            } else if (coordinate.length() == 3 && Character.isLetter(coordinate.charAt(2))) {
                System.out.println("Error! Invalid input. Please try again.");
                return false;
            } else if (Row < 'A' || Row > 'J') {
                System.out.println("Error! Coordinate row is not valid. Please try again.");
                return false;
            } else if (Column < 1 || Column > 10) {
                System.out.println("Error! Coordinate column is not valid. Please try again.");
                return false;
            } else {
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("Error! Invalid input. Please try again.");
            return false;
        }
    }

    public static int convertStringToInt(String str) {
        int val = 0;
        // Convert the String
        try {
            val = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return val;
        }
        return val;
    }

    public static void pressEnterToContinue(Scanner scanner) {
        try
        {
            System.in.read();
            scanner.nextLine();
        }
        catch(Exception e)
        {}
    }

    public static boolean checkIfAllShipsAreSunk(Ship[] playerShips) {
        for (int i = 0; i < playerShips.length; i++) {
            if (!playerShips[i].isSunk()) {
                return false;
            }
        }

        //all ships are sunk
        return true;
    }

}
