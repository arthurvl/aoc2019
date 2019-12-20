import day11.BigIntegerComputer;
import day15.MazeFiller;
import day15.MazeRunner;

import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day15.class.getResourceAsStream("day15/input"))).lines();

        String program = input.collect(Collectors.joining(","));

        try {
            MazeRunner droid = runMazeRunnerOn(program);
            System.out.println(droid.stepsToOxygen());

            printMap(droid.getSeen());

            MazeFiller filler = new MazeFiller(droid.getSeen(), droid.getOxygenStation());
            filler.fill();

            System.out.println(filler.getMinutesToFill());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printMap(Map<Point, Integer> seen) {
        int minX, maxX, minY, maxY;
        String[] pixels = new String[] {"┼", "·", "o", " ", " ", " ", " ", " ", " "};

        Set<Point> points = seen.keySet();

        minX = points.stream().min((p1,p2) -> Integer.compare(p1.x, p2.x)).get().x;
        maxX = points.stream().max((p1,p2) -> Integer.compare(p1.x, p2.x)).get().x;
        minY = points.stream().min((p1,p2) -> Integer.compare(p1.y, p2.y)).get().y;
        maxY = points.stream().max((p1,p2) -> Integer.compare(p1.y, p2.y)).get().y;

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Point p = new Point(x,y);
                int seenValue = seen.getOrDefault(p, 8);

                System.out.print(pixels[seenValue]);
            }
            System.out.println();
        }
    }

    private static MazeRunner runMazeRunnerOn(String program) throws IOException, InterruptedException {
        BigIntegerComputer controller = new BigIntegerComputer(program);

        MazeRunner droid = new MazeRunner();

        PipedOutputStream controllerOutput = new PipedOutputStream();
        PipedInputStream mazeRunnerInput = new PipedInputStream(controllerOutput);

        PipedOutputStream mazeRunnerOutput = new PipedOutputStream();
        PipedInputStream controllerInput = new PipedInputStream(mazeRunnerOutput);

        controller.connect(controllerInput,controllerOutput);
        droid.connect(mazeRunnerInput, mazeRunnerOutput);

        controller.run();

        droid.run();
        droid.join();

        controller.end();

        return droid;
    }
}
