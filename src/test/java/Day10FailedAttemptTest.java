import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class Day10FailedAttemptTest {
    @Test
    public void parsesPicture() {
        short[][] picture = Day10FailedAttempt.parsePicture("..\n#.");
        short[][] expected = new short[][] {{0,0},{1,0}};

        assertArrayEquals(expected,picture);
    }

    @Test
    public void calculatesExample1() {
        short[][] picture = Day10FailedAttempt.parsePicture(
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

        Pair<Point, Integer> found = Day10FailedAttempt.findBestMonitoringStation(picture);

        assertEquals(new Point(5,8), found.getValue0());
        assertEquals(33, found.getValue1());
    }

    @Test
    public void calculatesSingleAsteroidVisibility() {
        short[][] picture = Day10FailedAttempt.parsePicture("##\n#.");

        int visibleAsteroids = Day10FailedAttempt.countVisibleAsteroids(picture, 0, 0);

        assertEquals(2, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidEmptyLineVisibilityNegative() {
        short[][] picture = Day10FailedAttempt.parsePicture(".#\n##");

        int visibleAsteroids = Day10FailedAttempt.countVisibleAsteroidsOnLineTo(0,0, picture, 1, 1);

        assertEquals(0, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidSingleOnLineVisibilityNegative() {
        short[][] picture = Day10FailedAttempt.parsePicture("#.\n.#");

        int visibleAsteroids = Day10FailedAttempt.countVisibleAsteroidsOnLineTo(0,0, picture, 1, 1);

        assertEquals(1, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidMultipleOnLineVisibilityNegative() {
        short[][] picture = Day10FailedAttempt.parsePicture("#..\n.#.\n..#");

        int visibleAsteroids = Day10FailedAttempt.countVisibleAsteroidsOnLineTo(0,0, picture, 2, 2);

        assertEquals(1, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidEmptyLineVisibility() {
        short[][] picture = Day10FailedAttempt.parsePicture("##\n#.");

        int visibleAsteroids = Day10FailedAttempt.countVisibleAsteroidsOnLineTo(1,1, picture, 0, 0);

        assertEquals(0, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidSingleOnLineVisibility() {
        short[][] picture = Day10FailedAttempt.parsePicture("#.\n.#");

        int visibleAsteroids = Day10FailedAttempt.countVisibleAsteroidsOnLineTo(1,1, picture, 0, 0);

        assertEquals(1, visibleAsteroids);
    }

    @Test
    public void calculatesSingleAsteroidMultipleOnLineVisibility() {
        short[][] picture = Day10FailedAttempt.parsePicture("#..\n.#.\n..#");

        int visibleAsteroids = Day10FailedAttempt.countVisibleAsteroidsOnLineTo(2,2, picture, 0, 0);

        assertEquals(1, visibleAsteroids);
    }
}