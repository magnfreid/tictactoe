package Player;

import Game.Board;

public class Player {
    protected String name;
    protected String marker;
    protected int score;

    public Player(String name, String marker) {
        this.name = name;
        this.marker = marker;
        this.score = 0;
    }

    /**
     * Checks if the chosen position is null/empty, adds the player to position if not occupied.
     * @param board The current play board.
     * @param x X-Coordinate
     * @param y Y-Coordinate
     * @return Returns true if marker placement is successful.
     * @throws RuntimeException throws out of bounds error for the position in 2D-array.
     */
    public boolean placeMarkerCheckValid(Board board, int x, int y) throws RuntimeException {
        Object position;
        try {
            position = board.getPlayField()[x-1][y-1];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (position == null) {
            board.getPlayField()[x-1][y-1] = this;
            return true;
        }
        else return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
