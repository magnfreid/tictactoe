package Game;

import Game.BoardNavigation.Coordinate;
import Game.BoardNavigation.Line;
import Player.Bot.Analyzer;
import Player.Player;

import java.util.ArrayList;

/**
 * Class containing the game board. Responsible for drawing the board in the terminal and checking winners or if board is full.
 */
public class Board {
    private int boardSize;
    private Object[][] grid;
    final private Analyzer analyzer;


    public Board() {
        this.boardSize = 3;
        this.grid = new Object[boardSize][boardSize];
        this.analyzer = new Analyzer(this);
    }

    /**
     * Prints the board in the terminal.
     */
    public void drawBoard(Player p1, Player p2) {
        System.out.println("𝒚");
        for (int y = boardSize - 1; y >= 0; y--) {
            System.out.print(y + 1 + " ");
            for (int x = 0; x < boardSize; x++) {
                Object position = grid[x][y];
                char draw;
                if (position == p1) {
                    draw = p1.getMarker();
                } else if (position == p2) {
                    draw = p2.getMarker();
                } else {
                    draw = '_';
                }
                System.out.print("|" + draw);
            }
            System.out.println("|");
        }
        System.out.print("👻 ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.print("𝒙");
        System.out.println();
    }

    /**
     * Checks all lines provided by Analyzer class for a full row (winner).
     *
     * @param player The player being checked.
     * @return Returns true if player has won.
     */
    public boolean checkWinner(Player player) {
        ArrayList<Line> allLines = analyzer.getAllLines();
        boolean winner = true;
        for (Line line : allLines) {
            winner = true;
            for (Coordinate coordinate : line.getCoordinates()) {
                if (coordinate.getContent() != player) {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return true;
            }
        }
        return winner;
    }

    /**
     * Checks if board is full of markers or not.
     *
     * @return Returns true if there's no space left on the board.
     */
    public boolean checkIfBoardIsFull() {
        for (Object[] array : grid) {
            for (Object element : array) {
                if (element == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setBoardSize(int size) {
        this.boardSize = size;
        this.grid = new Object[size][size];
    }

    public Object[][] getGrid() {
        return grid;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
