package Player;

import Game.Board;

import java.util.Scanner;

public class Human extends Player {
    public Human(String name, char marker) {
        super(name, marker);
    }


    /**
     * Ask the player to enter coordinate to where to place marker.
     *
     * @param board The board that's being played.
     * @return Returns true if placement is valid.
     */
    @Override
    public boolean placeMarkerCheckValid(Board board) {
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        int y = 0;
        while (true) {
            System.out.println(name + ", where do you want to place your marker? " + "(" + marker + ")");
            System.out.println("x:");
            if (scanner.hasNextInt()) {
                x = scanner.nextInt();
            }
            scanner.nextLine();
            System.out.println("y:");
            if (scanner.hasNextInt()) {
                y = scanner.nextInt();
            }
            scanner.nextLine();
            if ((x > 0 && x <= board.getBoardSize()) && (y > 0 && y <= board.getBoardSize())) {
                break;
            } else {
                System.out.println("Invalid input, try again! Make sure to check that your position is on the board.");
            }
        }
        Object position = board.getGrid()[x - 1][y - 1];
        if (position == null) {
            board.getGrid()[x - 1][y - 1] = this;
            System.out.println(name + " placed a marker on x: " + x + " y: " + y);
            return true;
        } else {
            System.out.println("x: " + x + " y: " + y + " is already occupied!");
            return false;
        }
    }
}
