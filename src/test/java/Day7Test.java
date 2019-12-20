import day7.IntComputer;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    @Test
    public void connectsTwoComputers() {
        try {
            IntComputer outputingComputer = new IntComputer("4,2,99");
            IntComputer inputingComputer = new IntComputer("3,0,99");
            outputingComputer.connectInput(new ByteArrayInputStream("1".getBytes()));
            OutputStream outputWriter = new ByteArrayOutputStream();
            inputingComputer.connectOutput(outputWriter);

            Day7.connectWithPipe(outputingComputer, inputingComputer);

            outputingComputer.run();
            inputingComputer.run();

            outputingComputer.join();
            inputingComputer.join();

            assertEquals(99, inputingComputer.valueAtPosition(0));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectsTwoComputersAndWritesOutput() {
        try {
            IntComputer outputingComputer = new IntComputer("4,2,99");
            IntComputer inputingComputer = new IntComputer("3,0,4,0,99");
            outputingComputer.connectInput(new ByteArrayInputStream("1".getBytes()));
            OutputStream outputWriter = new ByteArrayOutputStream();
            inputingComputer.connectOutput(outputWriter);

            Day7.connectWithPipe(outputingComputer, inputingComputer);

            outputingComputer.run();
            inputingComputer.run();

            outputingComputer.join();
            inputingComputer.join();

            assertEquals("99\n", outputWriter.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readsTwoInputsCorrectly() {
        try {
            IntComputer readingComputer = new IntComputer("3,0,3,1,99");
            readingComputer.connectOutput(new ByteArrayOutputStream());
            readingComputer.connectInput(new ByteArrayInputStream("1\n2".getBytes()));

            readingComputer.run();
            readingComputer.join();

            assertEquals(1, readingComputer.valueAtPosition(0));
            assertEquals(2, readingComputer.valueAtPosition(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void calculatesPermutationForEmpty() {
        List<List<Integer>> permutations = Day7.generatePermutations(Collections.EMPTY_LIST);

        assertTrue(permutations.isEmpty());
    }

    @Test
    public void calculatesPermutationForSingle() {
        List<List<Integer>> permutations = Day7.generatePermutations(Collections.singletonList(1));

        assertEquals(1, permutations.size());
        assertIterableEquals(Collections.singleton(1), permutations.get(0));
    }

    @Test
    public void calculatesPermutationForTwo() {
        List<List<Integer>> permutations = Day7.generatePermutations(Arrays.asList(1,2));

        assertEquals(2, permutations.size());
    }

    @Test
    public void calculatesPowerStream() {
        List<Integer> settings = Arrays.asList(2,3);
        int power = Day7.runPowerStream("3,0,3,1,2,0,1,0,4,0,99",settings,1);

        assertEquals(6, power);
    }

    @Test
    public void calculatesPowerStreamForExample1() {
        Pair<Integer, List<Integer>> setting = Day7.calculatePowerStreamValueFor("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0");

        assertIterableEquals(Arrays.asList(4,3,2,1,0), setting.getValue1());
        assertEquals(43210, setting.getValue0());
    }

    @Test
    public void calculatesPowerStreamForExample2() {
        Pair<Integer, List<Integer>> setting = Day7.calculatePowerStreamValueFor("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0");

        assertIterableEquals(Arrays.asList(0,1,2,3,4), setting.getValue1());
        assertEquals(54321, setting.getValue0());
    }

    @Test
    public void calculatesFedBackPowerStream() {
        List<Integer> settings = Arrays.asList(2,3);
        int power = Day7.runFedBackPowerStream("3,0,3,1,2,0,1,0,4,0,99",settings,1);

        assertEquals(6, power);
    }

    @Test
    public void calculatesFedBackPowerStreamForExample1() {
        Pair<Integer, List<Integer>> setting = Day7.calculateFedBackPowerStreamValueFor("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5");

        assertIterableEquals(Arrays.asList(9,8,7,6,5), setting.getValue1());
        assertEquals(139629729, setting.getValue0());
    }

    @Test
    public void calculatesFedBackPowerStreamForExample2() {
        Pair<Integer, List<Integer>> setting = Day7.calculateFedBackPowerStreamValueFor("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10");

        assertIterableEquals(Arrays.asList(9,7,8,5,6), setting.getValue1());
        assertEquals(18216, setting.getValue0());
    }
}