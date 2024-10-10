package Player;

import Game.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Bot extends Player {
    final private Random random;

    public Bot(String name, char marker) {
        super(name, marker);
        this.random = new Random();
    }

    //!!!!!! CAPS println() are for testing purposes only !!!!!!!!

    private static class CoordinatePair {
        int x1, y1, x2, y2, sequence;
        boolean hasClearPath;

        public CoordinatePair(int x1, int y1, int x2, int y2, int sequence, boolean hasClearPath) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.sequence = sequence;
            this.hasClearPath = hasClearPath;
        }

        public int getSequence() {
            return sequence;
        }
    }


    /**
     * Loops until bot finds an empty space to place marker.
     * Priority: Try to block opponent from winning > Try to continue building on the longest line > Place at random coordinate.
     *
     * @param board The board that's being played.
     * @return Returns true when complete.
     */
    @Override
    public boolean placeMarkerCheckValid(Board board) {
        if (blockEnemyWin(board)) {
            return true;
        } else if (smartPositionFound(board)) {
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
     * Checks if opponent has a line of adjacent markers that is 1 off from filling the
     * line and winning the game then tries to fill that space.
     * Currently only works if the last, empty spot is at the edge of the game board.
     *
     * @param board The board that is being played.
     * @return Returns true if it finds a place to block and places a marker.
     */
    public boolean blockEnemyWin(Board board) {
        Object[][] grid = board.getGrid();
        int boardSize = board.getBoardSize();
        int opponentColumn;
        int opponentRow;
        ArrayList<CoordinatePair> coordinatesToTry = new ArrayList<>();
        //Rows and Columns
        for (int i = 0; i < boardSize; i++) {
            opponentColumn = 0;
            opponentRow = 0;
            for (int j = 0; j < boardSize; j++) {
                if (grid[i][j] == null || grid[i][j] == this) {
                    opponentColumn = 0;
                } else {
                    opponentColumn++;
                    if (opponentColumn == boardSize - 1) {
                        System.out.println("BLOCKING: COLUMN");
                        coordinatesToTry.add(new CoordinatePair(i, j + 1, i, j - opponentColumn, 0, true));
                    }
                }
                if (grid[j][i] == null || grid[j][i] == this) {
                    opponentRow = 0;
                } else {
                    opponentRow++;
                    if (opponentRow == boardSize - 1) {
                        System.out.println("BLOCKING: ROW");
                        coordinatesToTry.add(new CoordinatePair(j + 1, i, j - opponentRow, i, 0, true));
                    }
                }

            }
        }
        //Diagonal and anti-diagonal
        int opponentDiagonal = 0;
        int opponentAntiDiagonal = 0;
        for (int i = 0; i < boardSize; i++) {
            if (grid[i][i] == null || grid[i][i] == this) {
                opponentDiagonal = 0;
            } else {
                opponentDiagonal++;
                if (opponentDiagonal == boardSize - 1) {
                    System.out.println("BLOCKING: DIAGONAL");
                    coordinatesToTry.add(new CoordinatePair(i + 1, i + 1, i - opponentDiagonal, i - opponentDiagonal, 0, true));
                }
            }
            if (grid[i][boardSize - 1 - i] == null || grid[i][boardSize - 1 - i] == this) {
                opponentAntiDiagonal = 0;
            } else {
                opponentAntiDiagonal++;
                if (opponentAntiDiagonal == boardSize - 1) {
                    System.out.println("BLOCKING: ANTI-DIAGONAL");
                    coordinatesToTry.add(new CoordinatePair(i + 1, i - 1, i - opponentAntiDiagonal, i + opponentAntiDiagonal, 0, true));
                }
            }
        }
        return (tryPlaceMarker(board, coordinatesToTry));
    }

    /**
     * Checks all directions and collects the best coordinates (with the longest sequence and a clear path)
     * and tries to place marker at the longest sequence possible.
     *
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
                if (grid[x][y] != this) {
                    if (grid[x][y] != null) {
                        hasClearPath = false;
                    }
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
                if (grid[x][y] != this) {
                    if (grid[x][y] != null) {
                        hasClearPath = false;
                    }
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
            if (grid[i][i] != this) {
                if (grid[i][i] != null) {
                    hasClearPath = false;
                }
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
                if (grid[i][boardSize - 1 - i] != null) {
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
        if (tryPlaceMarker(board, coordinatesToCheck)) {
            System.out.println("PLACEMENT FOUND");
            return true;
        } else {
            System.out.println("NO BEST PLACEMENT FOUND");
            return false;
        }
    }

    /**
     * Sorts the coordinates passed to it by sequence length. Tries coordinates with a clear path based on highest sequence length.
     *
     * @param board       The board being played on.
     * @param coordinates A list of potential coordinates to try.
     * @return Returns true if any of the given coordinates can be placed on the board.
     */
    private boolean tryPlaceMarker(Board board, ArrayList<CoordinatePair> coordinates) {
        Object[][] grid = board.getGrid();
        int index = 0;
        coordinates.sort(Comparator.comparingInt(CoordinatePair::getSequence));
        Collections.reverse(coordinates);
        for (CoordinatePair cP : coordinates) {
            System.out.println("HAS CLEAR PATH: " + cP.hasClearPath);
            if (cP.hasClearPath) {
                index++;
                System.out.println("TRY: " + index);
                if (isValidPlacement(board, cP.x1, cP.y1) && grid[cP.x1][cP.y1] == null) {
                    grid[cP.x1][cP.y1] = this;
                    System.out.println(name + " placed a marker on x: " + (cP.x1 + 1) + " y: " + (cP.y1 + 1));
                    return true;
                }
                System.out.println("Bot is thinking...");
                if (isValidPlacement(board, cP.x2, cP.y2) && grid[cP.x2][cP.y2] == null) {
                    grid[cP.x2][cP.y2] = this;
                    System.out.println(name + " placed a marker on x: " + (cP.x2 + 1) + " y: " + (cP.y2 + 1));
                    return true;
                }
                System.out.println("BOT is thinking some more...");
            }
        }
        return false;
    }

    private boolean isValidPlacement(Board board, int x, int y) {
        return (x >= 0 && x < board.getBoardSize()) && (y >= 0 && y < board.getBoardSize());
    }
}
