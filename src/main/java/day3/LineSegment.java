package day3;

import java.util.Objects;

public class LineSegment {
    public final int startX;
    public final int startY;
    public final Orientation orientation;
    public final int endX;
    public final int endY;

    public LineSegment(int startx, int starty, Orientation o, int endx, int endy) {
        startX = startx;
        startY = starty;
        orientation = o;
        endX = endx;
        endY = endy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof LineSegment)) {
            return false;
        }

        LineSegment other = (LineSegment)o;

        return this.startX == other.startX && this.startY == other.startY && this.orientation == other.orientation && this.endX == other.endX && this.endY == other.endY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startX,startY,orientation,endX,endY);
    }

    public IntersectionPoint getIntersectionPoint(LineSegment second) {
        if (this.orientation == Orientation.VERTICAL) {
            if (Math.min(second.startX, second.endX) <= this.startX
                    && this.endX <= Math.max(second.startX, second.endX)
                    && Math.min(this.startY,this.endY) <= second.startY
                    && second.endY <= Math.max(this.startY, this.endY)) {
                return new IntersectionPoint(this.startX, second.startY);
            }
        } else {
            if (Math.min(second.startY, second.endY) <= this.startY
                    && this.endY <= Math.max(second.startY, second.endY)
                    && Math.min(this.startX,this.endX) <= second.startX
                    && second.endX <= Math.max(this.startX, this.endX)) {
                return new IntersectionPoint(second.startX, this.startY);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "(" + this.startX + "," + this.startY + (this.orientation == Orientation.VERTICAL ? "|" : "-") + this.endX + "," + this.endY + ")";
    }

    public int length() {
        if (this.orientation == Orientation.VERTICAL) {
            return Math.abs(this.endY - this.startY);
        } else {
            return Math.abs(this.endX - this.startX);
        }
    }
}
