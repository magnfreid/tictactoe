package Game;

import Player.Player;


public class Board {
    private int boardSize;
    private Object[][] playField;
    private Player player1, player2;


    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.playField = new Object[boardSize][boardSize];
        this.player1 = new Player("Player 1", 'X') {
        };
        this.player2 = new Player("Player 2", 'O');
    }

    /**
     * Prints the board in the terminal.
     */
    public void drawBoard() {
        System.out.println("Y");
        for (int row = boardSize - 1; row >= 0; row--) {

            for (int column = 0; column < boardSize; column++) {
                Object position = playField[row][column];
                char draw;
                if (position == player1) {
                    draw = player1.getMarker();
                } else if (position == player2) {
                    draw = player2.getMarker();
                } else {
                    draw = '_';
                }
                System.out.print("|" + draw);
            }
            System.out.println(row == 0 ? "| X" : "|");
        }
    }

    /**
     * Loops through rows, columns and diagonals to check if the player has filled a row.
     * @param player The current player being checked for winner.
     * @return Returns true if player is a winner.
     */
    public boolean checkWinner(Player player) {
        boolean winner = true;
        //Checks columns
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (playField[row][column] != player) {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return winner;
            }
        }
        //Checks rows
        winner = true;
        for (int column = 0; column < boardSize; column++) {
            for (int row = 0; row < boardSize; row++) {
                if (playField[row][column] != player) {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return winner;
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
            return winner;
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

    public int getBoardSize() {
        return boardSize;
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

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
