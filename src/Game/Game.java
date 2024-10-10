package Game;

import Player.Player;
import Player.Human;
import Player.Bot;

import java.util.Scanner;

public class Game {
    final private Board board;
    final private Scanner scanner;
    private int round;
    private boolean newGame;
    final private Human p1;
    private Player p2;

    public Game() {
        this.board = new Board();
        this.scanner = new Scanner(System.in);
        this.round = 1;
        this.newGame = true;
        this.p1 = new Human("Player 1", 'X');
    }

    /**
     * The main method of the game.
     */
    public void run() {
        do {
            if (newGame) {
                setup();
            }
            gameLoop();
        } while (playAgain());
    }

    /**
     * If a new game needs to be set up, with new players.
     */
    private void setup() {
        System.out.println("**** TIC-TAC-TOE *****");
        while (true) {
            System.out.println("Play against computer? Type \"yes\" or \"no\".");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                p2 = new Bot("BOT", 'O');
                break;
            } else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
                p2 = new Human("Player 2", 'O');
                break;
            }
        }
        chooseBoardSize();
        System.out.println("Player 1 name: ");
        p1.setName(scanner.nextLine());
        if (!(p2 instanceof Bot)) {
            System.out.println("Player 2 name: ");
            p2.setName(scanner.nextLine());
        }
        p1.setScore(0);
        p2.setScore(0);
        round = 1;
    }

    /**
     * The main game loop.
     * Players take their turn, loop ends if board is full or if there is a winner.
     * The starting player shifts every turn.
     */
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
            if (turn(isEvenRound() ? p2 : p1)) {
                break;
            }
            if (turn(isEvenRound() ? p1 : p2)) {
                break;
            }
        }
    }

    /**
     * A single player's turn. Loops until player makes a valid placement.
     *
     * @param player The player taking the turn.
     * @return Returns true if board is full or if player wins after placing marker.
     */
    private boolean turn(Player player) {
        if (board.checkIfBoardIsFull()) {
            System.out.println("The board is full, it's a draw!");
            return true;
        }
        do {
            board.drawBoard(p1, p2);
        } while (!player.placeMarkerCheckValid(board));
        if (board.checkWinner(player)) {
            board.drawBoard(p1, p2);
            System.out.println(player.getName() + " is the winner!");
            player.setScore(player.getScore() + 1);
            return true;
        }
        return false;
    }

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
            if (input.trim().equalsIgnoreCase("yes") || (input.trim().equalsIgnoreCase("y"))) {
                break;
            } else if (input.trim().equalsIgnoreCase("no") || (input.trim().equalsIgnoreCase("n"))) {
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
                round++;
                break;
            } else if (input.trim().equalsIgnoreCase("no") || (input.trim().equalsIgnoreCase("n"))) {
                System.out.println("Starting new game.");
                newGame = true;
                break;
            } else {
                System.out.println("Type \"yes\" or \"no\".");
            }
        }
        return true;
    }

    /**
     * Asks the player to set a board size between 3 and 9.
     * Automatically sets to 3 if input is below 3 and 9 if input is above 9.
     */
    private void chooseBoardSize() {
        while (true) {
                System.out.println("Choose board size (3-9): ");
                if (scanner.hasNextInt()) {
                    int size = scanner.nextInt();
                    scanner.nextLine();
                    if (size < 3) {
                        size = 3;
                        System.out.println("Size set to 3.");
                    } else if (size > 9) {
                        size = 9;
                        System.out.println("Size zet to 9.");
                    }
                    board.setBoardSize(size);
                    break;
                }
             else {
                 scanner.nextLine();
                System.out.println("Invalid input, try again!");}
            }
        }


    private boolean isEvenRound() {
        return round % 2 == 0;
    }
}
