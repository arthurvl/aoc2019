import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day16.class.getResourceAsStream("day16/input"))).lines();

        String in = input.collect(Collectors.joining());

        String after100Phases = applyMultiPhase(in,100);

        String first8 = after100Phases.substring(0,8);

        System.out.println(first8);
    }

    public static String applySinglePhase(String from) {
        List<Short> input = splitIntoDigits(from);
        List<Short> output = applySinglePhase(input);
        return digitsToString(output);
    }

    public static List<Short> applySinglePhase(List<Short> input) {
        List<Short> result = new ArrayList<>(input.size());

        for (int i = 0; i < input.size(); i++) { // walk through the input digits
            short newDigit = 0;
            int patternSize = (i + 1) * 4;
            int patternOccurrences = (int)Math.ceil((double)input.size() / (double)patternSize);
            for (int j = 0; j < patternOccurrences && i + j*patternSize < input.size(); j++) { // walk through the pattern occurrences
                for (int k = 0; k < i + 1 && i + j*patternSize + k < input.size(); k++) { // walk through the pattern parts
                    int oneIndex = i + j*patternSize + k;
                    int minusOneIndex = i + j*patternSize + patternSize / 2 + k;
//                    System.out.println("i = " + i + ", j = " + j + ", k = " + k + ", 1idx = " + oneIndex + ", -1idx = " + minusOneIndex);
//                    System.out.println(digitsToString(input));
//                    System.out.println(" ".repeat(oneIndex) + "^");
//                    System.out.println(" ".repeat(minusOneIndex) + "^");
                    newDigit += input.get(oneIndex);
                    if (minusOneIndex < input.size()) {
                        newDigit -= input.get(minusOneIndex);
                    }
                }
            }
            newDigit = (short)Math.floorMod(Math.abs(newDigit), 10);
//            System.out.println(" ".repeat(i) + newDigit);
            result.add(newDigit);
        }

        return result;
    }

    public static String digitsToString(List<Short> input) {
        return input.stream().map((s) -> s.toString()).collect(Collectors.joining());
    }

    private static List<Short> splitIntoDigits(String from) {
        List<Short> result = new ArrayList<>(from.length());

        for(int i = 0; i < from.length(); i++) {
            result.add(Short.parseShort(from.substring(i,i+1)));
        }

        return result;
    }

    public static String applyMultiPhase(String from, int count) {
        List<Short> input = splitIntoDigits(from);
        List<Short> output = applySinglePhase(input);
        for (int i = 0; i < count - 1; i++){
            output = applySinglePhase(output);
        }
        return digitsToString(output);
    }
}
