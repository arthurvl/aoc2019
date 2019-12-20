import day12.AxisPosition;
import day12.Moon;
import day12.Point3D;
import org.javatuples.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.util.function.ToIntFunction;

public class Day12 {
    public static void main(String[] args) {
        List<Moon> moons = Arrays.asList(
            new Moon(1, new Point3D(1, -4, 3)),
            new Moon(2, new Point3D(-14, 9, -4)),
            new Moon(3, new Point3D(-4, -6, 7)),
            new Moon(4, new Point3D(6, -9, -11)));

        List<List<Moon>> afterSteps = applySteps(1000, moons);

        System.out.println(getEnergy(afterSteps.get(1000)));

        System.out.println(findRepeatStep(moons));
    }

    public static Pair<Point3D, Point3D> calculateGravityAdjustments(Point3D first, Point3D second) {
        return new Pair<>(
                new Point3D(Integer.compare(second.x,first.x), Integer.compare(second.y,first.y), Integer.compare(second.z, first.z)),
                new Point3D(Integer.compare(first.x,second.x), Integer.compare(first.y,second.y), Integer.compare(first.z, second.z)));
    }

    public static <T> List<Pair<T, T>> generatePairs(List<T> elements) {
        if (elements.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        if (elements.size() == 1) {
            return Collections.EMPTY_LIST;
        }

        List<Pair<T,T>> result = new ArrayList<>();

        T first = elements.get(0);
        List<T> remaining = new ArrayList<>(elements);
        remaining.remove(first);

        for (T element : remaining) {
            result.add(new Pair(first, element));
        }

        result.addAll(generatePairs(remaining));

        return result;
    }

    public static List<Moon> applyStep(List<Moon> moons) {
        List<Pair<Moon,Moon>> moonPairs = generatePairs(moons);

        Map<Pair<Moon,Moon>, Pair<Point3D, Point3D>> adjustments = new HashMap<>();
        for (Pair<Moon,Moon> moonPair : moonPairs) {
            adjustments.put(moonPair, calculateGravityAdjustments(moonPair.getValue0().position, moonPair.getValue1().position));
        }

        Map<Moon, List<Point3D>> collectedAdjustments = new HashMap<>();
        for (Moon moon : moons) {
            collectedAdjustments.put(moon, new ArrayList<Point3D>());
        }

        for (Map.Entry<Pair<Moon,Moon>, Pair<Point3D,Point3D>> mmpp : adjustments.entrySet()) {
            collectedAdjustments.get(mmpp.getKey().getValue0()).add(mmpp.getValue().getValue0());
            collectedAdjustments.get(mmpp.getKey().getValue1()).add(mmpp.getValue().getValue1());
        }

        Map<Moon, Point3D> collapsedAdjustments = new HashMap<>();
        for (Map.Entry<Moon, List<Point3D>> entry : collectedAdjustments.entrySet()) {
            Point3D collapsed = new Point3D(0,0,0);
            for (Point3D adjustment : entry.getValue()) {
                collapsed = collapsed.add(adjustment);
            }
            collapsedAdjustments.put (entry.getKey(), collapsed);
        }

        Map<Moon,Point3D> newVelocities = new HashMap<>();
        for (Map.Entry<Moon, Point3D> entry : collapsedAdjustments.entrySet()) {
            Moon moon = entry.getKey();
            Point3D adjustment = entry.getValue();
            newVelocities.put(moon, moon.velocity.add(adjustment));
        }

        List<Moon> newMoons = new ArrayList<>();
        for (Map.Entry<Moon, Point3D> entry: newVelocities.entrySet()) {
            Moon moon = entry.getKey();
            Point3D newVelocity = entry.getValue();
            newMoons.add(new Moon(moon.id, moon.position.add(newVelocity), newVelocity));
        }

        return newMoons;
    }

    public static List<List<Moon>> applySteps(int steps, List<Moon> moons) {
        List<List<Moon>> moonsHistory = new ArrayList<>();
        moonsHistory.add(moons);
        List<Moon> currentMoons = moons;
        for (int i = 0; i < steps; i++) {
            currentMoons = applyStep(currentMoons);
            moonsHistory.add(currentMoons);
        }
        return moonsHistory;
    }

    public static int getEnergy(List<Moon> moons) {
        return moons.stream().mapToInt(m -> m.energy()).sum();
    }

    public static BigInteger findRepeatStep(List<Moon> moons) {
        List<Moon> currentMoons = moons;
        Map<AxisPosition,Integer> seenAt = new HashMap<>();

        Map<ToIntFunction<Moon>, Pair<Integer,Integer>> repeated = new HashMap<>();

        int currentStep = 0;

        while(!repeatsall(repeated)) {
            List<AxisPosition> positions = getPositions(currentMoons);
            for(AxisPosition pos : positions) {
                if (seenAt.containsKey(pos)) {
                    if (!repeated.containsKey(pos.getAxisReader())) {
                        repeated.put(pos.getAxisReader(), new Pair<>(currentStep, seenAt.get(pos)));
                    }
                }
                else {
                    seenAt.put(pos, currentStep);
                }
            }
            currentStep ++;
            currentMoons = applyStep(currentMoons);
        }

        BigInteger totalSteps = BigInteger.ONE;
        Integer offset = 0;
        for (Pair<Integer,Integer> repeatSteps : repeated.values()) {
            BigInteger period = BigInteger.valueOf((repeatSteps.getValue0() - repeatSteps.getValue1()));
            if (repeatSteps.getValue1() > offset) {
                offset = repeatSteps.getValue1();
            }
            BigInteger gcd = totalSteps.gcd(period);
            totalSteps = totalSteps.multiply(period).abs().divide(gcd);
        }

        return totalSteps;
    }

    private static boolean repeatsall(Map<ToIntFunction<Moon>, Pair<Integer,Integer>> repeated) {
        for (ToIntFunction<Moon> axisGetter : Moon.axisGetters()) {
            if (!repeated.containsKey(axisGetter)) {
                return false;
            }
        }
        return true;
    }

    private static List<AxisPosition> getPositions(List<Moon> currentMoons) {
        List<AxisPosition> result = new ArrayList<>();

        for (ToIntFunction<Moon> axisGetter : Moon.axisGetters()) {
            result.add(new AxisPosition(currentMoons, axisGetter));
        }


        return result;
    }
}
