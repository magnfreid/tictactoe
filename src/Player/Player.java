package Player;

import Game.Board;

public abstract class Player {
    protected String name;
    protected char marker;
    protected int score;

    public Player(String name, char marker) {
        this.name = name;
        this.marker = marker;
        this.score = 0;
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
