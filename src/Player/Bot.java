package Player;

import Game.Board;

import java.util.Random;

public class Bot extends Player {
    final private Random random;

    public Bot(String name, String marker) {
        super(name, marker);
        this.random = new Random();
    }


    /**
     * Loops until bot finds an empty space to place marker.
     *
     * @param board The board that's being played.
     * @return Returns true when complete.
     */
    @Override
    public boolean placeMarkerCheckValid(Board board) {
        while (true) {
            int x = random.nextInt(0, board.getBoardSize());
            int y = random.nextInt(0, board.getBoardSize());
            Object position;
            position = board.getPlayField()[x][y];
            if (position == null) {
                board.getPlayField()[x][y] = this;
                System.out.println(name + " placed a marker on x: " + (x + 1) + " y: " + (y + 1));
                return true;
            }
        }
    }
}
