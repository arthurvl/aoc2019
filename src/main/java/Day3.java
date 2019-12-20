import day3.LineSegment;
import day3.IntersectionPoint;
import day3.Orientation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        List<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day3/input"))).lines().collect(Collectors.toList());

        System.out.println(calculateClosestCrossing(input.get(0), input.get(1)));
        System.out.println(calculateNearestCrossing(input.get(0), input.get(1)));
    }

    public static int calculateClosestCrossing(String firstWire, String secondWire) {
        List<LineSegment> firstLineSegments = getSegments(firstWire);
        List<LineSegment> secondLineSegments = getSegments(secondWire);

        int smallestDistance = Integer.MAX_VALUE;

        for(LineSegment first : firstLineSegments) {
            for (LineSegment second : secondLineSegments) {
//                System.out.print(first.toString());
//                System.out.println(second.toString());
                IntersectionPoint p = first.getIntersectionPoint(second);
                if (p != null) {
                    int pointDistance = p.ManhattanDistanceFrom(0,0);
                    if (smallestDistance > pointDistance && pointDistance != 0)
                        smallestDistance = pointDistance;
                }
            }
        }
        return smallestDistance;
    }

    public static int calculateNearestCrossing(String firstWire, String secondWire) {
        List<LineSegment> firstLineSegments = getSegments(firstWire);
        List<LineSegment> secondLineSegments = getSegments(secondWire);

        int currentStepsInFirst = 0;

        for(LineSegment first : firstLineSegments) {
            int currentStepsInSecond = 0;
            for (LineSegment second : secondLineSegments) {
//                System.out.print(first.toString());
//                System.out.print(second.toString());
//                System.out.println(" " + currentStepsInFirst + " " + currentStepsInSecond);
                IntersectionPoint p = first.getIntersectionPoint(second);
                if (p != null && p.ManhattanDistanceFrom(0,0) != 0) {
                    int remainingSteps = p.ManhattanDistanceFrom(first.startX, first.startY) +
                                         p.ManhattanDistanceFrom(second.startX, second.startY);
//                    System.out.print(first.toString());
//                    System.out.print(second.toString());
//                    System.out.println(" " + currentStepsInFirst + " " + currentStepsInSecond + " " + remainingSteps);
                    return currentStepsInFirst + currentStepsInSecond + remainingSteps;
                } else {
                    currentStepsInSecond = currentStepsInSecond + second.length();
                }
            }
            currentStepsInFirst = currentStepsInFirst + first.length();
        }
        return currentStepsInFirst;
    }


    public static List<LineSegment> getSegments(String s) {
        String[] segments = s.split(",");
        int curx = 0;
        int cury = 0;
        List<LineSegment> result = new ArrayList<LineSegment>();
        for (String segment : segments) {
            LineSegment newSegment = getSegment(segment,curx,cury);
            if (newSegment != null) {
                curx = newSegment.endX;
                cury = newSegment.endY;
                result.add(newSegment);
            }
        }

        return result;
    }

    public static LineSegment getSegment(String step, int startx, int starty) {
        if (step.isEmpty()) return null;

        Character direction = step.charAt(0);
        int length = Integer.parseInt(step.substring(1));

        if (direction == 'U') {
            return new LineSegment(startx, starty, Orientation.VERTICAL, startx, starty + length);
        }
        else if (direction == 'D') {
            return new LineSegment(startx, starty, Orientation.VERTICAL, startx, starty - length);
        }
        else if (direction == 'L') {
            return new LineSegment(startx, starty, Orientation.HORIZONTAL, startx - length, starty);
        }
        else if (direction == 'R') {
            return new LineSegment(startx, starty, Orientation.HORIZONTAL, startx + length, starty);
        }

        return null;
    }
}
