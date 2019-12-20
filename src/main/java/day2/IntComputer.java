package day2;

import java.util.Arrays;

public class IntComputer {
    private int[] memory = new int[6];

    public IntComputer(String s) {
        String[] locationValues = s.split(",");
        memory = Arrays.stream(locationValues).map(Integer::parseInt).mapToInt(Integer::intValue).toArray();
    }

    public void run() {
        int pc = 0;

        while (true) {
            if (memory[pc] == 99) {
                return;
            } else if (memory[pc] == 1) {
                memory[memory[pc+3]] = memory[memory[pc+1]] + memory[memory[pc+2]];
            } else if (memory[pc] == 2) {
                memory[memory[pc+3]] = memory[memory[pc+1]] * memory[memory[pc+2]];
            }
            pc = pc+4;
        }
    }

    public int valueAtPosition(int position) {
        return memory[position];
    }

    public void setValueAtPosition(int position, int value) {
        memory[position] = value;
    }
}
