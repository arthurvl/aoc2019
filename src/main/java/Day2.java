import day2.IntComputer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {
    public static IntComputer getIntcomputer(String s) {
        return new IntComputer(s);
    }

    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day2/input"))).lines();

        String program = input.collect(Collectors.joining(","));

        int noun = 12;
        int verb = 2;

        IntComputer computer = runIntComputerOnNounAndVerb(program, noun, verb);

        System.out.println(computer.valueAtPosition(0));


        for (int i = 0; i < 100; i++) {
            for (int j = 0; j< 100; j++) {
                computer  = runIntComputerOnNounAndVerb(program,i,j);

                if (computer.valueAtPosition(0) == 19690720) {
                    System.out.println("i " + i + ", j " + j + " = " + computer.valueAtPosition(0) + " answer " + (i*100+j));
                }
            }
        }
    }

    private static IntComputer runIntComputerOnNounAndVerb(String program, int noun, int verb) {
        IntComputer computer = getIntcomputer(program);

        computer.setValueAtPosition(1,noun);
        computer.setValueAtPosition(2,verb);

        computer.run();
        return computer;
    }
}
