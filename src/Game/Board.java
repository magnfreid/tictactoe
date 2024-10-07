package Game;

import Player.Player;


public class Board {
    private int boardSize;
    private Object[][] playField;
    private final Player player1, player2;

    public Board() {
        this.boardSize = 3;
        this.playField = new Object[boardSize][boardSize];
        this.player1 = new Player("Player 1", "✘") {
        };
        this.player2 = new Player("Player 2", "●");
    }

    /**
     * Prints the board in the terminal.
     */
    public void drawBoard() {
        System.out.println("𝒚");
        for (int column = boardSize - 1; column >= 0; column--) {
            System.out.print(column + 1 + " ");
            for (int row = 0; row < boardSize; row++) {
                Object position = playField[row][column];
                String draw;
                if (position == player1) {
                    draw = player1.getMarker();
                } else if (position == player2) {
                    draw = player2.getMarker();
                } else {
                    draw = "_";
                }
                System.out.print("|" + draw);
            }
            System.out.println("|");
        }
        System.out.print("⦿ ");
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
                if (playField[x][y] != player) {
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
                if (playField[x][y] != player) {
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
            if (playField[i][i] != player) {
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
            if (playField[i][boardSize - 1 - i] != player) {
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
        for (Object[] row : playField) {
            for (Object position : row) {
                if (position == null) {
                    return false;
                }
            }
        }
        System.out.println("The board is full, it's a draw!");
        return true;
    }

    public void setBoardSize(int size) {
        this.boardSize = size;
        this.playField = new Object[size][size];
    }

    public Object[][] getPlayField() {
        return playField;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
