import day11.BigIntegerComputer;
import day11.PaintRobot;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day11/input"))).lines();

        String program = input.collect(Collectors.joining(","));


        try {
            PaintRobot robot = runPaintRobotOn(program, 0);

            System.out.println(robot.panelsPainted());

            robot = runPaintRobotOn(program, 1);

            System.out.println(robot.panelsPainted());
            robot.printImage(System.out);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static PaintRobot runPaintRobotOn(String program, int startPanel) throws IOException, InterruptedException {
        BigIntegerComputer controller = new BigIntegerComputer(program);
        PaintRobot robot = new PaintRobot();

        robot.paintCurrentLocation(startPanel);

        PipedOutputStream robotOutput = new PipedOutputStream();
        PipedInputStream controllerInput = new PipedInputStream(robotOutput);

        PipedOutputStream controllerOutput = new PipedOutputStream();
        PipedInputStream robotInput = new PipedInputStream(controllerOutput);

        robot.connect(robotInput, robotOutput);
        controller.connect(controllerInput, controllerOutput);

        robot.run();
        controller.run();

        controller.join();
        robot.join();

        return robot;
    }
}
