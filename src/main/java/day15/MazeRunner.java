package day15;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MazeRunner {
    private int curY;
    private int curX;
    private InputStream input;
    private OutputStream output;
    private int stepsToOxygen;
    private Map<Point, Integer> seen;
    private Thread t;
    private int oxygenX;
    private int oxygenY;

    public MazeRunner() {
        this.seen = new HashMap<>();
        this.curX = 0;
        this.curY = 0;
    }

    public int stepsToOxygen() {
        return stepsToOxygen;
    }

    public void run() {
        this.t = new Thread(() -> doRun());
        t.start();
    }

    private void doRun() {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(output));
        try {
            this.stepsToOxygen = walkToOxygenStation(in,out,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    private int walkToOxygenStation(BufferedReader in, PrintWriter out, int curSteps) throws IOException {
        int minSteps = Integer.MAX_VALUE;
        for (int dir = 1; dir <= 4; dir++) {
            int feedback = stepInDirection(dir, in, out);
            if (feedback == 0) {
                // hit wall, we have not moved, check other direction
            } else if (feedback == 2) {
                // found oxygen station in one step
                // we keep on searching to complete our map, then backtrack to where we were,
                // because we might yet find a faster path from our previous location,
                // but not from this location
                int pathLength = walkToOxygenStation(in,out,curSteps + 1);
                oxygenX = curX;
                oxygenY = curY;
                stepBackDirection(dir,in,out);
                minSteps = curSteps + 1;
            } else if (feedback == 1) {
                // found possible start of path, check it out, then backtrack
                // and see if other direction is shorter
                int pathLength = walkToOxygenStation(in,out,curSteps + 1);
                stepBackDirection(dir, in, out);
                if (pathLength != Integer.MAX_VALUE) {
                    // path succeeded
                    minSteps = Math.min(pathLength, minSteps);
                }
            } else {
                // not taken a step in said direction as it has already
                // been taken on another path
            }
        }
        // Checked all directions,
        if (minSteps == Integer.MAX_VALUE) {
            // have not found a valid path
            return Integer.MAX_VALUE;
        } else {
            return minSteps;
        }
    }

    private int stepInDirection(int dir, BufferedReader in, PrintWriter out) throws IOException {
        int[] dxs = new int[] {0,0,-1,1};
        int[] dys = new int[] {1,-1,0,0};
        int nextX = curX + dxs[dir -1];
        int nextY = curY + dys[dir -1];

        if (seen.containsKey(new Point(nextX,nextY))) {
            return -1;
        }

        out.println(dir);
        out.flush();
        int feedback = Integer.parseInt(in.readLine());
        seen.put(new Point(nextX, nextY), feedback);
        if (feedback != 0) {
            curX = nextX;
            curY = nextY;
        }
        return feedback;
    }

    private void stepBackDirection(int dir, BufferedReader in, PrintWriter out) throws IOException {
        int[] dxs = new int[] {0,0,-1,1};
        int[] dys = new int[] {1,-1,0,0};
        int[] inverseDirections = new int[] {2,1,4,3};
        int inverseDirection = inverseDirections[dir - 1];

        curX = curX + dxs[inverseDirection - 1];
        curY = curY + dys[inverseDirection - 1];

        out.println(inverseDirection);
        out.flush();
        in.readLine();
    }

    public void join() throws InterruptedException {
        t.join();
    }

    public void connect(InputStream mazeRunnerInput, OutputStream mazeRunnerOutput) {
        this.input = mazeRunnerInput;
        this.output = mazeRunnerOutput;
    }

    public Map<Point, Integer> getSeen() {
        return seen;
    }

    public Point getOxygenStation() {
        return new Point(oxygenX, oxygenY);
    }

}
