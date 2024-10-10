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

    private static class CoordinatePair{
        int x1, y1, x2, y2, sequence;
        boolean hasClearPath;

        public CoordinatePair(int x1, int y1, int x2, int xy, int sequence, boolean hasClearPath) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = xy;
            this.sequence = sequence;
            this.hasClearPath = hasClearPath;
        }

        public int getSequence() {
            return sequence;
        }
    }


    /**
     * Loops until bot finds an empty space to place marker.
     *
     * @param board The board that's being played.
     * @return Returns true when complete.
     */
    @Override
    public boolean placeMarkerCheckValid(Board board) {
        //TODO Add logic to first check if opponent is about to win?
        if (smartPositionFound(board)) {
            return true;
        } else {
            while (true) {
                //TODO Optimize to not try the same random coordinate twice. HashSet?
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
    public boolean smartPositionFound(Board board) {
        int boardSize = board.getBoardSize();
        Object[][] grid = board.getGrid();
        ArrayList<CoordinatePair> coordinatesToCheck = new ArrayList<>();
        int highestInSequence = 0;
        boolean hasClearPath;
        //Column
        for (int x = 0; x < boardSize; x++) {
            int inSequence = 0;
            hasClearPath = true;
            for (int y = 0; y < boardSize; y++) {
                if (grid[x][y] instanceof Human) {
                    hasClearPath = false;
                }
                if (grid[x][y] != this) {
                    inSequence = 0;
                } else {
                    inSequence++;
                    if (inSequence >= highestInSequence) {
                        System.out.println("Checking: COLUMN");
                        highestInSequence = inSequence;
                        //Coordinates for inserting above and below column
                        CoordinatePair coordinatePair = new CoordinatePair(x, y + 1, x, y - highestInSequence, inSequence, hasClearPath);
                        coordinatesToCheck.add(coordinatePair);
                    }
                }
            }
        }
        //Row
        highestInSequence = 0;
        for (int y = 0; y < boardSize; y++) {
            int inSequence = 0;
            hasClearPath = true;
            for (int x = 0; x < boardSize; x++) {
                if (grid[x][y] instanceof Human) {
                    hasClearPath = false;
                }
                if (grid[x][y] != this) {
                    inSequence = 0;
                } else {
                    inSequence++;
                    if (inSequence >= highestInSequence) {
                        System.out.println("Checking: ROW");
                        highestInSequence = inSequence;
                        //Coordinates for inserting left and right of row
                        CoordinatePair coordinatePair = new CoordinatePair(x + 1, y, x - highestInSequence, y, inSequence, hasClearPath);
                        coordinatesToCheck.add(coordinatePair);
                    }
                }
            }
        }
        //Diagonal
        highestInSequence = 0;
        int inSequence = 0;
        hasClearPath = true;
        for (int i = 0; i < boardSize; i++) {
            if (grid[i][i] instanceof Human) {
                hasClearPath = false;
            }
            if (grid[i][i] != this) {
                inSequence = 0;
            } else {
                inSequence++;
                if (inSequence >= highestInSequence) {
                    System.out.println("Checking: DIAGONAL");
                    highestInSequence = inSequence;
                    //Coordinates for inserting upper right corner and lower left corner of diagonal
                    CoordinatePair coordinatePair = new CoordinatePair(i + 1, i + 1, i - inSequence, i - inSequence, inSequence, hasClearPath);
                    coordinatesToCheck.add(coordinatePair);
                }
            }
        }
        //Anti-diagonal
        highestInSequence = 0;
        inSequence = 0;
        hasClearPath = true;
        for (int i = 0; i < boardSize; i++)
            if (grid[i][boardSize - 1 - i] != this) {
                if (grid[i][boardSize-1-i] instanceof Human) {
                    hasClearPath = false;
                }
                inSequence = 0;
            } else {
                inSequence++;
                if (inSequence >= highestInSequence) {
                    System.out.println("Checking: ANTI-DIAGONAL");
                    highestInSequence = inSequence;
                    //Coordinates for inserting upper left corner and lower right corner of diagonal
                    CoordinatePair coordinatePair = new CoordinatePair(i - 1, i + 1, i + inSequence, i + inSequence, inSequence, hasClearPath);
                    coordinatesToCheck.add(coordinatePair);
                }
            }
        if (tryPlaceMarker(grid, coordinatesToCheck)) {
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
    private boolean tryPlaceMarker(Object[][] grid, ArrayList<CoordinatePair> coordinates) {
        int index = 0;
        coordinates.sort(Comparator.comparingInt(CoordinatePair::getSequence));
        Collections.reverse(coordinates);
        for (CoordinatePair cP: coordinates) {
            System.out.println("HAS CLEAR PATH: " + cP.hasClearPath);
            if (cP.hasClearPath) {
                index++;
                System.out.println("TRY: " + index);
                try {
                    if (grid[cP.x1][cP.y1] == null) {
                        grid[cP.x1][cP.y1] = this;
                        System.out.println(name + " placed a marker on x: " + (cP.x1 + 1) + " y: " + (cP.y1 + 1));
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("BOT is thinking...");
                }
                try {
                    if (grid[cP.x2][cP.y2] == null) {
                        grid[cP.x2][cP.y2] = this;
                        System.out.println(name + " placed a marker on x: " + (cP.x2 + 1) + " y: " + (cP.y2 + 1));
                        return true;
                    }
                } catch (Exception e) {
                    System.out.println("BOT is thinking some more...");
                }
            }
        }
        return false;
    }
}
