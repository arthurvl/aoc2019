package day12;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class Moon {
    public final int id;
    public Point3D position;
    public Point3D velocity;

    public Moon(int id, Point3D position) {
        this(id, position, new Point3D(0,0,0));
    }

    public Moon(int id, Point3D position, Point3D velocity) {
        this.id = id;
        this.position = position;
        this.velocity = velocity;
    }

    public static int getX(Moon m) {
        return m.position.x;
    }

    public static int getY(Moon m) {
        return m.position.y;
    }

    public static int getZ(Moon m) {
        return m.position.z;
    }

    public static int getDX(Moon m) {
        return m.velocity.x;
    }

    public static int getDY(Moon m) {
        return m.velocity.y;
    }

    public static int getDZ(Moon m) {
        return m.velocity.z;
    }

    public static int getDelta(Moon m, ToIntFunction<Moon> axis) {
        int axisIndex = axisGetters().indexOf(axis);
        return axisDeltaGetters.get(axisIndex).applyAsInt(m);
    }

    private static final List<ToIntFunction<Moon>> axisDeltaGetters = Collections.unmodifiableList(Arrays.asList(Moon::getDX, Moon::getDY, Moon::getDZ));
    private static final List<ToIntFunction<Moon>> axisGetters = Collections.unmodifiableList(Arrays.asList(Moon::getX, Moon::getY, Moon::getZ));

    public static List<ToIntFunction<Moon>> axisGetters() {
        return axisGetters;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Moon other = (Moon)obj;

        return this.position.equals(other.position) && this.velocity.equals(other.velocity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.position, this.velocity);
    }

    @Override
    public String toString() {
        return "day12.Moon[<" + position.x + ", " + position.y + ", " +position.z + ">, <" +
                                velocity.x + ", " + velocity.y + ", " +velocity.z + ">]";

    }

    public int energy() {
        return position.energy() * velocity.energy();
    }

    public static String getAxis(ToIntFunction<Moon> axisGetter) {
        ToIntFunction<Moon> axis;
        axis = axisGetters.get(0);
        if (axisGetter == axis) {
            return "X";
        }
        axis = axisGetters.get(1);
        if (axisGetter == axis) {
            return "Y";
        }
        axis = axisGetters.get(2);
        if (axisGetter == axis) {
            return "Z";
        }
        return "?";
    }

}
