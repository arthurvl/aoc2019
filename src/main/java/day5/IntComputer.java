package day5;

import java.io.*;
import java.util.Arrays;

public class IntComputer {
    private int[] memory = new int[6];

    public IntComputer(String s) {
        String[] locationValues = s.split(",");
        memory = Arrays.stream(locationValues).map(Integer::parseInt).mapToInt(Integer::intValue).toArray();
    }

    public void run(Reader input) {
        run(input, new StringWriter());
    }

    public void run(Reader input, StringWriter output) {
        int pc = 0;

        BufferedReader bufferedInput = new BufferedReader(input);

        while (true) {
            int instruction = memory[pc] % 100;
            switch(instruction)
            {
                case 1:
                    pc += executeAddition(pc);
                    break;
                case 2:
                    pc += executeMultiplication(pc);
                    break;
                case 3:
                    pc += readInput(pc, bufferedInput);
                    break;
                case 4:
                    pc += writeOutput(pc, output);
                    break;
                case 5:
                    pc = jumpNonZero(pc);
                    break;
                case 6:
                    pc = jumpZero(pc);
                    break;
                case 7:
                    pc += lessThan(pc);
                    break;
                case 8:
                    pc += equalTo(pc);
                    break;
                case 99:
                    return;
                default:
                    if (pc > memory.length) {
                        System.out.println("Ran beyond memory");
                    } else {
                        System.out.println("Invalid instruction " + memory[pc] + " at " + pc );
                    }
                    return;
            }
        }
    }

    private int lessThan(int pc) {
        memory[memory[pc +3]] = getArg(pc, 1) < getArg(pc,2) ? 1 : 0;
        return 4;
    }

    private int equalTo(int pc) {
        memory[memory[pc +3]] = getArg(pc, 1) == getArg(pc,2) ? 1 : 0;
        return 4;
    }

    private int jumpZero(int pc) {
        if (getArg(pc,1) == 0) {
            return getArg(pc,2);
        }
        return pc + 3;
    }

    private int jumpNonZero(int pc) {
        if (getArg(pc,1) != 0) {
            return getArg(pc,2);
        }
        return pc + 3;
    }

    private int writeOutput(int pc, StringWriter output) {
        output.write(String.valueOf(getArg(pc, 1)));
        output.write("\n");
        return 2;
    }

    private int readInput(int pc, BufferedReader input) {
        try {
            int valueRead = Integer.parseInt(input.readLine());
            memory[memory[pc + 1]] = valueRead;
        } catch (IOException e) {
        }
        return 2;
    }

    private int executeMultiplication(int pc) {
        memory[memory[pc + 3]] = getArg(pc, 1) * getArg(pc,2);
        return 4;
    }

    private int getArg(int pc, int i) {
        if (isImmediate(pc,i)) {
            return memory[pc + i];
        }
        return memory[memory[pc + i]];
    }

    private boolean isImmediate(int pc, int i) {
        int parameterMode = memory[pc] / (i == 1 ? 100 : 1000) % 10;
        if (parameterMode == 1) {
            return true;
        }
        return false;
    }

    private int executeAddition(int pc) {
        memory[memory[pc + 3]] = getArg(pc, 1) + getArg(pc,2);
        return 4;
    }

    public int valueAtPosition(int position) {
        return memory[position];
    }

    public void setValueAtPosition(int position, int value) {
        memory[position] = value;
    }

}
