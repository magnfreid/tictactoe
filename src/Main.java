import Game.Board;
import Player.Human;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(2);
        Human human = (Human) board.getPlayer1();
        System.out.println(human);
        System.out.println(human.placeMarkerCheckValid(board, 1, 1));
        System.out.println(human.placeMarkerCheckValid(board, 1, 2));
        System.out.println(human.placeMarkerCheckValid(board, 2, 1));
        System.out.println(human.placeMarkerCheckValid(board, 2, 2));
        board.drawBoard();
        System.out.println(board.checkIfBoardIsFull());
    }
}