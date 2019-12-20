
import day11.BigIntegerComputer;
import day13.ArcadeCabinet;

import java.io.*;
import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day13.class.getResourceAsStream("day13/input"))).lines();

        String program = input.collect(Collectors.joining(","));

        try {
            ArcadeCabinet cabinet = runCabinetOn(program);

            System.out.println(cabinet.tileCount(2));

            cabinet = runCabinetWithQuartersAndAIOn(program);

            System.out.println(cabinet.getScore());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ArcadeCabinet runCabinetOn(String program) throws IOException, InterruptedException {
        BigIntegerComputer controller = new BigIntegerComputer(program);

        ArcadeCabinet cabinet = new ArcadeCabinet();

        PipedOutputStream controllerOutput = new PipedOutputStream();
        PipedInputStream cabinetInput = new PipedInputStream(controllerOutput);

        cabinet.connectInput(cabinetInput);
        controller.connectOutput(controllerOutput);
        controller.connectInput(new ByteArrayInputStream("".getBytes()));

        cabinet.run();
        controller.run();
        controller.join();
        cabinet.join();

        return cabinet;
    }

    private static ArcadeCabinet runCabinetWithQuartersAndAIOn(String program) throws IOException, InterruptedException {
        BigIntegerComputer controller = new BigIntegerComputer(program);
        controller.setValueAtPosition(BigInteger.ZERO, BigInteger.valueOf(2));

        ArcadeCabinet cabinet = new ArcadeCabinet();
        cabinet.startAI();

        PipedOutputStream cabinetOutput = new PipedOutputStream();
        PipedInputStream controllerInput = new PipedInputStream(cabinetOutput);
        PipedOutputStream controllerOutput = new PipedOutputStream();
        PipedInputStream cabinetInput = new PipedInputStream(controllerOutput);

        cabinet.connectInput(cabinetInput);
        cabinet.connectOutput(cabinetOutput);
        controller.connectOutput(controllerOutput);
        controller.connectInput(controllerInput);

        cabinet.run();
        controller.run();
        controller.join();
        cabinet.join();

        return cabinet;
    }
}
