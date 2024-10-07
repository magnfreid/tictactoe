package Game;

import Player.Player;

public class Game {

    final private Board board;
    final private Player p1, p2;
    private boolean newGame;

    public Game(Board board, Player p1, Player p2) {
        this.board = board;
        this.p1 = board.getPlayer1();
        this.p2 = board.getPlayer2();
        this.newGame = true;
    }


    public void run() {
        //TODO implement main run method
    }


    private void setup() {
        //TODO implement setup before starting the game loop
    }


    private void gameLoop() {
        //TODO implement game loop
    }


    private void turn(Player player) {
        //TODO implement player turn
    }


    private void playAgain() {
        //TODO implement question if new game or not
    }
}
