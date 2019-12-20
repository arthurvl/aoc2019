import day5.IntComputer;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    @Test
    public void multipliesImmediate() {
        IntComputer intComputer = new IntComputer("1002,4,3,4,33");
        intComputer.run(new StringReader("1"));
        assertEquals(99, intComputer.valueAtPosition(4));
    }

    @Test
    public void halts() {
        IntComputer intComputer = new IntComputer("99");
        intComputer.run(new StringReader("1"));
        assertEquals(99, intComputer.valueAtPosition(0));
    }

    @Test
    public void adds() {
        IntComputer intComputer = new IntComputer("1,0,0,0,99");
        intComputer.run(new StringReader("1"));
        assertEquals(2, intComputer.valueAtPosition(0));
    }

    @Test
    public void multiplies() {
        IntComputer intComputer = new IntComputer("2,3,0,3,99");
        intComputer.run(new StringReader("1"));
        assertEquals(6, intComputer.valueAtPosition(3));
    }

    @Test
    public void multipliesToMultipleLocations() {
        IntComputer intComputer = new IntComputer("2,4,4,5,99,0");
        intComputer.run(new StringReader("1"));
        assertEquals(9801, intComputer.valueAtPosition(5));
    }

    @Test
    public void steps() {
        IntComputer intComputer = new IntComputer("1,1,1,4,99,5,6,0,99");
        intComputer.run(new StringReader("1"));
        assertEquals(30, intComputer.valueAtPosition(0));
    }

    @Test
    public void calculatesWithData() {
        IntComputer intComputer = new IntComputer("1,9,10,3,2,3,11,0,99,30,40,50");
        intComputer.run(new StringReader("1"));
        assertEquals(3500, intComputer.valueAtPosition(0));
    }

    @Test
    public void readsInput() {
        IntComputer intComputer = new IntComputer("3,1,99");
        intComputer.run(new StringReader("8"));
        assertEquals(8, intComputer.valueAtPosition(1));

    }

    @Test
    public void writesOutput() {
        IntComputer intComputer = new IntComputer("4,2,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals("99\n", output.toString());
    }

    @Test
    public void writesOutputImmediate() {
        IntComputer intComputer = new IntComputer("104,12345,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals("12345\n", output.toString());

    }

    @Test
    public void calculatesWithNegativeNumbers() {
        IntComputer intComputer = new IntComputer("1101,100,-1,4,0");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(99, intComputer.valueAtPosition(4));
    }

    @Test
    public void jumpsNonZero() {
        IntComputer intComputer = new IntComputer("1105,1,7,1101,12300,45,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(1105, intComputer.valueAtPosition(0));
    }

    @Test
    public void jumpsZero() {
        IntComputer intComputer = new IntComputer("1106,0,7,1101,12300,45,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(1106, intComputer.valueAtPosition(0));
    }

    @Test
    public void lessThanTrue() {
        IntComputer intComputer = new IntComputer("1107,0,7,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(1, intComputer.valueAtPosition(0));
    }

    @Test
    public void lessThanFalse() {
        IntComputer intComputer = new IntComputer("1107,7,0,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(0, intComputer.valueAtPosition(0));
    }

    @Test
    public void equalTrue() {
        IntComputer intComputer = new IntComputer("1108,0,0,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(1, intComputer.valueAtPosition(0));
    }

    @Test
    public void equalFalse() {
        IntComputer intComputer = new IntComputer("1108,7,0,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(0, intComputer.valueAtPosition(0));
    }
}