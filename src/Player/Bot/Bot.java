package Player.Bot;

import Game.Board;
import Game.BoardNavigation.Coordinate;
import Game.BoardNavigation.CoordinatePair;
import Game.BoardNavigation.Line;
import Player.Player;

import java.util.*;

/**
 * A self playing bot!
 */
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
     * Sorts through all lines on the board (provided by Analyzer class). Looks for opponent's markers with a sequence
     * of boardSize-1 (one marker away from winning). Tries to place its marker in the way of opponent.
     *
     * @param board The board being played on.
     * @return Returns true if a blocking marker is placed.
     */
    public boolean blockEnemyWin(Board board) {
        Analyzer analyzer = new Analyzer(board);
        ArrayList<Line> allLines = analyzer.getAllLines();
        ArrayList<CoordinatePair> coordinatesToBlock = new ArrayList<>();
        for (Line line : allLines) {
            if (Objects.equals(line.getType(), "row")) {
                int opponentSequence = 0;
                for (Coordinate coordinate : line.getCoordinates()) {
                    if (coordinate.getContent() != null && coordinate.getContent() != this) {
                        opponentSequence++;
                        if (opponentSequence >= board.getBoardSize() - 1) {
                            System.out.println("ROW BLOCK FOUND");
                            coordinatesToBlock.add(getCoordinatePairRow(coordinate, opponentSequence));
                        }
                    } else opponentSequence = 0;
                }
            }
            if (Objects.equals(line.getType(), "column")) {
                int opponentSequence = 0;
                for (Coordinate coordinate : line.getCoordinates()) {
                    if (coordinate.getContent() != null && coordinate.getContent() != this) {
                        opponentSequence++;
                        if (opponentSequence >= board.getBoardSize() - 1) {
                            System.out.println("COLUMN BLOCK FOUND");
                            coordinatesToBlock.add(getCoordinatePairColumn(coordinate, opponentSequence));
                        }
                    } else opponentSequence = 0;
                }
            }
            if (Objects.equals(line.getType(), "diagonal")) {
                int opponentSequence = 0;
                for (Coordinate coordinate : line.getCoordinates()) {
                    if (coordinate.getContent() != null && coordinate.getContent() != this) {
                        opponentSequence++;
                        if (opponentSequence >= board.getBoardSize() - 1) {
                            System.out.println("DIAGONAL BLOCK FOUND");
                            coordinatesToBlock.add(getCoordinatePairDiagonal(coordinate, opponentSequence));
                        }
                    } else opponentSequence = 0;
                }
            }
            if (Objects.equals(line.getType(), "anti-diagonal")) {
                int opponentSequence = 0;
                for (Coordinate coordinate : line.getCoordinates()) {
                    if (coordinate.getContent() != null && coordinate.getContent() != this) {
                        opponentSequence++;
                        if (opponentSequence >= board.getBoardSize() - 1) {
                            System.out.println("ANTI-DIAGONAL BLOCK FOUND");
                            coordinatesToBlock.add(getCoordinatePairAntiDiagonal(coordinate, opponentSequence));
                        }
                    } else opponentSequence = 0;
                }
            }
        }
        return tryPlaceMarker(board, coordinatesToBlock);
    }

    /**
     * Sorts through all lines provided by Analyzer. First checks for any line that the opponent has not put a marker in.
     * Then collects the best coordinates for each direction that has the highest sequence. Tries to put next marker on
     * the line with the longest sequence.
     *
     * @param board The board being played on.
     * @return Returns true if a smart placement is found.
     */
    public boolean smartPositionFound(Board board) {
        Analyzer analyzer = new Analyzer(board);
        Object[][] grid = board.getGrid();
        ArrayList<CoordinatePair> coordinatesToCheck = new ArrayList<>();
        ArrayList<Line> allLines = analyzer.getAllLines();
        int highestSequence = 0;
        int currentSequence;
        for (Line line : allLines) {
            //Sets clearPath of any lines the opponent has markers in to false
            for (Coordinate coordinate : line.getCoordinates()) {
                if (coordinate.getContent() != null && coordinate.getContent() != this) {
                    line.setClearPath(false);
                }
            }
        }
        for (Line line : allLines) {
            //Only looks at the lines with a clear path and win potential
            if (line.hasClearPath()) {
                if (Objects.equals(line.getType(), "row")) {
                    currentSequence = 0;
                    for (Coordinate coordinate : line.getCoordinates()) {
                        Object position = grid[coordinate.getX()][coordinate.getY()];
                        if (position != this) {
                            currentSequence = 0;
                        } else {
                            currentSequence++;
                            if (currentSequence > highestSequence) {
                                highestSequence = currentSequence;
                                System.out.println("ROW ADDED");
                                CoordinatePair coordinatePair = getCoordinatePairRow(coordinate, currentSequence);
                                coordinatePair.setSequence(currentSequence);
                                coordinatesToCheck.add(coordinatePair);
                            }
                        }
                    }
                }
                if (Objects.equals(line.getType(), "column")) {
                    highestSequence = 0;
                    currentSequence = 0;

                    for (Coordinate coordinate : line.getCoordinates()) {
                        Object position = grid[coordinate.getX()][coordinate.getY()];
                        if (position != this) {
                            currentSequence = 0;
                        } else {
                            currentSequence++;
                            if (currentSequence > highestSequence) {
                                highestSequence = currentSequence;
                                System.out.println("COLUMN ADDED");
                                CoordinatePair coordinatePair = getCoordinatePairColumn(coordinate, currentSequence);
                                coordinatePair.setSequence(currentSequence);
                                coordinatesToCheck.add(coordinatePair);
                            }
                        }
                    }
                }
                if (Objects.equals(line.getType(), "diagonal")) {
                    highestSequence = 0;
                    currentSequence = 0;
                    for (Coordinate coordinate : line.getCoordinates()) {
                        Object position = grid[coordinate.getX()][coordinate.getY()];
                        if (position != this) {
                            currentSequence = 0;

                        } else {
                            currentSequence++;
                            if (currentSequence > highestSequence) {
                                highestSequence = currentSequence;
                                System.out.println("DIAGONAL ADDDED");
                                CoordinatePair coordinatePair = getCoordinatePairDiagonal(coordinate, currentSequence);
                                coordinatePair.setSequence(currentSequence);
                                coordinatesToCheck.add(coordinatePair);
                            }
                        }
                    }
                }
                if (Objects.equals(line.getType(), "anti-diagonal")) {
                    highestSequence = 0;
                    currentSequence = 0;
                    for (Coordinate coordinate : line.getCoordinates()) {
                        Object position = grid[coordinate.getX()][coordinate.getY()];

                        if (position != this) {
                            currentSequence = 0;
                        } else {
                            currentSequence++;
                            if (currentSequence > highestSequence) {
                                highestSequence = currentSequence;
                                CoordinatePair coordinatePair = getCoordinatePairAntiDiagonal(coordinate, currentSequence);
                                coordinatePair.setSequence(currentSequence);
                                coordinatesToCheck.add(coordinatePair);
                                System.out.println("ANTI-DIAGONAL ADDED");
                            }
                        }
                    }
                }
            }
        }
        if (tryPlaceMarker(board, coordinatesToCheck)) {
            System.out.println("SMART PLACEMENT FOUND");
            return true;
        } else {
            System.out.println("NO BEST PLACEMENT FOUND");
            return false;
        }
    }

    /**
     * Sorts the coordinates passed to it by sequence length. Tries coordinates based on highest sequence length.
     *
     * @param board                  The board being played on.
     * @param coordinatePairsToCheck A list of potential coordinates to try.
     * @return Returns true if any of the given coordinates can be placed on the board.
     */
    private boolean tryPlaceMarker(Board board, ArrayList<CoordinatePair> coordinatePairsToCheck) {
        int index = 0;
        Object[][] grid = board.getGrid();
        if (coordinatePairsToCheck.isEmpty()) {
            return false;
        }
        coordinatePairsToCheck.sort(Comparator.comparingInt(CoordinatePair::getSequence));
        Collections.reverse(coordinatePairsToCheck);
        for (CoordinatePair cp : coordinatePairsToCheck) {
            index++;
            System.out.println("TRY: " + index);
            Coordinate startCoordinate = cp.getCoordinateStart();
            Coordinate endCoordinate = cp.getCoordinateEnd();
            if (isValidPlacement(board, startCoordinate.getX(), startCoordinate.getY()) && grid[startCoordinate.getX()][startCoordinate.getY()] == null) {
                grid[startCoordinate.getX()][startCoordinate.getY()] = this;
                System.out.println(name + " placed a marker on x: " + (startCoordinate.getX() + 1) + " y: " + (startCoordinate.getY() + 1));
                return true;
            }
            System.out.println("START PLACEMENT DENIED");
            if (isValidPlacement(board, endCoordinate.getX(), endCoordinate.getY()) && grid[endCoordinate.getX()][endCoordinate.getY()] == null) {
                grid[endCoordinate.getX()][endCoordinate.getY()] = this;
                System.out.println(name + " placed a marker on x: " + (endCoordinate.getX() + 1) + " y: " + (endCoordinate.getY() + 1));
                return true;
            }
            System.out.println("END PLACEMENT DENIED");

        }
        return false;
    }

    /**
     * Checks that a coordinate is within the board's limit to avoid out of bounds errors.
     *
     */
    private boolean isValidPlacement(Board board, int x, int y) {
        return (x >= 0 && x < board.getBoardSize()) && (y >= 0 && y < board.getBoardSize());
    }


    /**
     * Returns the coordinates at the beginning and end of a row.
     * @param coordinate The starter coordinate.
     * @param offset Offset to find starter/end coordinate. Usually the length of the sequence.
     */
    private CoordinatePair getCoordinatePairRow(Coordinate coordinate, int offset) {
        Coordinate coordinateStart = new Coordinate(coordinate.getX() - offset, coordinate.getY());
        Coordinate coordinateEnd = new Coordinate(coordinate.getX() + 1, coordinate.getY());
        return new CoordinatePair(coordinateStart, coordinateEnd);
    }

    /**
     * Returns the coordinates at the beginning and end of a column.
     * @param coordinate The starter coordinate.
     * @param offset Offset to find starter/end coordinate. Usually the length of the sequence.
     */
    private CoordinatePair getCoordinatePairColumn(Coordinate coordinate, int offset) {
        Coordinate coordinateStart = new Coordinate(coordinate.getX(), coordinate.getY() - offset);
        Coordinate coordinateEnd = new Coordinate(coordinate.getX(), coordinate.getY() + 1);
        return new CoordinatePair(coordinateStart, coordinateEnd);
    }

    /**
     * Returns the coordinates at the beginning and end of a diagonal.
     * @param coordinate The starter coordinate.
     * @param offset Offset to find starter/end coordinate. Usually the length of the sequence.
     */
    private CoordinatePair getCoordinatePairDiagonal(Coordinate coordinate, int offset) {
        Coordinate coordinateStart = new Coordinate(coordinate.getX() - offset, coordinate.getY() - offset);
        Coordinate coordinateEnd = new Coordinate(coordinate.getX() + 1, coordinate.getY() + 1);
        return new CoordinatePair(coordinateStart, coordinateEnd);
    }

    /**
     * Returns the coordinates at the beginning and end of an anti-diagonal.
     * @param coordinate The starter coordinate.
     * @param offset Offset to find starter/end coordinate. Usually the length of the sequence.
     */
    private CoordinatePair getCoordinatePairAntiDiagonal(Coordinate coordinate, int offset) {
        Coordinate coordinateStart = new Coordinate(coordinate.getX() - offset, coordinate.getY() + offset);
        Coordinate coordinateEnd = new Coordinate(coordinate.getX() + 1, coordinate.getY() - 1);
        return new CoordinatePair(coordinateStart, coordinateEnd);
    }
}
