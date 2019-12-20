package day12;

import java.util.Objects;

public class Point3D {
    public final int x;
    public final int y;
    public final int z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Point3D other = (Point3D)obj;

        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y,z);
    }

    @Override
    public String toString() {
        return "day12.Point3D[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    public Point3D add(Point3D other) {
        return new Point3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public int energy() {
        return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);
    }
}
