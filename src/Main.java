import Game.Board;
import Player.Human;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);
        Human human = (Human) board.getPlayer1();
        System.out.println(human);
        System.out.println(board.getPlayField()[1][1]);
        System.out.println(human.placeMarkerCheckValid(board, 1, 1));
        System.out.println(board.getPlayField()[1][1]);
        board.drawBoard();
    }
}