package Game.BoardNavigation;

/**
 * Stores int x- and int y- coordinates as well as its content Object (null or Player)
 */
public class Coordinate {
    final int x, y;
    Object content;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.content = null;
    }

    public Coordinate(int x, int y, Object content) {
        this.x = x;
        this.y = y;
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
