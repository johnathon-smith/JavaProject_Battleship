import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Create two-dimensional array to represent field of play
        String[][] playField = new String[][]{
                {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
                {"A", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"B", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"C", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"D", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"E", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"F", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"G", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"H", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"I", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                {"J", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"}
        };

        //Print the initial field of play
        printPlayField(playField);

        // Create variables to store the number of ships, the names of the ships, and their sizes
        String[] shipNames = new String[] {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        int[] shipSizes = new int[] {5, 4, 3, 3, 2};
        int numShips = 5;

        //Now loop through the previous arrays and create the Ship objects. Store them in a Ship array
        Ship[] playerShips = new Ship[numShips];

        //Create scanner to receive user input
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < numShips; i++) {
            playerShips[i] = new Ship(shipNames[i], shipSizes[i]);

            //Now use a while loop to get user input and position the current ship
            boolean shipPlaced = false;
            while (!shipPlaced) {
                System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", shipNames[i], shipSizes[i]);
                //Don't forget to add the carrot to indicate user input
                System.out.print("> ");
                String firstCoordinate = scanner.next();
                String secondCoordinate = scanner.next();

                //Validate user input.
                if (isUserInputValid(firstCoordinate) && isUserInputValid(secondCoordinate)) {
                    //If user input was valid, place coordinates in order from smallest to largest.
                    //Order by row
                    if (firstCoordinate.charAt(0) > secondCoordinate.charAt(0)) {
                        String tempCoordinate = firstCoordinate;
                        firstCoordinate = secondCoordinate;
                        secondCoordinate = tempCoordinate;
                    }
                    //Order by column
                    if (convertStringToInt(firstCoordinate.substring(1)) > convertStringToInt(secondCoordinate.substring(1))) {
                        String tempCoordinate = firstCoordinate;
                        firstCoordinate = secondCoordinate;
                        secondCoordinate = tempCoordinate;
                    }

                    //Validate ship's position. Update ship's coordinates and playField if all is okay
                    if (playerShips[i].isPositionValid(firstCoordinate, secondCoordinate, playField)) {
                        //update ship's coordinates
                        playerShips[i].setCoordinates(firstCoordinate, secondCoordinate);
                        //update playField
                        addShip(firstCoordinate, secondCoordinate, playField);
                        //print updated playField
                        System.out.println();
                        printPlayField(playField);
                        shipPlaced = true;
                    }
                }

            }
        }

        System.out.println();
        System.out.println("You did it! Stage 1 complete!");


    }

    public static void printPlayField(String[][] playField) {
        //print the field of play
        for (int i = 0; i < playField[0].length; i++) {
            for (int j = 0; j < playField[i].length; j++) {
                System.out.print(playField[i][j] + " ");
            }

            System.out.println();
        }
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

    public static void addShip(String firstCoordinate, String secondCoordinate, String[][] playField) {
        //Split the coordinates up into useful parts
        char firstRow = firstCoordinate.charAt(0);
        int firstColumn = convertStringToInt(firstCoordinate.substring(1));
        char secondRow = secondCoordinate.charAt(0);
        int secondColumn = convertStringToInt(secondCoordinate.substring(1));

        //Check if the coordinates are in the same row
        if (firstRow == secondRow && firstColumn != secondColumn) {
            //Update the playField with the column numbers given
            for (int i = 0; i < playField[0].length; i++) {
                if (playField[i][0].charAt(0) == firstRow) {
                    for (int j = firstColumn; j <= secondColumn; j++) {
                        playField[i][j] = "O";
                    }
                    break;
                }
            }
        } else if (firstRow != secondRow && firstColumn == secondColumn) {
            for (char letter = firstRow; letter <= secondRow; letter++) {
                for (int i = 0; i < playField[0].length; i++) {
                    if (playField[i][0].charAt(0) == letter) {
                        playField[i][firstColumn] = "O";
                        break;
                    }
                }
            }
        }
    }
}
