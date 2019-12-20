import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {
    @Test
    public void findsIntersectionInMiddle() {
        String image = ".#.\n" +
                       "###\n" +
                       ".#.\n";

        List<Point> intersections = Day17.getIntersections(image);

        assertEquals(1, intersections.size());
        assertEquals(new Point(1,1), intersections.get(0));
    }

    @Test
    public void findsNoIntersectionInCorner() {
        String image = "###\n" +
                "#..\n" +
                "#..\n";

        List<Point> intersections = Day17.getIntersections(image);

        assertEquals(0, intersections.size());
    }

    @Test
    public void findsIntersectionOnSide() {
        String image = "###\n" +
                ".#.\n";

        List<Point> intersections = Day17.getIntersections(image);

        assertEquals(1, intersections.size());
        assertEquals(new Point(1,0), intersections.get(0));
    }

    @Test
    public void findsMultipleIntersections() {
        String image =  ".#...\n" +
                        "####.\n" +
                        ".#.#.\n" +
                        ".####\n" +
                        "...#.\n";


        List<Point> intersections = Day17.getIntersections(image);

        assertEquals(2, intersections.size());
        assertEquals(new Point(1,1), intersections.get(0));
        assertEquals(new Point(3,3), intersections.get(1));
    }

    @Test
    public void calculatesAlignmentParameters() {
        String image =  ".#...\n" +
                "####.\n" +
                ".#.#.\n" +
                ".####\n" +
                "...#.\n";

        List<Integer> alignmentParameters = Day17.calculateAlignmentParameters(image);

        assertEquals(2, alignmentParameters.size());
        assertEquals(1, alignmentParameters.get(0));
        assertEquals(9, alignmentParameters.get(1));
    }

    @Test
    public void calculatesAlignment() {
        String image =  ".#...\n" +
                "####.\n" +
                ".#.#.\n" +
                ".####\n" +
                "...#.\n";

        int alignment = Day17.calculateAlignment(image);

        assertEquals(10, alignment);
    }
}