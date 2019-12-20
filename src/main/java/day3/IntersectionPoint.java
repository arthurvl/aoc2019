package day3;

public class IntersectionPoint {
    public final int x;
    public final int y;

    public IntersectionPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int ManhattanDistanceFrom(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }
}
