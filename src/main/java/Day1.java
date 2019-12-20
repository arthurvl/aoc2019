import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day1 {
    public static int calculateFuel(int mass) {
        return Math.max(mass / 3 - 2, 0);
    }

    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day1/input"))).lines();

        int[] masses = input.mapToInt((s) -> Integer.parseInt(s)).toArray();

        IntStream singleFuelRequirements = Arrays.stream(masses).map(Day1::calculateFuel);
        System.out.println(singleFuelRequirements.sum());

        IntStream recursiveFuelRequirements = Arrays.stream(masses).map(Day1::calculateFuelRecursively);
        System.out.println(recursiveFuelRequirements.sum());
    }

    public static int calculateFuelRecursively(int mass) {
        int totalFuel = calculateFuel(mass);
        int currentAddition = totalFuel;
        while(currentAddition > 0) {
            currentAddition = calculateFuel(currentAddition);
            totalFuel += currentAddition;
        }
        return totalFuel;
    }
}
