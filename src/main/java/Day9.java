import day9.BigIntegerComputer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day9/input"))).lines();

        String program = input.collect(Collectors.joining(","));

        StringWriter output = new StringWriter();

        runIntComputerOn(program, "1", output);

        System.out.println(output.toString());

        output = new StringWriter();

        runIntComputerOn(program, "2", output);

        System.out.println(output.toString());
    }

    private static void runIntComputerOn(String program, String programInput, StringWriter output) {
        BigIntegerComputer intComputer = new BigIntegerComputer(program);
        intComputer.run(new StringReader(programInput), output);
    }
}
