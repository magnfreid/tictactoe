package Player.Bot;

import Game.Board;
import Game.BoardNavigation.Coordinate;
import Game.BoardNavigation.CoordinatePair;
import Game.BoardNavigation.Line;
import Player.Player;

import java.util.*;

public class Bot extends Player {
    final private Random random;

    public Bot(String name, char marker) {
        super(name, marker);
        this.random = new Random();
    }

    //!!!!!! CAPS println() are for testing purposes only !!!!!!!!


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
            return true;}
        /* else if (smartPositionFound(board)) {
            return true;
        }*/ else {
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
     * line and winning the game, then tries to fill that space.
     * Currently only works if the last, empty spot is at the edge of the game board.
     *
     * @param board The board that is being played.
     * @return Returns true if it finds a place to block and places a marker.
     */
/*    public boolean blockEnemyWin(Board board) {
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
    }*/
    public boolean blockEnemyWin(Board board) {
        int boardSize = board.getBoardSize();
        Analyzer analyzer = new Analyzer(board);
        ArrayList<Line> allLines = analyzer.getAllLines();
        ArrayList<CoordinatePair> coordinatesToBlock = new ArrayList<>();
        for (Line line : allLines) {
            if (Objects.equals(line.type(), "row")) {
                int opponentSequence = 0;
                for (Coordinate coordinate : line.coordinates()) {
                    if (coordinate.getContent() != null && coordinate.getContent() != this) {
                        opponentSequence++;
                        if (opponentSequence >= board.getBoardSize() - 1) {
                            coordinatesToBlock.add(getCoordinatePairRow(coordinate, boardSize));
                        }
                    } else opponentSequence = 0;
                }
            }
            if (Objects.equals(line.type(), "column")) {
                int opponentSequence = 0;
                for (Coordinate coordinate : line.coordinates()) {
                    if (coordinate.getContent() != null && coordinate.getContent() != this) {
                        opponentSequence++;
                        if (opponentSequence >= board.getBoardSize() - 1) {
                            coordinatesToBlock.add(getCoordinatePairColumn(coordinate, boardSize));
                        }
                    } else opponentSequence = 0;
                }
            }
            if (Objects.equals(line.type(), "diagonal")) {
                int opponentSequence = 0;
                for (Coordinate coordinate : line.coordinates()) {
                    if (coordinate.getContent() != null && coordinate.getContent() != this) {
                        opponentSequence++;
                        if (opponentSequence >= board.getBoardSize() - 1) {
                            coordinatesToBlock.add(getCoordinatePairDiagonal(coordinate, boardSize));
                        }
                    } else opponentSequence = 0;
                }
            }
            if (Objects.equals(line.type(), "anti-diagonal")) {
                int opponentSequence = 0;
                for (Coordinate coordinate : line.coordinates()) {
                    if (coordinate.getContent() != null && coordinate.getContent() != this) {
                        opponentSequence++;
                        if (opponentSequence >= board.getBoardSize() - 1) {
                            coordinatesToBlock.add(getCoordinatePairAntiDiagonal(coordinate, boardSize));
                        }
                    } else opponentSequence = 0;
                }
            }
        }
   return tryPlaceMarker(board, coordinatesToBlock);
    }

    /**
     * Checks all directions and collects the best coordinates (with the longest sequence and a clear path)
     * and tries to place marker at the longest sequence possible.
     *
     * @param board The board being played on.
     * @return Returns true if a smart placement is found.
     */
   /* public boolean smartPositionFound(Board board) {
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
                    //TODO check if this is starting from the place I am thinking it does!
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
    }*/

    /**
     * Sorts the coordinates passed to it by sequence length. Tries coordinates with a clear path based on highest sequence length.
     *
     * @param board       The board being played on.
     * @param coordinatePairsToCheck A list of potential coordinates to try.
     * @return Returns true if any of the given coordinates can be placed on the board.
     */
    private boolean tryPlaceMarker(Board board, ArrayList<CoordinatePair> coordinatePairsToCheck) {
        int index = 0;
        coordinatePairsToCheck.sort(Comparator.comparingInt(CoordinatePair::getSequence));
        Collections.reverse(coordinatePairsToCheck);
        for (CoordinatePair cp : coordinatePairsToCheck) {
            if (cp.hasWinPotential()) {
                index++;
                System.out.println("TRY: " + index);
                Coordinate startCoordinate = cp.getCoordinateStart();
                Coordinate endCoordinate = cp.getCoordinateEnd();
                if (isValidPlacement(board, startCoordinate.getX(), startCoordinate.getY()) && startCoordinate.getContent() == null) {
                 startCoordinate.setContent(this);
                    System.out.println(name + " placed a marker on x: " + (startCoordinate.getX() + 1) + " y: " + (startCoordinate.getY() + 1));
                    return true;
                }
                System.out.println("BOT is thinking...");
                if (isValidPlacement(board, endCoordinate.getX(), endCoordinate.getY()) && endCoordinate.getContent() == null) {
                    endCoordinate.setContent(this);
                    System.out.println(name + " placed a marker on x: " + (endCoordinate.getX() + 1) + " y: " + (endCoordinate.getY() + 1));
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


    private CoordinatePair getCoordinatePairRow(Coordinate coordinate, int boardSize) {
        Coordinate coordinateStart = new Coordinate(coordinate.getX() - boardSize, coordinate.getY());
        coordinateStart.setContent(coordinate.getContent());
        Coordinate coordinateEnd = new Coordinate(coordinate.getX() + 1, coordinate.getY());
        coordinateEnd.setContent(coordinate.getContent());
        return new CoordinatePair(coordinateStart, coordinateEnd);
    }

    private CoordinatePair getCoordinatePairColumn(Coordinate coordinate, int boardSize) {
        Coordinate coordinateStart = new Coordinate(coordinate.getX(), coordinate.getY()-boardSize);
        coordinateStart.setContent(coordinate.getContent());
        Coordinate coordinateEnd = new Coordinate(coordinate.getX(), coordinate.getY()+1);
        coordinateEnd.setContent(coordinate.getContent());
        return new CoordinatePair(coordinateStart, coordinateEnd);
    }

    private CoordinatePair getCoordinatePairDiagonal(Coordinate coordinate, int boardSize) {
        Coordinate coordinateStart = new Coordinate(coordinate.getX() - boardSize, coordinate.getY()-boardSize);
        coordinateStart.setContent(coordinate.getContent());
        Coordinate coordinateEnd = new Coordinate(coordinate.getX() + 1, coordinate.getY()+1);
        coordinateEnd.setContent(coordinate.getContent());

        return new CoordinatePair(coordinateStart, coordinateEnd);
    }

    private CoordinatePair getCoordinatePairAntiDiagonal(Coordinate coordinate, int boardSize) {
        Coordinate coordinateStart = new Coordinate(coordinate.getX() - boardSize, coordinate.getY()+boardSize);
        coordinateStart.setContent(coordinate.getContent());
        Coordinate coordinateEnd = new Coordinate(coordinate.getX() + 1, coordinate.getY()-1);
        coordinateEnd.setContent(coordinate.getContent());
        return new CoordinatePair(coordinateStart, coordinateEnd);
    }
}
