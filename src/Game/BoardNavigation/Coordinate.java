package Game.BoardNavigation;

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

    public void setContent(Object content) {
        this.content = content;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
