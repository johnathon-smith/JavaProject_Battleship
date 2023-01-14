public class Ship {
    private String name;
    private int size;
    private int[][] coordinates;
    private boolean sunk;

    public Ship (String name, int size) {
        this.name = name;
        this.size = size;
        this.sunk = false;
        this.coordinates = new int[this.size][2];
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isSunk() {
        return this.sunk;
    }

    public void setSunk() {
        this.sunk = true;
    }

    public void setCoordinates(String firstCoordinate, String secondCoordinate) {
        char firstRow = firstCoordinate.charAt(0);
        int firstColumn = Main.convertStringToInt(firstCoordinate.substring(1));
        int firstRowIndex = firstRow - 'A' + 1;
        char secondRow = secondCoordinate.charAt(0);
        int secondColumn = Main.convertStringToInt(secondCoordinate.substring(1));

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < 2; j++) {
                //Row, then column
                if (j == 0) {
                    this.coordinates[i][j] = firstRowIndex;
                } else {
                    this.coordinates[i][j] = firstColumn;
                }
            }

            //If vertical, increment the row index
            if (firstColumn == secondColumn) {
                firstRowIndex++;
            }
            //If horizontal, increment the column index
            if (firstRow == secondRow) {
                firstColumn++;
            }
        }
    }

    public boolean isPositionValid(String firstCoordinate, String secondCoordinate, String[][] playField) {
        char firstRow = firstCoordinate.charAt(0);
        int firstColumn = Main.convertStringToInt(firstCoordinate.substring(1));
        int firstRowIndex = firstRow - 'A' + 1;
        char secondRow = secondCoordinate.charAt(0);
        int secondColumn = Main.convertStringToInt(secondCoordinate.substring(1));
        int secondRowIndex = secondRow - 'A' + 1;

        //Verify that the coordinates are in either a straight horizontal or vertical line
        if (firstRow != secondRow && firstColumn != secondColumn) {
            System.out.println("Error! Ship must be in a straight horizontal or vertical line. Please try again.");
            return false;
        }

        //Verify that the coordinates represent the correct size of the current ship
        //For vertical placement
        if (firstColumn == secondColumn && Math.abs(firstRow - secondRow) + 1 != this.size) {
            System.out.println("Error! Wrong length of the " + this.name + ". Please try again.");
            return false;
        }
        //For horizontal placement
        if (firstRow == secondRow && Math.abs(firstColumn - secondColumn) + 1 != this.size) {
            System.out.println("Error! Wrong length of the " + this.name + ". Please try again.");
            return false;
        }

        //Verify that the coordinates are not directly adjacent to another ship
        //For vertical placement. Don't forget edge cases
        if (firstColumn == secondColumn) {
            if (firstRow != 'A') {
                if (playField[firstRowIndex-1][firstColumn].charAt(0) == 'O') {
                    System.out.println("Error! You placed it too close to another one. Please try again.");
                    return false;
                }
            }

            if (secondRowIndex + 1 < playField.length) {
                if (playField[secondRowIndex + 1][secondColumn].charAt(0) == 'O') {
                    System.out.println("Error! You placed it too close to another one. Please try again.");
                    return false;
                }
            }

            //Now check either side of the ship, if applicable
            for (int i = firstRowIndex; i <= secondRowIndex; i++) {
                if (firstColumn == 1) {
                    if (playField[i][firstColumn + 1].charAt(0) == 'O') {
                        System.out.println("Error! You placed it too close to another one. Please try again.");
                        return false;
                    }
                } else if (firstColumn == playField[0].length - 1) {
                    if (playField[i][firstColumn - 1].charAt(0) == 'O') {
                        System.out.println("Error! You placed it too close to another one. Please try again.");
                        return false;
                    }
                } else {
                    if (playField[i][firstColumn - 1].charAt(0) == 'O' || playField[i][firstColumn + 1].charAt(0) == 'O') {
                        System.out.println("Error! You placed it too close to another one. Please try again.");
                        return false;
                    }
                }
            }
        }

        //For Horizontal Placement. Don't forget edge cases.
        if (firstRow == secondRow) {
            if (firstColumn != 1) {
                if (playField[firstRowIndex][firstColumn - 1].charAt(0) == 'O') {
                    System.out.println("Error! You placed it too close to another one. Please try again.");
                    return false;
                }
            }

            if (secondColumn + 1 < playField[0].length) {
                if (playField[firstRowIndex][secondColumn + 1].charAt(0) == 'O') {
                    System.out.println("Error! You placed it too close to another one. Please try again.");
                    return false;
                }
            }

            //Now check above and below the ship, if applicable
            for (int i = firstColumn; i <= secondColumn; i++) {
                if (firstRow == 'A') {
                    if (playField[firstRowIndex + 1][i].charAt(0) == 'O') {
                        System.out.println("Error! You placed it too close to another one. Please try again.");
                        return false;
                    }
                } else if (firstRowIndex == playField.length - 1) {
                    if (playField[firstRowIndex - 1][i].charAt(0) == 'O') {
                        System.out.println("Error! You placed it too close to another one. Please try again.");
                        return false;
                    }
                } else {
                    if (playField[firstRowIndex - 1][i].charAt(0) == 'O' || playField[firstRowIndex + 1][i].charAt(0) == 'O') {
                        System.out.println("Error! You placed it too close to another one. Please try again.");
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
