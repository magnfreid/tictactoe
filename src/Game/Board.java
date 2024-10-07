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


    public void drawBoard(){
        //TODO implement drawBoard
    }


    public void checkWinner(){
        //TODO implement check winner method
    }


    public void checkIfBoardIsFull(){
        //TODO implement a check if board is full or not
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
