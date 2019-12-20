import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    @ParameterizedTest
    @CsvSource({"12,2", "14,2", "1969,654", "100756,33583"})
    public void calculatesFuelForMass(int mass, int expectedFuel) {
        int actualFuel = Day1.calculateFuel(mass);
        assertEquals(expectedFuel, actualFuel);
    }

    @ParameterizedTest
    @CsvSource({"2,0"})
    public void calculatesFuelForFuel(int fuelMass, int expectedFuel) {
        int actualFuel = Day1.calculateFuel(fuelMass);
        assertEquals(expectedFuel, actualFuel);
    }

    @ParameterizedTest
    @CsvSource({"1969,966","14,2","100756,50346"})
    public void calculatesFuelForMassWithMass(int mass, int expectedFuel) {
        int actualFuel = Day1.calculateFuelRecursively(mass);
        assertEquals(expectedFuel,actualFuel);
    }
}