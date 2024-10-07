import Game.Board;
import Player.Human;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);
        Human human = (Human) board.getPlayer1();
        System.out.println(human.placeMarkerCheckValid(board, 1, 3));
        System.out.println(human.placeMarkerCheckValid(board, 2, 2));
        System.out.println(human.placeMarkerCheckValid(board, 3, 1));
        board.drawBoard();
        System.out.println(board.checkWinner(human));

    }
}