package Player;

import Game.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Bot extends Player {
    final private Random random;

    public Bot(String name, String marker) {
        super(name, marker);
        this.random = new Random();
    }


    /**
     * Loops until bot finds an empty space to place marker.
     *
     * @param board The board that's being played.
     * @return Returns true when complete.
     */
    @Override
    public boolean placeMarkerCheckValid(Board board) {
        if (bestPlacementPositionFound(board)) {
            return true;
        } else {
            while (true) {
                int x = random.nextInt(0, board.getBoardSize());
                int y = random.nextInt(0, board.getBoardSize());
                Object position = board.getGrid()[x][y];
                if (position == null) {
                    board.getGrid()[x][y] = this;
                    System.out.println(name + " placed a marker on x: " + (x + 1) + " y: " + (y + 1));
                    return true;
                }
            }
        }
    }

    /**
     * Stores the coordinates with the best sequence length of each direction
     * @param board The board being played on.
     * @return Returns true if a smart placement is found.
     */
    public boolean bestPlacementPositionFound(Board board) {
        int boardSize = board.getBoardSize();
        Object[][] grid = board.getGrid();
        int[] columnCoordinates = new int[5];
        int[] rowCoordinates = new int[5];
        int[] diagonalCoordinates = new int[5];
        int[] antiDiagonalCoordinates = new int[5];
        ArrayList<int[]> allCoordinates = new ArrayList<>();
        int highestInSequence = 0;
        //Column
        for (int x = 0; x < boardSize; x++) {
            int inSequence = 0;
            for (int y = 0; y < boardSize; y++) {
                if (grid[x][y] != this) {
                    inSequence = 0;
                } else {
                    inSequence++;
                    if (inSequence >= highestInSequence) {
                        System.out.println("Checking: COLUMN");
                        highestInSequence = inSequence;
                        //Coordinates for inserting above and below column
                        columnCoordinates[0] = x;
                        columnCoordinates[1] = y + 1;
                        columnCoordinates[2] = x;
                        columnCoordinates[3] = y - highestInSequence;
                        columnCoordinates[4] = highestInSequence;
                        allCoordinates.add(columnCoordinates);
                    }
                }
            }
        }
        //Row
        highestInSequence = 0;
        for (int y = 0; y < boardSize; y++) {
            int markerInRow = 0;
            for (int x = 0; x < boardSize; x++) {
                if (grid[x][y] != this) {
                    markerInRow = 0;
                } else {
                    markerInRow++;
                    if (markerInRow >= highestInSequence) {
                        System.out.println("Checking: ROW");
                        highestInSequence = markerInRow;
                        //Coordinates for inserting left and right of row
                        rowCoordinates[0] = x + 1;
                        rowCoordinates[1] = y;
                        rowCoordinates[2] = x - highestInSequence;
                        rowCoordinates[3] = y;
                        rowCoordinates[4] = highestInSequence;
                        allCoordinates.add(rowCoordinates);
                    }
                }
            }
        }
        //Diagonal
        highestInSequence = 0;
        int markerInRow = 0;
        for (int i = 0; i < boardSize; i++) {
            if (grid[i][i] != this) {
                markerInRow = 0;
            } else {
                markerInRow++;
                if (markerInRow >= highestInSequence) {
                    System.out.println("Checking: DIAGONAL");
                    highestInSequence = markerInRow;
                    //Coordinates for inserting upper right corner and lower left corner of diagonal
                    diagonalCoordinates[0] = i + 1;
                    diagonalCoordinates[1] = i + 1;
                    diagonalCoordinates[2] = i - highestInSequence;
                    diagonalCoordinates[3] = i - highestInSequence;
                    diagonalCoordinates[4] = highestInSequence;
                    allCoordinates.add(diagonalCoordinates);
                }
            }
        }
        highestInSequence = 0;
        markerInRow = 0;
        for (int i = 0; i < boardSize; i++)
            if (grid[i][boardSize - 1 - i] != this) {
                markerInRow = 0;
            } else {
                markerInRow++;
                if (markerInRow >= highestInSequence) {
                    System.out.println("Checking: ANTI-DIAGONAL");
                    highestInSequence = markerInRow;
                    //Coordinates for inserting upper left corner and lower right corner of diagonal
                    antiDiagonalCoordinates[0] = i - 1;
                    antiDiagonalCoordinates[1] = i + 1;
                    antiDiagonalCoordinates[2] = i + highestInSequence;
                    antiDiagonalCoordinates[3] = i + highestInSequence;
                    antiDiagonalCoordinates[4] = highestInSequence;
                    allCoordinates.add(antiDiagonalCoordinates);
                }
            }
        if (tryPlaceMarker(grid, allCoordinates)) {
            System.out.println("PLACEMENT FOUND");
            return true;
        } else {
            System.out.println("NO BEST PLACEMENT FOUND");
            return false;
        }
    }

    /**
     * Sorts the coordinates passed to it by sequence length (index 4). Tries the coordinate with the highest sequence first.
     * @param grid The grid being played on.
     * @param coordinates A list of potential coordinates to try.
     * @return Returns true if any of the given coordinates can be placed on the board.
     */
    private boolean tryPlaceMarker(Object[][] grid, ArrayList<int[]> coordinates) {
        int index = 0;
        coordinates.sort(Comparator.comparingInt(arr -> arr[4]));
        Collections.reverse(coordinates);
        for (int[] coordinate : coordinates) {
            index++;
            System.out.println("TRY: " + index);
            int x1 = coordinate[0];
            int y1 = coordinate[1];
            int x2 = coordinate[2];
            int y2 = coordinate[3];
            try {
                if (grid[x1][y1] == null) {
                    grid[coordinate[0]][coordinate[1]] = this;
                    System.out.println(name + " placed a marker on x: " + (x1 + 1) + " y: " + (y1 + 1));
                    return true;
                }
            } catch (Exception e) {
                System.out.println("BOT is thinking...");
            }
            try {
                if (grid[x2][y2] == null) {
                    grid[x2][y2] = this;
                    System.out.println(name + " placed a marker on x: " + (x2 + 1) + " y: " + (y2 + 1));
                    return true;
                }
            } catch (Exception e) {
                System.out.println("BOT is thinking some more...");
            }
        }
        return false;
    }
}
