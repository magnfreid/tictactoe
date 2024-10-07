package Game;

import Player.Player;

import java.util.Scanner;

public class Game {

    final private Board board;
    final private Player p1, p2;
    private final Scanner scanner;
    private boolean newGame;
    private boolean botMatch;

    public Game() {
        this.board = new Board(3);
        this.p1 = board.getPlayer1();
        this.p2 = board.getPlayer2();
        this.scanner = new Scanner(System.in);
        this.newGame = true;
        this.botMatch = false;

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

    public boolean playerTurn(Player player) {
        if (board.checkIfBoardIsFull()) {
            return true;
        }
        while (true) {
            int x;
            int y;
            while (true) {
                try {
                    System.out.println(player.getName() + ", where do you want to place your marker? " + "(" + player.getMarker() + ")");
                    System.out.println("X:");
                    x = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Y:");
                    y = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("Invalid input, try again!");
                }
            }

            try {
                if (player.placeMarkerCheckValid(board, x, y)) {
                    System.out.println(player.getName() + " placed a marker on X: " + x + " Y: " + y);
                    break;
                }
            } catch (RuntimeException e) {
                System.out.println("You cannot place a marker on X: " + x + " Y: " + y + ". Try again.");
            }

        }
        if (board.checkWinner(player)) {
            player.setScore(player.getScore() + 1);
            return true;
        }
        return false;
    }

 /*   private boolean botTurn(Player player) {
        if (board.checkIfBoardIsFull()) {
            return true;
        }
        player.placeMarker()
    }
*/

    private void playAgain() {
        //TODO implement question if new game or not
    }

    public Board getBoard() {
        return board;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }


    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }
}
