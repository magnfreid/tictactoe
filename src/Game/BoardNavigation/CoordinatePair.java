package Game.BoardNavigation;

public class CoordinatePair {
    Coordinate coordinateStart, coordinateEnd;
    int sequence;
    boolean hasWinPotential;


    public CoordinatePair(Coordinate coordinateStart, Coordinate coordinateEnd) {
        this.coordinateStart = coordinateStart;
        this.coordinateEnd = coordinateEnd;
        this.sequence = 0;
        this.hasWinPotential = true;
    }

    public CoordinatePair(Coordinate coordinateStart, Coordinate coordinateEnd, int sequence, boolean hasWinPotential) {
        this.coordinateStart = coordinateStart;
        this.coordinateEnd = coordinateEnd;
        this.sequence = sequence;
        this.hasWinPotential = hasWinPotential;
    }

    public int getSequence() {
        return sequence;
    }

    public Coordinate getCoordinateStart() {
        return coordinateStart;
    }

    public Coordinate getCoordinateEnd() {
        return coordinateEnd;
    }

    public boolean hasWinPotential() {
        return hasWinPotential;
    }
}
