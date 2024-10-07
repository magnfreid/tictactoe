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
}
