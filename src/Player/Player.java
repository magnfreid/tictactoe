package Player;

import Game.Board;

public class Player {
    protected String name;
    protected char marker;
    protected int score;

    public Player(String name, char marker) {
        this.name = name;
        this.marker = marker;
        this.score = 0;
    }

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

    public char getMarker() {
        return marker;
    }

    public void setMarker(char marker) {
        this.marker = marker;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
