package Player;

import Game.Board;

public class Bot extends Player {
    public Bot(String name, char marker) {
        super(name, marker);
        this.name = "BOT";
        this.marker = 'B';
    }
        public void placeMarker(Board board) {
            //TODO implement bot marker placement
    }
}
