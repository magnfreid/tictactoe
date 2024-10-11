package Game.BoardNavigation;

import java.util.ArrayList;

/**
 * A single line on the game board (any direction). Stores all coordinates and has a String type to sort through directions.
 * Has a clearPath boolean for Bot to check if opponent has markers in the line or not.
 */
public class Line {
    private final ArrayList<Coordinate> coordinates;
    private final String type;
    private boolean clearPath;

    public Line(ArrayList<Coordinate> coordinates, String type) {
        this.coordinates = coordinates;
        this.type = type;
        this.clearPath = true;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public String getType() {
        return type;
    }

    public boolean hasClearPath() {
        return clearPath;
    }

    public void setClearPath(boolean clearPath) {
        this.clearPath = clearPath;
    }
}