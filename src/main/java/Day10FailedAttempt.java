import org.javatuples.Pair;

import java.awt.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Day10FailedAttempt {
    public static short[][] parsePicture(String pictureString) {
        return parsePicture(Arrays.asList(pictureString.split("\n")));
    }

    private static short[][] parsePicture(List<String> pictureStringLines) {
        if (pictureStringLines.size() <= 0) {
            return new short[0][];
        }
        short[][] picture = new short[pictureStringLines.size()][pictureStringLines.get(0).length()];

        for (int y = 0; y < pictureStringLines.size(); y++) {
            String line = pictureStringLines.get(y);
            for (int x = 0; x < line.length(); x++) {
                picture[y][x] = (short)(line.charAt(x) == '#' ? 1 : 0);
            }
        }

        return picture;
    }

    public static Pair<Point, Integer> findBestMonitoringStation(short[][] picture) {
        int maxCount = 0;
        Point found = new Point();
        for (int y = 0; y< picture.length; y++) {
            for (int x = 0; x < picture[0].length; x++) {
                if (picture[y][x] == 1) {
                    int asteroidCount = countVisibleAsteroids(picture, x, y);
                    if (maxCount <= asteroidCount) {
                        maxCount = asteroidCount;
                        found = new Point(x, y);
                    }
                }
            }
        }
        return new Pair<>(found, maxCount);
    }

    public static int countVisibleAsteroids(short[][] picture, int originX, int originY) {
        int count = 0;
        for (int y = 0; y< picture.length; y++) {
            for (int x = 0; x < picture[0].length; x++) {
                if (picture[y][x] == 1) {
                    count += countVisibleAsteroidsOnLineTo(x, y, picture, originX, originY);
                }
            }
        }
        return count;
    }

    public static int countVisibleAsteroidsOnLineTo(int x, int y, short[][] picture, int originX, int originY) {
        if (x == originX && y == originY) {
            return 0;
        }

        int dx = x - originX;
        int dy = y - originY;

        int gcd = BigInteger.valueOf(dx).gcd(BigInteger.valueOf(dy)).intValue();

        dx = dx / gcd;
        dy = dy / gcd;

        for (int yAcc = originY + dy; 0 <= yAcc && yAcc < picture.length; yAcc += dy) {
            for (int xAcc = originX + dx; 0<= xAcc && xAcc < picture[0].length; xAcc += dx) {
                if (picture[yAcc][xAcc] != 0) {
                    return 1;
                }
            }
        }
        return 0;
    }

}
