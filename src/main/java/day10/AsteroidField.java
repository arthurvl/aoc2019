package day10;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AsteroidField {
    public final int width;

    public final int height;

    private final List<Point> asteroidPositions;

    private AsteroidField() {
        this(0,0,new ArrayList<>());
    }

    private AsteroidField(int width, int height, List<Point> asteroidPositions) {
        this.width = width;
        this.height = height;
        this.asteroidPositions = Collections.unmodifiableList(asteroidPositions);
    }

    public static AsteroidField valueOf(String s) {

        List<String> lines = Arrays.asList(s.split("\n"));

        if (lines.size() <= 0) {
            return new AsteroidField();
        }

        if (lines.get(0).length() <= 0) {
            return new AsteroidField();
        }


        int height = lines.size();
        int width = lines.get(0).length();

        List<Point> asteroids = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                if (line.charAt(x) == '#') {
                    asteroids.add(new Point(x,y));
                }
            }
        }

        return new AsteroidField(width, height, asteroids);
    }

    public int countAsteroids() {
        return asteroidPositions.size();
    }

    public List<Point> getAsteroidLocations() {
        return asteroidPositions;
    }
}
