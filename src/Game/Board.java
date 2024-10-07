package Game;

import Player.Player;
import Player.Human;

public class Board {
    private int boardSize;
    private Object[][] playField;
    private Player player1, player2;


    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.playField = new Object[boardSize][boardSize];
        this.player1 = new Human("Player 1", 'X');
        this.player2 = new Human("Player 2", 'O');
    }

    /**
     * Prints the board in the terminal.
     */
    public void drawBoard() {
        System.out.println("Y");
        for (int row = boardSize-1; row >= 0; row--) {

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


    public void checkWinner() {
        //TODO implement check winner method
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
       return  true;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int size) {
        this.boardSize = size;
        this.playField = new Object[boardSize][boardSize];
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
