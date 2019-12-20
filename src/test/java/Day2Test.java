import day2.IntComputer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    @Test
    public void halts() {
        IntComputer intComputer = Day2.getIntcomputer("99");
        intComputer.run();
        assertEquals(99, intComputer.valueAtPosition(0));
    }

    @Test
    public void adds() {
        IntComputer intComputer = Day2.getIntcomputer("1,0,0,0,99");
        intComputer.run();
        assertEquals(2, intComputer.valueAtPosition(0));
    }

    @Test
    public void multiplies() {
        IntComputer intComputer = Day2.getIntcomputer("2,3,0,3,99");
        intComputer.run();
        assertEquals(6, intComputer.valueAtPosition(3));
    }

    @Test
    public void multipliesToMultipleLocations() {
        IntComputer intComputer = Day2.getIntcomputer("2,4,4,5,99,0");
        intComputer.run();
        assertEquals(9801, intComputer.valueAtPosition(5));
    }

    @Test
    public void steps() {
        IntComputer intComputer = Day2.getIntcomputer("1,1,1,4,99,5,6,0,99");
        intComputer.run();
        assertEquals(30, intComputer.valueAtPosition(0));
    }

    @Test
    public void calculatesWithData() {
        IntComputer intComputer = Day2.getIntcomputer("1,9,10,3,2,3,11,0,99,30,40,50");
        intComputer.run();
        assertEquals(3500, intComputer.valueAtPosition(0));
    }
}