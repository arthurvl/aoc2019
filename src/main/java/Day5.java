import day5.IntComputer;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {

    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day5/input"))).lines();

        String program = input.collect(Collectors.joining(","));

        StringWriter output = new StringWriter();

        runIntComputerOn(program, "1", output);

        System.out.println(output.toString());

        output = new StringWriter();

        runIntComputerOn(program, "5", output);

        System.out.println(output.toString());
    }

    private static void runIntComputerOn(String program, String programInput, StringWriter output) {
        IntComputer intComputer = new IntComputer(program);
        intComputer.run(new StringReader(programInput), output);
    }

    private static IntComputer runIntComputerOnInput(String program, int input, OutputStream output) {
        return null;
    }
}
