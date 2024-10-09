package Player;

import Game.Board;

import java.sql.SQLOutput;
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


    public boolean bestPlacementPositionFound(Board board) {
        int boardSize = board.getBoardSize();
        Object[][] grid = board.getGrid();
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        int bestInRow = 0;
        //Column
        for (int x = 0; x < boardSize; x++) {
            int markerInRow = 0;
            for (int y = 0; y < boardSize; y++) {
                if (grid[x][y] != this) {
                    markerInRow = 0;
                } else {
                    markerInRow++;
                    if (markerInRow >= bestInRow) {
                        bestInRow = markerInRow;
                        //Coordinates for inserting above and below column
                        x1 = x;
                        y1 = y + 1;
                        x2 = x;
                        y2 = y - bestInRow;
                    }
                }
            }
        }
        if (tryPlaceMarker(grid, x1, y1, x2, y2)) {
            System.out.println("COLUMN");
            return true;
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
                    if (markerInRow >= bestInRow) {
                        bestInRow = markerInRow;
                        //Coordinates for inserting left and right of row
                        x1 = x + 1;
                        y1 = y;
                        x2 = x - bestInRow;
                        y2 = y;
                    }
                }
            }
        }
        if (tryPlaceMarker(grid, x1, y1, x2, y2)) {
            System.out.println("ROW");
            return true;
        }
        //Diagonal
        bestInRow = 0;
        int markerInRow = 0;
        for (int i = 0; i < boardSize; i++) {
            if (grid[i][i] != this) {
                markerInRow = 0;
            } else {
                markerInRow++;
                if (markerInRow >= bestInRow) {
                    bestInRow = markerInRow;
                    //Coordinates for inserting upper right corner and lower left corner of diagonal
                    x1 = i + 1;
                    y1 = i + 1;
                    x2 = i - bestInRow;
                    y2 = i - bestInRow;
                }
            }
        }
        if (tryPlaceMarker(grid, x1, y1, x2, y2)) {
            System.out.println("DIAGONAL");
            return true;
        }
        bestInRow = 0;
        markerInRow = 0;
        for (int i = 0; i < boardSize; i++)
            if (grid[i][boardSize - 1 - i] != this) {
                markerInRow = 0;
            } else {
                markerInRow++;
                if (markerInRow >= bestInRow) {
                    bestInRow = markerInRow;
                    //Coordinates for inserting upper left corner and lower right corner of diagonal
                    x1 = i - 1;
                    y1 = i + 1;
                    x2 = i + bestInRow;
                    y2 = i + bestInRow;
                }
            }
       if (tryPlaceMarker(grid, x1, y1, x2, y2)) {
           System.out.println("ANTI-DIAGONAL");
           return  true;
       }
       else  {
           System.out.println("NO BEST PLACEMENT FOUND");
           return  false;
       }
    }

    private boolean tryPlaceMarker(Object[][] grid, int x1, int y1, int x2, int y2) {
        try {
            if (grid[x1][y1] == null) {
                grid[x1][y1] = this;
                System.out.println(name + " placed a marker on x: " + (x1 + 1) + " y: " + (y1 + 1));
                return true;
                //TODO BUG: else if does not happen on exception
            } else if (grid[x2][y2] == null) {
                grid[x2][y2] = this;
                System.out.println(name + " placed a marker on x: " + (x2 + 1) + " y: " + (y2 + 1));
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
