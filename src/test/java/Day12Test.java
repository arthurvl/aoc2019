import day12.AxisPosition;
import day12.Moon;
import day12.Point3D;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {
    @Test
    public void calculatesGravityAdjustment() {
        Point3D first = new Point3D(0,0,0);
        Point3D second = new Point3D(2,2,2);

        Pair<Point3D, Point3D> expectedAdjustments = new Pair<>(new Point3D(1,1,1), new Point3D(-1, -1, -1));

        Pair<Point3D,Point3D> gravityAdjustments = Day12.calculateGravityAdjustments(first, second);

        assertEquals(expectedAdjustments, gravityAdjustments);
    }

    @Test
    public void calculatesPairs() {
        List<Integer> elements = Arrays.asList(1,2,3,4);

        List<Pair<Integer,Integer>> pairs = Day12.generatePairs(elements);

        List<Pair<Integer,Integer>> expected = Arrays.asList(
                new Pair(1,2),
                new Pair(1,3),
                new Pair(1,4),
                new Pair(2,3),
                new Pair(2,4),
                new Pair(3,4));

        assertIterableEquals(expected,pairs);
    }

    @Test
    public void stepsSingle() {
        List<Moon> moons = Arrays.asList(
                new Moon(1, new Point3D(-1,0,2)),
                new Moon(2, new Point3D(2,-10,-7)),
                new Moon(3, new Point3D(4,-8,8)),
                new Moon(4, new Point3D(3,5,-1)));

        List<Moon> actual = Day12.applyStep(moons);

        List<Moon> expected = Arrays.asList(
                new Moon(1, new Point3D(2,-1,1), new Point3D(3,-1,-1)),
                new Moon(2, new Point3D(3,-7,-4), new Point3D(1,3,3)),
                new Moon(3, new Point3D(1,-7,5), new Point3D(-3,1,-3)),
                new Moon(4, new Point3D(2,2,0), new Point3D(-1,-3,1)));

        assertEquals(new HashSet<>(expected), new HashSet<>(actual));
    }

    @Test
    public void steps10() {
        List<Moon> moons = Arrays.asList(
                new Moon(1, new Point3D(-1,0,2)),
                new Moon(2, new Point3D(2,-10,-7)),
                new Moon(3, new Point3D(4,-8,8)),
                new Moon(4, new Point3D(3,5,-1)));

        List<List<Moon>> history = Day12.applySteps(10,moons);

        List<Moon> expected = Arrays.asList(
                new Moon(1, new Point3D(2,1,-3), new Point3D(-3,-2,1)),
                new Moon(2, new Point3D(1,-8,0), new Point3D(-1,1,3)),
                new Moon(3, new Point3D(3,-6,1), new Point3D(3,2,-3)),
                new Moon(4, new Point3D(2,0,4), new Point3D(1,-1,-1)));

        assertEquals(new HashSet<>(expected), new HashSet<>(history.get(10)));
    }

    @Test
    public void calculatesEnergyInPoint() {
        assertEquals(6, new Point3D(2,1,-3).energy());
    }

    @Test
    public void calculatesEnergyInMoon() {
        assertEquals(36, new Moon(1, new Point3D(2,1,-3), new Point3D(-3,-2,1)).energy());
    }

    @Test
    public void steps10AndCalculatesEnergy() {
        List<Moon> moons = Arrays.asList(
                new Moon(1, new Point3D(-1,0,2)),
                new Moon(2, new Point3D(2,-10,-7)),
                new Moon(3, new Point3D(4,-8,8)),
                new Moon(4, new Point3D(3,5,-1)));

        List<List<Moon>> history = Day12.applySteps(10,moons);

        int energy = Day12.getEnergy(history.get(10));

        assertEquals(179, energy);
    }

    @Test
    public void findsRepeatShortSample() {
        List<Moon> moons = Arrays.asList(
                new Moon(1, new Point3D(-1,0,2)),
                new Moon(2, new Point3D(2,-10,-7)),
                new Moon(3, new Point3D(4,-8,8)),
                new Moon(4, new Point3D(3,5,-1)));

        BigInteger repeatStep = Day12.findRepeatStep(moons);

        assertEquals(new BigInteger("2772"), repeatStep);
    }

    @Test
    public void axisPositionsCompareCorrectly() {
        List<Moon> moons = Arrays.asList(
                new Moon(1, new Point3D(-1,0,2)),
                new Moon(2, new Point3D(2,-10,-7)),
                new Moon(3, new Point3D(4,-8,8)),
                new Moon(4, new Point3D(3,5,-1)));

        AxisPosition positions = new AxisPosition(moons, Moon.axisGetters().get(0));
        AxisPosition expected = new AxisPosition(moons, Moon.axisGetters().get(0));

        assertEquals(expected, positions);
        assertEquals(expected.hashCode(), positions.hashCode());
    }

    @Test
    public void findsRepeatLongSample() {
        List<Moon> moons = Arrays.asList(
                new Moon(1, new Point3D(-8,-10,0)),
                new Moon(2, new Point3D(5,5,10)),
                new Moon(3, new Point3D(2,-7,3)),
                new Moon(4, new Point3D(9,-8,-3)));

        BigInteger repeatStep = Day12.findRepeatStep(moons);

        assertEquals(new BigInteger("4686774924") , repeatStep);
    }

}