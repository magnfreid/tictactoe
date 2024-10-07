package Game;

import Player.Player;

import java.util.Scanner;

public class Game {

    final private Board board;
    final private Player p1, p2;
    private final Scanner scanner;
    private boolean newGame;
    private boolean botMatch;
    private int round;

    public Game() {
        this.board = new Board(3);
        this.p1 = board.getPlayer1();
        this.p2 = board.getPlayer2();
        this.scanner = new Scanner(System.in);
        this.newGame = true;
        this.botMatch = false;
        this.round = 1;

    }


    public void run() {
        setup();
        gameLoop();
    }


    private void setup() {
        System.out.println("***** TIC-TAC-TOE *****");

    }


    private void gameLoop() {
        board.drawBoard();
        while (true) {
            if (playerTurn(isEvenRound() ? p2 : p1)) {
                break;
            }
            if (playerTurn(isEvenRound() ? p1 : p2)) {
                break;
            }
        }
        round++;
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
                } else {
                    System.out.println("X: " + x + " Y: " + y + " is already occupied!");
                }
            } catch (RuntimeException e) {
                System.out.println("You cannot place a marker on X: " + x + " Y: " + y + " because it is not on the board! Try again.");
            }

        }
        board.drawBoard();
        if (board.checkWinner(player)) {
            System.out.println(player.getName() + " is the winner!");
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

    private boolean isEvenRound() {
        return round % 2 == 0;
    }

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
