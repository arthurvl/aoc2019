package day11;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class PaintRobot {
    private InputStream input;
    private OutputStream output;

    private Point currentLocation = new Point(0, 0);
    private Point direction = new Point(0,1);

    private Map<Point,Integer> panels = new HashMap<>();

    private static Map<Point,Map<Integer, Point>> turns;

    public static final Point up = new Point(0,1);
    public static final Point left = new Point(-1,0);
    public static final Point down = new Point(0,-1);
    public static final Point right = new Point(1,0);

    static  {
        turns = new HashMap<>();

        HashMap<Integer, Point> upTurns = new HashMap<>();
        upTurns.put(0,left);
        upTurns.put(1,right);

        turns.put(up, upTurns);

        HashMap<Integer, Point> leftTurns = new HashMap<>();
        leftTurns.put(0,down);
        leftTurns.put(1,up);

        turns.put(left, leftTurns);

        HashMap<Integer, Point> downTurns = new HashMap<>();
        downTurns.put(0,right);
        downTurns.put(1,left);

        turns.put(down, downTurns);

        HashMap<Integer, Point> rightTurns = new HashMap<>();
        rightTurns.put(0,up);
        rightTurns.put(1,down);

        turns.put(right, rightTurns);
    }

    private Thread t;

    public static Point turn(Point direction, int turn) {
        return turns.get(direction).get(turn);
    }

    public void connect(InputStream controllerStream, OutputStream robotOutput) {
       this.input = controllerStream;
       this.output = robotOutput;
    }

    public int panelsPainted() {
        return panels.size();
    }

    public void run() {
        t = new Thread(() -> doRun());
        t.start();
    }

    public void doRun() {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(output));

        try {
            while (true) {
                out.println(panels.getOrDefault(currentLocation, 0));
                out.flush();

                String read = in.readLine();
                if (read == null) {
                    break;
                }

                int newColor = Integer.parseInt(read);
                panels.put(currentLocation, newColor);

                read = in.readLine();
                if (read == null) {
                    break;
                }

                int newDirection = Integer.parseInt(read);
                direction = turn(direction, newDirection);

                currentLocation = new Point(currentLocation.x + direction.x, currentLocation.y + direction.y);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void join() throws InterruptedException {
        t.join();
    }

    public void printImage(PrintStream out) {
        Set<Point> points = panels.keySet();

        int minX = points.stream().min(Comparator.comparingInt(p -> p.x)).get().x;
        int minY = points.stream().min(Comparator.comparingInt(p -> p.y)).get().y;
        int maxX = points.stream().max(Comparator.comparingInt(p -> p.x)).get().x;
        int maxY = points.stream().max(Comparator.comparingInt(p -> p.y)).get().y;

        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                out.print(panels.getOrDefault(new Point(x,y), 0) == 1 ? "@" : " ");
            }
            out.println();
        }
    }

    public void paintCurrentLocation(int startPanel) {
        panels.put(currentLocation, startPanel);
    }
}
