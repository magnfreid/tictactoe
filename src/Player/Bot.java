package Player;

import Game.Board;

import java.util.ArrayList;
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

 /*
 * Takes the best coordinates and stores in ArrayList. Checks each coordinate and places it if possible. In order of column -> row -> diagnal -> anti-diagonal.
 * */
    public boolean bestPlacementPositionFound(Board board) {
        int boardSize = board.getBoardSize();
        Object[][] grid = board.getGrid();
        int[] columnCoordinates = new int[4];
        int[] rowCoordinates = new int[4];
        int[] diagonalCoordinates = new int[4];
        int[] antiDiagonalCoordinates = new int[4];
        ArrayList<int[]> allCoordinates = new ArrayList<>();
        int bestInRow = 0;
        //Column
        for (int x = 0; x < boardSize; x++) {
            int markerInRow = 0;
            for (int y = 0; y < boardSize; y++) {
                if (grid[x][y] != this) {
                    markerInRow = 0;
                } else {
                    markerInRow++;
                    if (markerInRow > bestInRow) {
                        System.out.println("Checking: COLUMN");
                        bestInRow = markerInRow;
                        //Coordinates for inserting above and below column
                        columnCoordinates[0] = x;
                        columnCoordinates[1] = y + 1;
                        columnCoordinates[2] = x;
                        columnCoordinates[3] = y - bestInRow;
                        allCoordinates.add(columnCoordinates);
                    }
                }
            }
        }
        //Row
        bestInRow = 0;
        for (int y = 0; y < boardSize; y++) {
            int markerInRow = 0;
            for (int x = 0; x < boardSize; x++) {
                if (grid[x][y] != this) {
                    markerInRow = 0;
                } else {
                    markerInRow++;
                    if (markerInRow > bestInRow) {
                        System.out.println("Checking: ROW");
                        bestInRow = markerInRow;
                        //Coordinates for inserting left and right of row
                        rowCoordinates[0] = x + 1;
                        rowCoordinates[1] = y;
                        rowCoordinates[2] = x - bestInRow;
                        rowCoordinates[3] = y;
                        allCoordinates.add(rowCoordinates);
                    }
                }
            }
        }
        //Diagonal
        bestInRow = 0;
        int markerInRow = 0;
        for (int i = 0; i < boardSize; i++) {
            if (grid[i][i] != this) {
                markerInRow = 0;
            } else {
                markerInRow++;
                if (markerInRow > bestInRow) {
                    System.out.println("Checking: DIAGONAL");
                    bestInRow = markerInRow;
                    //Coordinates for inserting upper right corner and lower left corner of diagonal
                    diagonalCoordinates[0] = i + 1;
                    diagonalCoordinates[1] = i + 1;
                    diagonalCoordinates[2] = i - bestInRow;
                    diagonalCoordinates[3] = i - bestInRow;
                    allCoordinates.add(diagonalCoordinates);
                }
            }
        }
        bestInRow = 0;
        markerInRow = 0;
        for (int i = 0; i < boardSize; i++)
            if (grid[i][boardSize - 1 - i] != this) {
                markerInRow = 0;
            } else {
                markerInRow++;
                if (markerInRow > bestInRow) {
                    System.out.println("Checking: ANTI-DIAGONAL");
                    bestInRow = markerInRow;
                    //Coordinates for inserting upper left corner and lower right corner of diagonal
                    antiDiagonalCoordinates[0] = i - 1;
                    antiDiagonalCoordinates[1] = i + 1;
                    antiDiagonalCoordinates[2] = i + bestInRow;
                    antiDiagonalCoordinates[3] = i + bestInRow;
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

    private boolean tryPlaceMarker(Object[][] grid, ArrayList<int[]> coordinates) {
        int index = 0;
        for (int[] coordinate : coordinates) {
            index++;
            System.out.println("TRY: " + index);
            int x1 = coordinate[0];
            int y1 = coordinate[1];
            int x2 = coordinate[2];
            int y2 = coordinate[3];
            //TODO Debug the try/catches, rarely seems to go to the second one.
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
