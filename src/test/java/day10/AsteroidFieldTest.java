package day10;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class AsteroidFieldTest {
    @Test
    public void valueOfReadsEmptyAsteroidField() {
        AsteroidField actual = AsteroidField.valueOf("");

        assertEquals(0, actual.countAsteroids());
        assertEquals(0, actual.width);
        assertEquals(0, actual.height);
    }

    @Test
    public void valueOfReadsEmptyAsteroidFieldOfNonEmptySize() {
        AsteroidField actual = AsteroidField.valueOf(".");

        assertEquals(0, actual.countAsteroids());
        assertEquals(1, actual.width);
        assertEquals(1, actual.height);
    }

    @Test
    public void valueOfReadsSingleAsteroidInTinyField() {
        AsteroidField actual = AsteroidField.valueOf("#");

        assertEquals(1, actual.countAsteroids());
        assertEquals(1, actual.width);
        assertEquals(1, actual.height);
        assertEquals(new Point(0,0), actual.getAsteroidLocations().get(0));
    }
}