package day15;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class MazeFiller {
    private final Map<Point, Integer> seen;
    private final Map<Point, Integer> minutes;
    private final Point startingPoint;
    private int minutesToFill;
    private int curX;
    private int curY;

    public MazeFiller(Map<Point, Integer> seen, Point oxygenStation) {
        this.seen = seen;
        this.startingPoint = oxygenStation;
        this.minutes = new HashMap<>();
    }

    public void fill() {
        this.curX= startingPoint.x;
        this.curY= startingPoint.y;
        walkFrom(0);
    }

    private void walkFrom(int curSteps) {
        minutesToFill = Math.max(minutesToFill, curSteps);
        for (int dir = 1; dir <= 4; dir++) {
            int feedback = stepInDirection(dir, curSteps);
            if (feedback <= 0) {
                // hit wall or space we can reach quicker,
                // we have not moved, check other direction
            } else  {
                // found open path, seed through this
                walkFrom(curSteps + 1);
                stepBackDirection(dir);
            }
        }
    }

    private int stepInDirection(int dir, int minutesToHere) {
        int[] dxs = new int[] {0,0,-1,1};
        int[] dys = new int[] {1,-1,0,0};
        int nextX = curX + dxs[dir -1];
        int nextY = curY + dys[dir -1];

        Point next = new Point(nextX, nextY);

        if (minutes.containsKey(next) && minutes.get(next) < minutesToHere) {
            return -1;
        }

        int feedback = seen.get(next);
        minutes.put(next,minutesToHere);
        if (feedback != 0) {
            curX = nextX;
            curY = nextY;
        }
        return feedback;
    }

    private void stepBackDirection(int dir) {
        int[] dxs = new int[] {0,0,-1,1};
        int[] dys = new int[] {1,-1,0,0};
        int[] inverseDirections = new int[] {2,1,4,3};
        int inverseDirection = inverseDirections[dir - 1];

        curX = curX + dxs[inverseDirection - 1];
        curY = curY + dys[inverseDirection - 1];
    }

    public int getMinutesToFill() {
        return minutesToFill;
    }
}
