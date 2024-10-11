package Game.BoardNavigation;

import java.util.ArrayList;

public class Line {
    private ArrayList<Coordinate> coordinates;
    private String type;
    private boolean clearPath;

    public Line(ArrayList<Coordinate> coordinates, String type) {
        this.coordinates = coordinates;
        this.type = type;
        this.clearPath = true;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean hasClearPath() {
        return clearPath;
    }

    public void setClearPath(boolean clearPath) {
        this.clearPath = clearPath;
    }
}