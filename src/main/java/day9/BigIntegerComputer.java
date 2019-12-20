package day9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BigIntegerComputer {
    private LinkedHashMap<BigInteger,BigInteger> memory;
    private BigInteger base = BigInteger.ZERO;

    public BigIntegerComputer(String s) {
        String[] locationValues = s.split(",");
        List<BigInteger> program = Arrays.stream(locationValues).map(BigInteger::new).collect(Collectors.toList());
        memory = new LinkedHashMap<>(program.size());
        for(int i = 0; i < program.size(); i++) {
            memory.put(BigInteger.valueOf(i), program.get(i));
        }
    }

    public void run(Reader input) {
        run(input, new StringWriter());
    }

    public void run(Reader input, StringWriter output) {
        BigInteger pc = BigInteger.ZERO;

        BufferedReader bufferedInput = new BufferedReader(input);

        while (true) {
            BigInteger instruction = memory.get(pc).mod(BigInteger.valueOf(100));
            switch(instruction.intValue())
            {
                case 1:
                    pc = pc.add(executeAddition(pc));
                    break;
                case 2:
                    pc = pc.add(executeMultiplication(pc));
                    break;
                case 3:
                    pc = pc.add(readInput(pc, bufferedInput));
                    break;
                case 4:
                    pc = pc.add(writeOutput(pc, output));
                    break;
                case 5:
                    pc = jumpNonZero(pc);
                    break;
                case 6:
                    pc = jumpZero(pc);
                    break;
                case 7:
                    pc = pc.add(lessThan(pc));
                    break;
                case 8:
                    pc = pc.add(equalTo(pc));
                    break;
                case 9:
                    pc = pc.add(setBase(pc));
                    break;
                case 99:
                    return;
                default:
                    if (pc.compareTo(memory.keySet().stream().max(BigInteger::compareTo).get()) > 0)  {
                        System.out.println("Ran beyond memory");
                    } else {
                        System.out.println("Invalid instruction " + memory.get(pc).toString() + " at " + pc );
                    }
                    return;
            }
        }
    }

    private BigInteger setBase(BigInteger pc) {
        base = base.add(getArg(pc,1));
        return BigInteger.valueOf(2);
    }

    private BigInteger lessThan(BigInteger pc) {
        setValueAtPosition(getDest(pc,3), getArg(pc, 1).compareTo( getArg(pc,2)) < 0 ? BigInteger.ONE : BigInteger.ZERO);
        return BigInteger.valueOf((4));
    }

    private BigInteger equalTo(BigInteger pc) {
        setValueAtPosition(getDest(pc,3), getArg(pc, 1).equals( getArg(pc,2)) ? BigInteger.ONE : BigInteger.ZERO);
        return BigInteger.valueOf((4));
    }

    private BigInteger jumpZero(BigInteger pc) {
        if (getArg(pc,1).equals(BigInteger.ZERO)) {
            return getArg(pc,2);
        }
        return pc.add(BigInteger.valueOf(3));
    }

    private BigInteger jumpNonZero(BigInteger pc) {
        if (!getArg(pc,1).equals(BigInteger.ZERO)) {
            return getArg(pc,2);
        }
        return pc.add(BigInteger.valueOf(3));
    }

    private BigInteger writeOutput(BigInteger pc, StringWriter output) {
        output.write(String.valueOf(getArg(pc, 1)));
        output.write("\n");
        return BigInteger.valueOf(2);
    }

    private BigInteger readInput(BigInteger pc, BufferedReader input) {
        try {
            BigInteger valueRead = new BigInteger(input.readLine());
            setValueAtPosition(getDest(pc,1), valueRead);
        } catch (IOException e) {
        }
        return BigInteger.valueOf(2);
    }

    private BigInteger executeMultiplication(BigInteger pc) {
        BigInteger arg1 = getArg(pc,1);
        BigInteger arg2 = getArg(pc,2);
        BigInteger result = arg1.multiply(arg2);
        setValueAtPosition(getDest(pc,3), result);
        return BigInteger.valueOf((4));
    }

    private BigInteger getArg(BigInteger pc, int i) {
        if (isImmediate(pc,i)) {
            return getValueAtOffset(pc, i);
        } else if (isRelative(pc,i)) {
            return getValueAtOffset(base, getValueAtOffset(pc, i).intValue());
        }
        return valueAtPosition(getValueAtOffset(pc, i));
    }

    private BigInteger getDest(BigInteger pc, int i) {
        if (isRelative(pc,i)) {
            return base.add(getValueAtOffset(pc, i));
        }
        return getValueAtOffset(pc, i);
    }

    private BigInteger getValueAtOffset(BigInteger pc, int i) {
        return valueAtPosition(pc.add(BigInteger.valueOf(i)));
    }

    private boolean isImmediate(BigInteger pc, int i) {
        int parameterMode = valueAtPosition(pc).intValue() / (i == 1 ? 100 : 1000) % 10;
        if (parameterMode == 1) {
            return true;
        }
        return false;
    }

    private boolean isRelative(BigInteger pc, int i) {
        int parameterMode = valueAtPosition(pc).intValue() / (i == 1 ? 100 : (i == 2) ? 1000 : 10000) % 10;
        if (parameterMode == 2) {
            return true;
        }
        return false;
    }

    private BigInteger executeAddition(BigInteger pc) {
        setValueAtPosition(getDest(pc,3), getArg(pc, 1) .add( getArg(pc,2)));
        return BigInteger.valueOf((4));
    }

    public BigInteger valueAtPosition(BigInteger position) {
        BigInteger value = memory.get(position);
        return value == null ? BigInteger.ZERO : value;
    }

    public void setValueAtPosition(BigInteger position, BigInteger value) {
        memory.put(position, value);
    }

}
