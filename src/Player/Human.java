package Player;

import Game.Board;

public class Human extends Player {
    public Human(String name, char marker) {
        super(name, marker);
    }


    /**
     * Checks if position is empty then adds player to position.
     * @param board The board that's being played.
     * @param x X - Coordinate
     * @param y Y - Coordinate
     * @return Returns true if placement is valid.
     */
    public boolean placeMarkerCheckValid(Board board, int x, int y) {
        if (board.getPlayField()[x][y] == null) {
            board.getPlayField()[x][y] = this;
            return true;
        }
        else return false;
    }
}
