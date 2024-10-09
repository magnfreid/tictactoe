package Player;

import Game.Board;

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

    //Try to find the row/column/diagonal with most markers in a row
    public Object bestMarkerPosition(Board board) {
        int boardSize = board.getBoardSize();
        Object[][] grid = board.getGrid();
        Object markerPlacement = null;
        int bestMarkerInRow = 0;

        for (int x = 0; x < boardSize; x++) {
            int markerInRow = 0;
            for (int y = 0; y < boardSize; y++) {
                if (grid[x][y] != this) {
                    markerInRow = 0;
                } else {
                    markerInRow++;
                    //Extract the column with the highest nr of markers in a row
                    if (markerInRow > bestMarkerInRow) {
                        bestMarkerInRow = markerInRow;
                        //Check if the position above the column is open for placement or not
                        if(grid[x][y + 1] == null) {
                            markerPlacement = grid[x][y+1];
                        }
                        //Same for the position below the column
                        else if (grid[x][y-bestMarkerInRow] == null) {
                            markerPlacement = grid[x][y-bestMarkerInRow];
                        }
                    }
                }
            }
        }
        return markerPlacement;
        /*//Checks rows
        for (int y = 0; y < boardSize; y++) {
            winner = true;
            for (int x = 0; x < boardSize; x++) {
                if (grid[x][y] != this) {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return true;
            }
        }
        //Checks diagonal
        winner = true;
        for (int i = 0; i < boardSize; i++) {
            if (grid[i][i] != this) {
                winner = false;
                break;
            }
        }
        if (winner) {
            return true;
        }
        //Checks anti-diagonal
        winner = true;
        for (int i = 0; i < boardSize; i++)
            if (grid[i][boardSize - 1 - i] != this) {
                winner = false;
                break;
            }
        return winner;*/
    }
}
