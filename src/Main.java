import Game.Board;
import Game.Game;
import Player.Player;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.playerTurn(game.getBoard().getPlayer1());
        game.getBoard().drawBoard();
    }
}