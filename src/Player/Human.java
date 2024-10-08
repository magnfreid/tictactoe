package Player;

import Game.Board;

import java.util.Scanner;

public class Human extends Player {
    public Human(String name, String marker) {
        super(name, marker);
    }

    @Override
    public boolean placeMarkerCheckValid(Board board) {
        Scanner scanner = new Scanner(System.in);
        int x;
        int y;
        while (true) {
            try {
                System.out.println(name + ", where do you want to place your marker? " + "(" + marker + ")");
                System.out.println("x:");
                x = scanner.nextInt();
                scanner.nextLine();
                System.out.println("y:");
                y = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid input, try again!");
            }
        }
        Object position;
        try {
            position = board.getPlayField()[x - 1][y - 1];
        } catch (Exception e) {
            System.out.println("You cannot place a marker on x: " + x + " y: " + y + " because it is not on the board! Try again.");
            return false;
        }
        if (position == null) {
            board.getPlayField()[x - 1][y - 1] = this;
            System.out.println(name + " placed a marker on x: " + x + " y: " + y);
            return true;
        } else {
            System.out.println("x: " + x + " y: " + y + " is already occupied!");
            return false;
        }


    }
}
