package Game;

import Player.Player;


public class Board {
    private int boardSize;
    private Object[][] grid;


    public Board() {
        this.boardSize = 3;
        this.grid = new Object[boardSize][boardSize];
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
                String draw;
                if (position == p1) {
                    draw = p1.getMarker();
                } else if (position == p2) {
                    draw = p2.getMarker();
                } else {
                    draw = "_";
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
     * Loops through rows, columns and diagonals to check if the player has filled a row.
     *
     * @param player The current player being checked for winner.
     * @return Returns true if player is a winner.
     */
    public boolean checkWinner(Player player) {
        boolean winner;
        //Checks columns
        for (int x = 0; x < boardSize; x++) {
            winner = true;
            for (int y = 0; y < boardSize; y++) {
                if (grid[x][y] != player) {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return true;
            }
        }
        //Checks rows
        for (int y = 0; y < boardSize; y++) {
            winner = true;
            for (int x = 0; x < boardSize; x++) {
                if (grid[x][y] != player) {
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
            if (grid[i][i] != player) {
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
            if (grid[i][boardSize - 1 - i] != player) {
                winner = false;
                break;
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
