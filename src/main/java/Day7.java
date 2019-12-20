import day7.IntComputer;
import org.apache.commons.io.output.TeeOutputStream;
import org.javatuples.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day7 {
    public static List<List<Integer>> generatePermutations(List<Integer> ints) {
        if (ints.size() == 0) {
            return new ArrayList<>();
        } else if (ints.size() == 1) {
            return Collections.singletonList(ints);
        }

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for(int i : ints) {
            List<List<Integer>> permutedRemainder = generatePermutations(ints.stream().filter(j -> j != i).collect(Collectors.toList()));
            permutedRemainder.stream().forEach(list -> list.add(i));
            result.addAll(permutedRemainder);
        }

        return result;
    }

    public static int runPowerStream(String program, List<Integer> settings, int startvalue) {
        int currentValue = startvalue;
        for(int i: settings) {
            InputStream settingAndValue = new ByteArrayInputStream((String.valueOf(i) +"\n" + String.valueOf(currentValue)).getBytes());
            OutputStream intermediateOutput = new ByteArrayOutputStream();
            IntComputer computer = new IntComputer(program);
            computer.connect(settingAndValue, intermediateOutput);
            computer.run();
            try {
                computer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentValue = Integer.parseInt(intermediateOutput.toString().trim());
        }
        return currentValue;
    }

    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day7/input"))).lines();

        String program = input.collect(Collectors.joining(","));

        Pair<Integer,List<Integer>> found= calculatePowerStreamValueFor(program);

        System.out.println(found.getValue0().toString());
        System.out.println(found.getValue1().toString());

        found = calculateFedBackPowerStreamValueFor(program);

        System.out.println(found.getValue0().toString());
        System.out.println(found.getValue1().toString());
    }

    public static Pair<Integer, List<Integer>> calculatePowerStreamValueFor(String program) {
        List<List<Integer>> settingPermutations = generatePermutations(Arrays.asList(0,1,2,3,4));

        int currentMax = 0;
        List<Integer> foundSetting = Collections.EMPTY_LIST;
        for(List<Integer> setting: settingPermutations) {
            int newValue = runPowerStream(program,setting,0);
            if (newValue > currentMax) {
                foundSetting = setting;
                currentMax = newValue;
            }
        }
        return new Pair<>(currentMax,foundSetting);
    }

    public static Pair<Integer, List<Integer>> calculateFedBackPowerStreamValueFor(String program) {
        List<List<Integer>> settingPermutations = generatePermutations(Arrays.asList(5,6,7,8,9));

        int currentMax = 0;
        List<Integer> foundSetting = Collections.EMPTY_LIST;

        for(List<Integer> setting: settingPermutations) {
            int newValue = runFedBackPowerStream(program,setting,0);
            if (newValue > currentMax) {
                foundSetting = setting;
                currentMax = newValue;
            }
        }
        return new Pair<>(currentMax,foundSetting);
    }

    public static int runFedBackPowerStream(String program, List<Integer> setting, int startValue) {
        try {
            InputStream settingAndValue = new ByteArrayInputStream((String.valueOf(setting.get(0)) +"\n" + String.valueOf(startValue) +"\n").getBytes());
            OutputStream readOutput = new ByteArrayOutputStream();
            PipedOutputStream feedBackOutput = new PipedOutputStream();
            PipedInputStream feedBackInput = new PipedInputStream(feedBackOutput);
            InputStream initialStream = new SequenceInputStream(settingAndValue, feedBackInput);
            OutputStream teedOutput = new TeeOutputStream(readOutput, feedBackOutput);

            IntComputer[] computers = new IntComputer[setting.size()];
            computers[0] = new IntComputer(program);
            computers[0].connectInput(initialStream);
            for (int i : IntStream.range(1,setting.size()).boxed().collect(Collectors.toList())) {
                computers[i] = new IntComputer(program);
                InputStream settingInput = new ByteArrayInputStream((String.valueOf(setting.get(i))+"\n").getBytes());
                PipedOutputStream out = new PipedOutputStream();
                PipedInputStream in = new PipedInputStream(out);
                InputStream settingAndInput = new SequenceInputStream(settingInput,in);

                computers[i-1].connectOutput(out);
                computers[i].connectInput(settingAndInput);
            }
            computers[setting.size()-1].connectOutput(teedOutput);

            for (int i = 0; i < setting.size(); i++) {
                computers[i].run();
            }

            for (int i = 0; i< setting.size(); i++) {
                computers[i].join();
            }

            String output = readOutput.toString();

            List<String> outputValues = Arrays.asList(output.split("\\n"));

            String lastValue = outputValues.get(outputValues.size() - 1);

            return Integer.valueOf(lastValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    static void connectWithPipe(IntComputer outputingComputer, IntComputer inputingComputer) throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        outputingComputer.connectOutput(out);
        inputingComputer.connectInput(in);
    }
}
