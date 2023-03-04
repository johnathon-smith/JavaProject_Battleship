import java.util.Scanner;

public class Player {

    private String[][] playField; //The playField represents the player's side of the board
    private String[][] fogOfWar; //The fogOfWar represents the enemy's side of the board.
    private String[] shipNames;
    private int[] shipSizes;
    private int numShips;

    private Ship[] ships;

    public Player() {
        this.shipNames = new String[] {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        this.shipSizes = new int[] {5, 4, 3, 3, 2};
        this.numShips = 5;
        this.ships = new Ship[numShips];
        this.playField = new String[][] {
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
        this.fogOfWar = new String[][] {
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
    }

    public String[][] getPlayField() {
        return this.playField;
    }

    public Ship[] getShips() {
        return this.ships;
    }

    public void printPlayField() {
        //print the field of play
        for (int i = 0; i < this.playField[0].length; i++) {
            for (int j = 0; j < this.playField[i].length; j++) {
                System.out.print(this.playField[i][j] + " ");
            }

            System.out.println();
        }
    }

    private void printFogOfWar() {
        //print the field of play
        for (int i = 0; i < this.fogOfWar[0].length; i++) {
            for (int j = 0; j < this.fogOfWar[i].length; j++) {
                System.out.print(this.fogOfWar[i][j] + " ");
            }

            System.out.println();
        }
    }

    public void printGameBoard() {
        printFogOfWar();
        System.out.println("--------------------");
        printPlayField();
    }

    public void placeShips(Scanner scanner) {

        for (int i = 0; i < numShips; i++) {
            this.ships[i] = new Ship(shipNames[i], shipSizes[i]);

            //Now use a while loop to get user input and position the current ship
            boolean shipPlaced = false;
            while (!shipPlaced) {
                System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", shipNames[i], shipSizes[i]);
                //Don't forget to add the carrot to indicate user input
                System.out.print("> ");
                String firstCoordinate = scanner.next();
                String secondCoordinate = scanner.next();

                //Validate user input.
                if (Game.isUserInputValid(firstCoordinate) && Game.isUserInputValid(secondCoordinate)) {
                    //If user input was valid, place coordinates in order from smallest to largest.
                    //Order by row
                    if (firstCoordinate.charAt(0) > secondCoordinate.charAt(0)) {
                        String tempCoordinate = firstCoordinate;
                        firstCoordinate = secondCoordinate;
                        secondCoordinate = tempCoordinate;
                    }
                    //Order by column
                    if (Game.convertStringToInt(firstCoordinate.substring(1)) > Game.convertStringToInt(secondCoordinate.substring(1))) {
                        String tempCoordinate = firstCoordinate;
                        firstCoordinate = secondCoordinate;
                        secondCoordinate = tempCoordinate;
                    }

                    //Validate ship's position. Update ship's coordinates and playField if all is okay
                    if (this.ships[i].isPositionValid(firstCoordinate, secondCoordinate, playField)) {
                        //update ship's coordinates
                        this.ships[i].setCoordinates(firstCoordinate, secondCoordinate);
                        //update playField
                        addShip(firstCoordinate, secondCoordinate, playField);
                        //print updated playField
                        System.out.println();
                        printPlayField();
                        shipPlaced = true;
                    }
                }
            }
        }
    }

    private static void addShip(String firstCoordinate, String secondCoordinate, String[][] playField) {
        //Split the coordinates up into useful parts
        char firstRow = firstCoordinate.charAt(0);
        int firstColumn = Game.convertStringToInt(firstCoordinate.substring(1));
        char secondRow = secondCoordinate.charAt(0);
        int secondColumn = Game.convertStringToInt(secondCoordinate.substring(1));

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

    public boolean takeTurn(Scanner scanner, String[][] enemyField, Ship[] enemyShips) {
        boolean newTarget = false;
        String targetCoordinate;
        int column = 0;
        int rowIndex = 0;
        char row;

        //loop until user input is valid, and they hit a new target
        while(!newTarget) {

            do {
                //Get user input. Don't forget the carrot symbol
                System.out.print("> ");
                targetCoordinate = scanner.next();

            } while (!Game.isUserInputValid(targetCoordinate));

            row = targetCoordinate.charAt(0);
            column = Game.convertStringToInt(targetCoordinate.substring(1));
            rowIndex = row - 'A' + 1;

            //check if the targeted spot has already been hit
            if (!this.fogOfWar[rowIndex][column].equals("~")) {
                System.out.println("\nYou already hit this coordinate! Please try again.");
                continue;
            }

            newTarget = true;
        }

        //loop through each ship and check the targetCoordinate against the ship coordinates
        for (int i = 0; i < enemyShips.length; i++) {
            for (int j = 0; j < enemyShips[i].getSize(); j++) {
                if (enemyShips[i].getCoordinates()[j][0] == rowIndex && enemyShips[i].getCoordinates()[j][1] == column) {

                    //damage ship's health
                    enemyShips[i].damageShip();

                    //update playerFogOfWar and enemyField
                    this.fogOfWar[rowIndex][column] = "X";
                    enemyField[rowIndex][column] = "X";

                    //check if sunk. if so, give message and break from this loop
                    if (enemyShips[i].isSunk()) {
                        //check if all ships have been sunk. If so, leave a game over message and update allShipsSunk
                        if (Game.checkIfAllShipsAreSunk(enemyShips)) {
                            System.out.printf("\nYou sank their last ship, the %s. You won. Congratulations!\n\n", enemyShips[i].getName());
                            //Since all ships are sunk, return true
                            return true;
                        } else {
                            //this ship was sunk, but others are still alive. Leave the standard message
                            System.out.printf("\nYou sank their %s!\n", enemyShips[i].getName());
                        }
                    } else {
                        //Ship was hit, but not sunk
                        System.out.println("\nYou hit a ship!");
                    }

                    //End of turn
                    System.out.println("Pass the move to the other player.");
                    System.out.println("...");
                    System.out.println("Press Enter to continue:");
                    Game.pressEnterToContinue(scanner);
                    return false;
                }
            }
        }

        //Ship was not hit. Update playerFogOfWar and enemyField
        this.fogOfWar[rowIndex][column] = "M";
        enemyField[rowIndex][column] = "M";
        System.out.println("\nYou missed!");
        System.out.println("Pass the move to the other player.");
        System.out.println("...");
        System.out.println("Press Enter to continue:");
        Game.pressEnterToContinue(scanner);
        return false;

    }
}
