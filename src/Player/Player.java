package Player;

import Game.Board;

public abstract class Player {
    protected String name;
    protected String marker;
    protected int score;

    public Player(String name, String marker) {
        this.name = name;
        this.marker = marker;
        this.score = 0;
    }

    public abstract boolean placeMarkerCheckValid(Board board);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarker() {
        return marker;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
