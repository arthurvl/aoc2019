package day12;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;

public class AxisPosition {
    private final int[] axisPositions;
    private final int[] axisDeltas;
    private final ToIntFunction<Moon> getter;

    public AxisPosition(List<Moon> moons, ToIntFunction<Moon> getAxisValue) {
        this.getter = getAxisValue;
        axisPositions = new int[moons.size()];
        axisDeltas = new int[moons.size()];
        for (Moon m : moons) {
            axisPositions[m.id - 1] = getAxisValue.applyAsInt(m);
            axisDeltas[m.id - 1] = Moon.getDelta(m, getAxisValue);
        }
    }

    public ToIntFunction<Moon> getAxisReader() {
        return getter;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AxisPosition other = (AxisPosition)obj;

        if (this.axisPositions.length != other.axisPositions.length) {
            return false;
        }

        if (this.getter != other.getter) {
            return false;
        }

        boolean result = true;

        for (int i = 0; i<axisPositions.length; i++) {
            result = result && (this.axisPositions[i] == other.axisPositions[i]);
            result = result && (this.axisDeltas[i] == other.axisDeltas[i]);
        }

        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(axisPositions), Arrays.hashCode(axisDeltas), getter);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < axisPositions.length; i++) {
            b.append("(");
            b.append(axisPositions[i]);
            b.append(",");
            b.append(axisDeltas[i]);
            b.append(")");
        }
        return "day12.AxisPositions[" + Moon.getAxis(getter) + ", " + b.toString() + "]";
    }
}
