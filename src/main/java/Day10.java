import day10.AsteroidField;
import org.javatuples.Pair;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day10/input"))).lines();

        String asteroidField = input.collect(Collectors.joining("\n"));

        AsteroidField field = AsteroidField.valueOf(asteroidField);

        System.out.println(findBestMonitoringStation(field));

        System.out.println(calculateVaporizations(field).get(199));
    }


    public static Pair<Point, Integer> findBestMonitoringStation(AsteroidField field) {
        int maxCount = 0;
        Point found = new Point();
        for (Point asteroid : field.getAsteroidLocations()) {
            int visibleOthers = countVisibleAsteroids(field,asteroid.x,asteroid.y);
            if (visibleOthers > maxCount) {
                maxCount = visibleOthers;
                found = asteroid;
            }
        }
        return new Pair<>(found, maxCount);
    }

    public static int countVisibleAsteroids(AsteroidField field, int x, int y) {
        List<Point> remaining = new ArrayList<>(field.getAsteroidLocations());

        remaining.remove(new Point(x,y));

        int count = 0;
        for (int i = 0; i < remaining.size(); count++) {
            Point toCheck = remaining.get(i);
            Point delta = calculateDelta(x, y, toCheck.x, toCheck.y);
            List<Point> line = calculateLine(field, x, y, delta);
            remaining.removeAll(line);
        }

        return count;
    }

    public static int countVisibleAsteroidsOnLineTo(int x, int y, AsteroidField field, int origX, int origY) {
        Point delta = calculateDelta(origX, origY, x, y);

        List<Point> possiblePoints = calculateLine(field, origX, origY, delta);

        for (Point p : possiblePoints) {
            if (field.getAsteroidLocations().contains(p)) {
                return 1;
            }
        }

        return 0;
    }

    private static List<Point> calculateLine(AsteroidField field, int origX, int origY, Point delta) {
        List<Point> result = new ArrayList<Point>();

        int x = origX + delta.x;
        int y = origY + delta.y;

        for (int i = 2; 0<= x && x < field.width && 0 <= y && y < field.height; i++) {
            result.add(new Point (x,y));
            x = origX + i * delta.x;
            y = origY + i * delta.y;
        }

        return result;
    }

    private static Point calculateDelta(int origX, int origY, int x, int y) {
        int dx = x - origX;
        int dy = y - origY;

        int gcd = BigInteger.valueOf(dx).gcd(BigInteger.valueOf(dy)).intValue();

        if (gcd != 0) {
            dx = dx / gcd;
            dy = dy / gcd;
        }

        if (dy == 0) {
            dx = dx > 0 ? 1 : -1;
        }

        if (dx == 0) {
            dy = dy > 0 ? 1 : -1;
        }

        return new Point(dx,dy);
    }

    public static List<Point> calculateVaporizations(AsteroidField field) {
        Pair<Point,Integer> stationWithVisible = findBestMonitoringStation(field);
        Point station = stationWithVisible.getValue0();

        return calculateVaporizationsFrom(field, station);
    }

    public static List<Point> calculateVaporizationsFrom(AsteroidField field, Point station) {
        List<Point> remaining = new ArrayList<>(field.getAsteroidLocations());

        remaining.remove(new Point(station.x,station.y));

        Collections.sort(remaining, new PointClockwiseComparator(field, station));

        return remaining;
    }

    public static class PointClockwiseComparator implements Comparator<Point> {
        private final AsteroidField field;
        private final Point station;

        public PointClockwiseComparator(AsteroidField field, Point station) {
            this.field = field;
            this.station = station;
        }

        @Override
        public int compare(Point o1, Point o2) {
            if (o1.equals(o2)) {
                return 0;
            }

            Point o1Delta = calculateDelta(station.x, station.y, o1.x, o1.y);
            int o1Dist = getPointDist(o1, o1Delta);

            Point o2Delta = calculateDelta(station.x, station.y, o2.x, o2.y);
            int o2Dist = getPointDist(o2, o2Delta);

            if (o1Dist < o2Dist) {
                return -1;
            }

            if (o1Dist > o2Dist) {
                return 1;
            }

            double o1Cos = getCos(o1Delta);
            double o2Cos = getCos(o2Delta);

            double o1Sin = getSin(o1Delta);
            double o2Sin = getSin(o2Delta);

            if (o1Cos == o2Cos && o1Sin == o2Sin) {
                return 0;
            }

            if (o1Cos < 0) {
                if (o2Cos >= 0) {
                    return 1;
                } else if (o1Sin < o2Sin) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                if (o2Cos < 0) {
                    return -1;
                } else if (o1Sin < o2Sin) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        public int getPointDist(Point o1, Point o1Delta) {
            List<Point> o1Line = calculateLine(field, station.x, station.y, o1Delta);
            o1Line.removeIf((p) -> !field.getAsteroidLocations().contains(p));
            return o1Line.indexOf(o1) + 1;
        }

        private double getSin(Point o1) {
            double o1Size = Math.sqrt(o1.x * o1.x + o1.y * o1.y);
            return  o1.y / o1Size;
        }

        private double getCos(Point o1) {
            double o1Size = Math.sqrt(o1.x * o1.x + o1.y * o1.y);
            return o1.x / o1Size;
        }
    }
}
