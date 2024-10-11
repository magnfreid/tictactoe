package Game.BoardNavigation;

public class CoordinatePair {
    Coordinate coordinateStart, coordinateEnd;
    int sequence;


    public CoordinatePair(Coordinate coordinateStart, Coordinate coordinateEnd) {
        this.coordinateStart = coordinateStart;
        this.coordinateEnd = coordinateEnd;
        this.sequence = 0;

    }

    public CoordinatePair(Coordinate coordinateStart, Coordinate coordinateEnd, int sequence, boolean hasWinPotential) {
        this.coordinateStart = coordinateStart;
        this.coordinateEnd = coordinateEnd;
        this.sequence = sequence;

    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }


    public Coordinate getCoordinateStart() {
        return coordinateStart;
    }

    public Coordinate getCoordinateEnd() {
        return coordinateEnd;
    }

}
