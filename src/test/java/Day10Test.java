import day10.AsteroidField;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {
    @Test
    public void parsesPicture() {
        AsteroidField field = AsteroidField.valueOf("##\n.#");

        List<Point> asteroidLocations = Arrays.asList(new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1)});

        assertEquals(3, field.countAsteroids());
        assertIterableEquals(asteroidLocations, field.getAsteroidLocations());
    }

    @Test
    public void calculatesExample1() {
        AsteroidField field = AsteroidField.valueOf(
                "......#.#.\n" +
                        "#..#.#....\n" +
                        "..#######.\n" +
                        ".#.#.###..\n" +
                        ".#..#.....\n" +
                        "..#....#.#\n" +
                        "#..#....#.\n" +
                        ".##.#..###\n" +
                        "##...#..#.\n" +
                        ".#....####"
        );

        Pair<Point, Integer> found = Day10.findBestMonitoringStation(field);

        assertEquals(new Point(5, 8), found.getValue0());
        assertEquals(33, found.getValue1());
    }

    @Test
    public void calculatesExample2() {
        AsteroidField field = AsteroidField.valueOf(
                "#.#...#.#.\n" +
                        ".###....#.\n" +
                        ".#....#...\n" +
                        "##.#.#.#.#\n" +
                        "....#.#.#.\n" +
                        ".##..###.#\n" +
                        "..#...##..\n" +
                        "..##....##\n" +
                        "......#...\n" +
                        ".####.###.\n"
        );

        Pair<Point, Integer> found = Day10.findBestMonitoringStation(field);

        assertEquals(new Point(1, 2), found.getValue0());
        assertEquals(35, found.getValue1());
    }

    @Test
    public void calculatesExample3() {
        AsteroidField field = AsteroidField.valueOf(
                ".#..#..###\n" +
                        "####.###.#\n" +
                        "....###.#.\n" +
                        "..###.##.#\n" +
                        "##.##.#.#.\n" +
                        "....###..#\n" +
                        "..#.#..#.#\n" +
                        "#..#.#.###\n" +
                        ".##...##.#\n" +
                        ".....#.#..\n"
        );

        Pair<Point, Integer> found = Day10.findBestMonitoringStation(field);

        assertEquals(new Point(6, 3), found.getValue0());
        assertEquals(41, found.getValue1());
    }

    @Test
    public void calculatesExample4() {
        AsteroidField field = AsteroidField.valueOf(
                ".#..##.###...#######\n" +
                        "##.############..##.\n" +
                        ".#.######.########.#\n" +
                        ".###.#######.####.#.\n" +
                        "#####.##.#.##.###.##\n" +
                        "..#####..#.#########\n" +
                        "####################\n" +
                        "#.####....###.#.#.##\n" +
                        "##.#################\n" +
                        "#####.##.###..####..\n" +
                        "..######..##.#######\n" +
                        "####.##.####...##..#\n" +
                        ".#####..#.######.###\n" +
                        "##...#.##########...\n" +
                        "#.##########.#######\n" +
                        ".####.#.###.###.#.##\n" +
                        "....##.##.###..#####\n" +
                        ".#.#.###########.###\n" +
                        "#.#.#.#####.####.###\n" +
                        "###.##.####.##.#..##\n"
        );

        Pair<Point, Integer> found = Day10.findBestMonitoringStation(field);

        assertEquals(new Point(11, 13), found.getValue0());
        assertEquals(210, found.getValue1());
    }

    @Test
    public void calculatesSingleAsteroidVisibility() {
        AsteroidField field = AsteroidField.valueOf("##\n#.");

        int visibleAsteroids = Day10.countVisibleAsteroids(field, 0, 0);

        assertEquals(2, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidEmptyLineVisibilityNegative() {
        AsteroidField field = AsteroidField.valueOf(".#\n##");

        int visibleAsteroids = Day10.countVisibleAsteroidsOnLineTo(0, 0, field, 1, 1);

        assertEquals(0, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidSingleLineVisibilityNegative() {
        AsteroidField field = AsteroidField.valueOf("##\n##");

        int visibleAsteroids = Day10.countVisibleAsteroidsOnLineTo(0, 0, field, 1, 1);

        assertEquals(1, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidDoubleLineVisibilityNegative() {
        AsteroidField field = AsteroidField.valueOf("###\n.#.\n###");

        int visibleAsteroids = Day10.countVisibleAsteroidsOnLineTo(0, 0, field, 2, 2);

        assertEquals(1, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidMultipleVisibility() {
        AsteroidField field = AsteroidField.valueOf("###\n#..\n#.#");

        int visibleAsteroids = Day10.countVisibleAsteroids(field, 0, 0);

        assertEquals(3, visibleAsteroids);
    }

    @Test
    public void calculatesExample4Assignment2() {
        AsteroidField field = AsteroidField.valueOf(
                ".#..##.###...#######\n" +
                        "##.############..##.\n" +
                        ".#.######.########.#\n" +
                        ".###.#######.####.#.\n" +
                        "#####.##.#.##.###.##\n" +
                        "..#####..#.#########\n" +
                        "####################\n" +
                        "#.####....###.#.#.##\n" +
                        "##.#################\n" +
                        "#####.##.###..####..\n" +
                        "..######..##.#######\n" +
                        "####.##.####...##..#\n" +
                        ".#####..#.######.###\n" +
                        "##...#.##########...\n" +
                        "#.##########.#######\n" +
                        ".####.#.###.###.#.##\n" +
                        "....##.##.###..#####\n" +
                        ".#.#.###########.###\n" +
                        "#.#.#.#####.####.###\n" +
                        "###.##.####.##.#..##\n"
        );

        List<Point> vaporizations = Day10.calculateVaporizations(field);

        assertEquals(299, vaporizations.size());
        assertEquals(new Point(11, 12), vaporizations.get(0));
        assertEquals(new Point(12, 1), vaporizations.get(1));
        assertEquals(new Point(12, 2), vaporizations.get(2));
        assertEquals(new Point(12, 8), vaporizations.get(9));
        assertEquals(new Point(16, 0), vaporizations.get(19));
        assertEquals(new Point(16, 9), vaporizations.get(49));
        assertEquals(new Point(10, 16), vaporizations.get(99));
        assertEquals(new Point(9, 6), vaporizations.get(198));
        assertEquals(new Point(8, 2), vaporizations.get(199));
        assertEquals(new Point(10, 9), vaporizations.get(200));
        assertEquals(new Point(11, 1), vaporizations.get(298));
    }

    @Test
    public void calculatesTinyExample() {
        AsteroidField field = AsteroidField.valueOf(
                ".#.\n" +
                        "###\n" +
                        ".#.\n"
        );

        List<Point> vaporizations = Day10.calculateVaporizations(field);

        assertEquals(4, vaporizations.size());
        assertEquals(new Point(1, 0), vaporizations.get(0));
        assertEquals(new Point(2, 1), vaporizations.get(1));
        assertEquals(new Point(1, 2), vaporizations.get(2));
        assertEquals(new Point(0, 1), vaporizations.get(3));
    }

    @Test
    public void comparatorComparesCorrectly() {
        AsteroidField field = AsteroidField.valueOf(
                ".#.\n" +
                        "###\n" +
                        ".#.\n"
        );
        Comparator<Point> comparator = new Day10.PointClockwiseComparator(field, new Point(1, 1));

        assertEquals(0, comparator.compare(new Point(0, 1), new Point(0, 1)));
        assertEquals(-1, comparator.compare(new Point(1, 0), new Point(0, 1)));
        assertEquals(-1, comparator.compare(new Point(1, 0), new Point(2, 1)));
        assertEquals(-1, comparator.compare(new Point(1, 0), new Point(1, 2)));
        assertEquals(1, comparator.compare(new Point(0, 1), new Point(1, 0)));
        assertEquals(1, comparator.compare(new Point(2, 1), new Point(1, 0)));
        assertEquals(1, comparator.compare(new Point(1, 2), new Point(1, 0)));

        assertEquals(-1, comparator.compare(new Point(2, 1), new Point(1, 2)));
        assertEquals(-1, comparator.compare(new Point(2, 1), new Point(0, 1)));

        assertEquals(-1, comparator.compare(new Point(1, 2), new Point(0, 1)));
    }

    @Test
    public void calculatesSlightlyLargerExample() {
        AsteroidField field = AsteroidField.valueOf(
                ".#.\n" +
                        "###\n" +
                        ".#.\n"
        );

        List<Point> vaporizations = Day10.calculateVaporizations(field);

        assertEquals(4, vaporizations.size());
        assertEquals(new Point(1, 0), vaporizations.get(0));
        assertEquals(new Point(2, 1), vaporizations.get(1));
        assertEquals(new Point(1, 2), vaporizations.get(2));
        assertEquals(new Point(0, 1), vaporizations.get(3));
    }

    @Test
    public void comparatorComparesSlightlyLarger() {
        AsteroidField field = AsteroidField.valueOf(
                "..#..\n" +
                        "..#..\n" +
                        "#####\n" +
                        "..#..\n" +
                        "..#..\n"
        );
        Comparator<Point> comparator = new Day10.PointClockwiseComparator(field, new Point(2, 2));

        assertEquals(-1, comparator.compare(new Point(3, 2), new Point(2, 0)));
    }

    @Test
    public void comparatorComparesSlightlyMoreComplex() {
        AsteroidField field = AsteroidField.valueOf(
                "..##.\n" +
                        "..#.#\n" +
                        "#####\n" +
                        "..#.#\n" +
                        "..##.\n"
        );
        Comparator<Point> comparator = new Day10.PointClockwiseComparator(field, new Point(2, 2));

        assertEquals(-1, comparator.compare(new Point(3, 2), new Point(2, 0)));
        assertEquals(-1, comparator.compare(new Point(3, 0), new Point(2, 0)));
    }

    @Test
    public void comparatorComparesSlightlyMoreComplexSecondOnLine() {
        AsteroidField field = AsteroidField.valueOf(
                "..#.#\n" +
                        "..###\n" +
                        "#####\n" +
                        "..#.#\n" +
                        "..##.\n"
        );
        Comparator<Point> comparator = new Day10.PointClockwiseComparator(field, new Point(2, 2));

        assertEquals(-1, comparator.compare(new Point(3, 2), new Point(2, 0)));
        assertEquals(-1, comparator.compare(new Point(3, 1), new Point(2, 0)));
        assertEquals(1, comparator.compare(new Point(4, 0), new Point(2, 0)));
    }

    @Test
    public void comparatorCorrectInExampleAssignmentTwo() {
        AsteroidField field = AsteroidField.valueOf(
                ".#..##.###...###X###\n" +
                        "##.############..##.\n" +
                        ".#.######.########.#\n" +
                        ".###.#######.####.#.\n" +
                        "#####.##.#.##.###.##\n" +
                        "..#####..#.#########\n" +
                        "####################\n" +
                        "#.####....###.#.#.##\n" +
                        "##.#################\n" +
                        "#####.##.###..####..\n" +
                        "..######..##.#######\n" +
                        "####.##.####...##..#\n" +
                        ".#####..#.######.###\n" +
                        "##...#.####X#####...\n" +
                        "#.##########.#######\n" +
                        ".####.#.###.###.#.##\n" +
                        "....##.##.###..#####\n" +
                        ".#.#.###########.###\n" +
                        "#.#.#.#####.####.###\n" +
                        "###.##.####.##.#..##\n"
        );

        Comparator<Point> comparator = new Day10.PointClockwiseComparator(field, new Point(11, 13));

        assertEquals(-1, comparator.compare(new Point(16, 0), new Point(15, 0)));
        assertEquals(-1, comparator.compare(new Point(12, 2), new Point(12, 8)));
        assertEquals(1, comparator.compare(new Point(11, 9), new Point(12, 1)));
        assertEquals(1, comparator.compare(new Point(11, 1), new Point(10, 16)));
        assertEquals(-1, comparator.compare(new Point(16, 0), new Point(16, 9)));
    }


    @Test
    public void calculatesSmallExampleAssignmentTwo() {
        AsteroidField field = AsteroidField.valueOf(
                ".#....#####...#..\n" +
                        "##...##.#####..##\n" +
                        "##...#...#.#####.\n" +
                        "..#.....X...###..\n" +
                        "..#.#.....#....##\n"
        );

        List<Point> vaporizations = Day10.calculateVaporizationsFrom(field, new Point(8, 3));

        List<Point> expected = Arrays.asList(
                new Point(8, 1),
                new Point(9, 0),
                new Point(9, 1),
                new Point(10, 0),
                new Point(9, 2),
                new Point(11, 1),
                new Point(12, 1),
                new Point(11, 2),
                new Point(15, 1),

                new Point(12, 2),
                new Point(13, 2),
                new Point(14, 2),
                new Point(15, 2),
                new Point(12, 3),
                new Point(16, 4),
                new Point(15, 4),
                new Point(10, 4),
                new Point(4, 4),

                new Point(2, 4),
                new Point(2, 3),
                new Point(0, 2),
                new Point(1, 2),
                new Point(0, 1),
                new Point(1, 1),
                new Point(5, 2),
                new Point(1, 0),
                new Point(5, 1),

                new Point(6, 1),
                new Point(6, 0),
                new Point(7, 0),
                new Point(8, 0),
                new Point(10, 1),
                new Point(14, 0),
                new Point(16, 1),
                new Point(13, 3),
                new Point(14, 3)
        );

        assertEquals(field.getAsteroidLocations().size(), vaporizations.size());
        assertIterableEquals(expected, vaporizations);
    }

    @Test
    public void comparesCorrectlySmallExampleAssignmentTwo() {
        AsteroidField field = AsteroidField.valueOf(
                ".#....#####...#..\n" +
                "##...##.#####..##\n" +
                "##...#...#.#####.\n" +
                "..#.....X...###..\n" +
                "..#.#.....#....##\n"
        );

        Comparator<Point> comparator = new Day10.PointClockwiseComparator(field, new Point(8, 3));

        assertEquals(-1, comparator.compare(new Point(8, 1), new Point(9, 0)));
        assertEquals(-1, comparator.compare(new Point(4, 4), new Point(7, 0)));
    }
}