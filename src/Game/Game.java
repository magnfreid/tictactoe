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
        do {
            if (newGame) {
                setup();
            }
            gameLoop();
        } while (playAgain());
    }


    private void setup() {
        p1.setScore(0);
        p1.setScore(0);
        round = 1;
        System.out.println("**** TIC-TAC-TOE *****");
        chooseBoardSize();
        System.out.println("Player 1 name: ");
        p1.setName(scanner.nextLine());
        System.out.println("Player 2 name: ");
        p2.setName(scanner.nextLine());

    }


    private void gameLoop() {
        if (!newGame) {
            System.out.println("***** ROUND " + round + " *****");
            System.out.println("_____ Score _____");
            System.out.println(p1.getName() + ": " + p1.getScore());
            System.out.println(p2.getName() + ": " + p2.getScore());
            chooseBoardSize();
        }
        System.out.println("Let's play!");
        System.out.println(isEvenRound() ? p2.getName() + " begins." : p1.getName() + " begins.");
        while (true) {
            if (playerTurn(isEvenRound() ? p2 : p1)) {
                break;
            }
            if (playerTurn(isEvenRound() ? p1 : p2)) {
                break;
            }
        }
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
                    board.drawBoard();
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

    /**
     * Asks the player if it wants to continue playing or exit the app.
     * Also asks if same players should continue or to start a new game.
     *
     * @return Returns true if player wants to play again.
     */
    private boolean playAgain() {
        System.out.println("Play again?");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("yes") || (input.equalsIgnoreCase("y"))) {
                break;
            } else if (input.equalsIgnoreCase("no") || (input.equalsIgnoreCase("n"))) {
                System.out.println("Exiting...");
                return false;
            } else {
                System.out.println("Type \"yes\" or \"no\".");
            }
        }
        System.out.println("Continue with the same players?");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("yes") || (input.equalsIgnoreCase("y"))) {
                newGame = false;
                break;
            } else if (input.equalsIgnoreCase("no") || (input.equalsIgnoreCase("n"))) {
                System.out.println("Starting new game.");
                newGame = true;
                break;
            } else {
                System.out.println("Type \"yes\" or \"no\".");
            }
        }
        round++;
        return true;
    }

    private void chooseBoardSize() {
        while (true) {
            try {
                System.out.println("Choose board size: ");
                int size = scanner.nextInt();
                board.setBoardSize(size);
                scanner.nextLine();
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid input, try again!");
            }
        }
    }

    private boolean isEvenRound() {
        return round % 2 == 0;
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
