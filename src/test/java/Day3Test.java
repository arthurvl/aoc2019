import org.junit.jupiter.api.Test;
import day3.LineSegment;
import day3.IntersectionPoint;
import day3.Orientation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {
    @Test
    public void smallCrossingWiresCrossClosest() {
        int actualClosestCrossing = Day3.calculateClosestCrossing("R8,U5,L5,D3","U7,R6,D4,L4");
        assertEquals(6, actualClosestCrossing);
    }

    @Test
    public void calculatesStepsSmallExample() {
        int actualNearestCrossing = Day3.calculateNearestCrossing("R8,U5,L5,D3","U7,R6,D4,L4");
        assertEquals(30, actualNearestCrossing);
    }

    @Test
    public void slightlyLongerCrossingWires() {
        int actualClosestCrossing = Day3.calculateClosestCrossing("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83");
        assertEquals(159, actualClosestCrossing);
    }

    @Test
    public void slightlyLongerCrossingWiresNearest() {
        int actualClosestCrossing = Day3.calculateNearestCrossing("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83");
        assertEquals(610, actualClosestCrossing);
    }

    @Test
    public void yetLongerCrossingWires() {
        int actualClosestCrossing = Day3.calculateNearestCrossing("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51","U98,R91,D20,R16,D67,R40,U7,R15,U6,R7");
        assertEquals(410, actualClosestCrossing);
    }

    @Test
    public void calculatesSegmentsForEmptyWire() {
        List<LineSegment> actualSegments = Day3.getSegments("");
        assertTrue(actualSegments.isEmpty());
    }

    @Test
    public void calculatesCorrectSegmentUpFromPoint() {
        LineSegment expectedSegment = new LineSegment(0,0, Orientation.VERTICAL, 0, 1);
        LineSegment actualSegment = Day3.getSegment("U1", 0, 0);
        assertEquals(expectedSegment, actualSegment);
    }

    @Test
    public void calculatesCorrectSegmentDownFromPoint() {
        LineSegment expectedSegment = new LineSegment(0,0, Orientation.VERTICAL, 0, -1);
        LineSegment actualSegment = Day3.getSegment("D1", 0, 0);
        assertEquals(expectedSegment, actualSegment);
    }

    @Test
    public void calculatesCorrectSegmentLeftFromPoint() {
        LineSegment expectedSegment = new LineSegment(0,0, Orientation.HORIZONTAL, -1, 0);
        LineSegment actualSegment = Day3.getSegment("L1", 0, 0);
        assertEquals(expectedSegment, actualSegment);
    }

    @Test
    public void calculatesCorrectSegmentRightFromPoint() {
        LineSegment expectedSegment = new LineSegment(0,0, Orientation.HORIZONTAL, 1, 0);
        LineSegment actualSegment = Day3.getSegment("R1", 0, 0);
        assertEquals(expectedSegment, actualSegment);
    }

    @Test
    public void calculatesSegmentsForSingleWire() {
        List<LineSegment> expectedSegments = new ArrayList();
        expectedSegments.add(new LineSegment(0,0, Orientation.VERTICAL, 0, 1));
        List<LineSegment> actualSegments = Day3.getSegments("U1");

        assertEquals(expectedSegments, actualSegments);
    }

    @Test
    public void calculatesNoIntersectionPointParallel() {
        LineSegment first = new LineSegment(-1,-1, Orientation.VERTICAL, -1, 1);
        LineSegment second = new LineSegment(-2,-1, Orientation.VERTICAL, -2, 1);

        assertNull(first.getIntersectionPoint(second));
    }

    @Test
    public void calculatesNoIntersectionPointNotCrossing() {
        LineSegment first = new LineSegment(-1,-1, Orientation.VERTICAL, -1, 1);
        LineSegment second = new LineSegment(-2,-2, Orientation.HORIZONTAL, 2, -2);

        assertNull(first.getIntersectionPoint(second));
    }

    @Test
    public void calculatesIntersectionPointCrossingOnEnd() {
        LineSegment first = new LineSegment(-1,-1, Orientation.VERTICAL, -1, 1);
        LineSegment second = new LineSegment(-2,-1, Orientation.HORIZONTAL, 2, -1);

        IntersectionPoint actualPoint = first.getIntersectionPoint(second);

        assertNotNull(actualPoint);
        assertEquals(-1, actualPoint.x);
        assertEquals(-1, actualPoint.y);
    }

    @Test
    public void calculatesIntersectionPointCrossing() {
        LineSegment first = new LineSegment(-1,-1, Orientation.VERTICAL, -1, 1);
        LineSegment second = new LineSegment(-2,0, Orientation.HORIZONTAL, 2, 0);
        IntersectionPoint actualPoint = first.getIntersectionPoint(second);

        assertNotNull(actualPoint);
        assertEquals(-1, actualPoint.x);
        assertEquals(0, actualPoint.y);
    }

}