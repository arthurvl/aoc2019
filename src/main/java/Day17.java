import day11.BigIntegerComputer;

import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day17 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day17.class.getResourceAsStream("day17/input"))).lines();

        String program = input.collect(Collectors.joining(","));
        try {
            String image = getImageFrom(program);

            System.out.println(image);

            System.out.println(calculateAlignment(image));
        } catch ( InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getImageFrom(String program) throws InterruptedException {
        BigIntegerComputer controller = new BigIntegerComputer(program);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        controller.connect(new ByteArrayInputStream("".getBytes()), output);

        controller.run();
        controller.join();

        return collectImage(output);
    }

    private static String collectImage(ByteArrayOutputStream output) {
        String unparsed = output.toString();

        List<String> charNumList = new BufferedReader(new StringReader(unparsed)).lines().collect(Collectors.toList());

        StringBuilder b = new StringBuilder();

        for(String charNum : charNumList) {
            int charCode = Integer.parseInt(charNum);
            b.append(Character.toChars(charCode));
        }

        return b.toString();
    }

    public static List<Point> getIntersections(String image) {
        List<Point> result = new ArrayList<>();

        char[][] picture = getPicture(image);

        for(int y = 0; y < picture.length; y++) {
            for(int x = 0; x < picture[0].length; x++) {
                Point p = new Point(x,y);
                if (isIntersection(picture,p)) {
                    result.add(p);
                }
            }
        }

        return result;
    }

    private static boolean isIntersection(char[][] picture, Point p) {
        if (picture[p.y][p.x] == '.') { // not on scaffold so no intersection
            return false;
        }

        int width = picture[0].length;
        int height = picture.length;

        List<Point> neighbors = new ArrayList<>();
        if (p.x > 0) {
            neighbors.add(new Point(p.x - 1, p.y));
        }
        if (p.x < width - 1) {
            neighbors.add(new Point(p.x + 1, p.y));
        }
        if (p.y > 0) {
            neighbors.add(new Point(p.x, p.y - 1));
        }
        if (p.y < height - 1) {
            neighbors.add(new Point(p.x, p.y + 1));
        }

        if (neighbors.size() < 3) { // in a corner so no intersection
            return false;
        }

        boolean neighborsAreOnScaffold = true;

        for (Point n : neighbors) {
            neighborsAreOnScaffold = neighborsAreOnScaffold && picture[n.y][n.x] != '.';
        }

        return neighborsAreOnScaffold;
    }

    private static char[][] getPicture(String image) {
        String[] lines = image.split("\n");
        char[][] result = new char[lines.length][lines[0].length()];

        for (int y = 0; y < result.length; y++ ) {
            for (int x = 0; x < result[0].length; x++) {
                result[y][x] = lines[y].charAt(x);
            }
        }

        return result;
    }

    public static List<Integer> calculateAlignmentParameters(String image) {
        return getIntersections(image).stream().map(Day17::alignmentParameter).collect(Collectors.toList());
    }

    public static Integer alignmentParameter(Point p) {
        return p.x * p.y;
    }

    public static int calculateAlignment(String image) {
        return calculateAlignmentParameters(image).stream().mapToInt(Integer::intValue).sum();
    }
}
