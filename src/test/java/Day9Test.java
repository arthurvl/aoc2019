import day9.BigIntegerComputer;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {
    @Test
    public void halts() {
        BigIntegerComputer intComputer = new BigIntegerComputer("99");
        intComputer.run(new StringReader("1"));
        assertEquals(99, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void adds() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1,0,0,0,99");
        intComputer.run(new StringReader("1"));
        assertEquals(2, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void multiplies() {
        BigIntegerComputer intComputer = new BigIntegerComputer("2,3,0,3,99");
        intComputer.run(new StringReader("1"));
        assertEquals(6, intComputer.valueAtPosition(BigInteger.valueOf(3)).intValue());
    }

    @Test
    public void multipliesToMultipleLocations() {
        BigIntegerComputer intComputer = new BigIntegerComputer("2,4,4,5,99,0");
        intComputer.run(new StringReader("1"));
        assertEquals(9801, intComputer.valueAtPosition(BigInteger.valueOf(5)).intValue());
    }

    @Test
    public void steps() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1,1,1,4,99,5,6,0,99");
        intComputer.run(new StringReader("1"));
        assertEquals(30, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void calculatesWithData() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1,9,10,3,2,3,11,0,99,30,40,50");
        intComputer.run(new StringReader("1"));
        assertEquals(3500, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void multipliesImmediate() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1002,4,3,4,33");
        intComputer.run(new StringReader("1"));
        assertEquals(99, intComputer.valueAtPosition(BigInteger.valueOf(4)).intValue());
    }

    @Test
    public void readsInput() {
        BigIntegerComputer intComputer = new BigIntegerComputer("3,1,99");
        intComputer.run(new StringReader("8"));
        assertEquals(8, intComputer.valueAtPosition(BigInteger.ONE).intValue());

    }

    @Test
    public void writesOutput() {
        BigIntegerComputer intComputer = new BigIntegerComputer("4,2,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals("99\n", output.toString());
    }

    @Test
    public void writesOutputImmediate() {
        BigIntegerComputer intComputer = new BigIntegerComputer("104,12345,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals("12345\n", output.toString());

    }

    @Test
    public void calculatesWithNegativeNumbers() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1101,100,-1,4,0");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(99, intComputer.valueAtPosition(BigInteger.valueOf(4)).intValue());
    }

    @Test
    public void jumpsNonZero() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1105,1,7,1101,12300,45,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(1105, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void jumpsZero() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1106,0,7,1101,12300,45,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(1106, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void lessThanTrue() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1107,0,7,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(1, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void lessThanFalse() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1107,7,0,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(0, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void equalTrue() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1108,0,0,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(1, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void equalFalse() {
        BigIntegerComputer intComputer = new BigIntegerComputer("1108,7,0,0,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(0, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void knowsBaseSetting() {
        BigIntegerComputer intComputer = new BigIntegerComputer("109,7,99");
        StringWriter output = new StringWriter();
        intComputer.run(new StringReader("1"), output);
        assertEquals(109, intComputer.valueAtPosition(BigInteger.ZERO).intValue());
    }

    @Test
    public void multipliesRelative() {
        BigIntegerComputer intComputer = new BigIntegerComputer("109,6,1202,0,3,6,33");
        intComputer.run(new StringReader("1"));
        assertEquals(99, intComputer.valueAtPosition(BigInteger.valueOf(6)).intValue());
    }

    @Test
    public void multipliesRelativeDestination() {
        BigIntegerComputer intComputer = new BigIntegerComputer("109,6,21202,0,3,0,33");
        intComputer.run(new StringReader("1"));
        assertEquals(99, intComputer.valueAtPosition(BigInteger.valueOf(6)).intValue());
    }
}