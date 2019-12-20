import day11.PaintRobot;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {
    @Test
    public void fakeProgramPaints6() {
        String controllerOutput = "1\n0\n0\n0\n1\n0\n1\n0\n0\n1\n1\n0\n1\n0\n";
        InputStream controllerStream = new ByteArrayInputStream(controllerOutput.getBytes());
        OutputStream robotOutput = new ByteArrayOutputStream();

        PaintRobot robot = new PaintRobot();
        robot.connect(controllerStream, robotOutput);

        robot.run();
        try {
            robot.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("0\n0\n0\n0\n1\n0\n0\n0\n", robotOutput.toString());
        assertEquals(6, robot.panelsPainted());
    }

    @Test
    public void turnsCorrectly() {
        Point up = new Point(0,1);
        Point left = new Point(-1,0);
        Point down = new Point(0,-1);
        Point right = new Point(1,0);

        int turnLeft = 0;
        int turnRight = 1;

        assertEquals(up, PaintRobot.turn(right, turnLeft));
        assertEquals(left, PaintRobot.turn(down, turnRight));
    }
}