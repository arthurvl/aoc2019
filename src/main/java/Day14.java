import day14.ReactionProcess;
import org.jgrapht.Graph;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day14.class.getResourceAsStream("day14/input"))).lines();

        String[] process = input.collect(Collectors.toList()).toArray(new String[0]);

        System.out.println(calculateFuelRequired(process));
        System.out.println(calculateFuelAvailable(process,1000000000000L));
    }

    public static long calculateFuelAvailable(String[] processString, long ore) {
        ReactionProcess process;

        process = parseProcess(processString);

        return calculateReactantsAvailableFromTo(process, "ORE", "FUEL", ore);
    }

    private static long calculateReactantsAvailableFromTo(ReactionProcess process, String from, String to, long fromCount) {
        Map<String, Long> reactants = calculateReactantsFromTo(process, from, to, 1L);
        long ratio = reactants.getOrDefault(from, 0L);
        long fuelSearchStart = fromCount/ratio;
        long fuelSearchEnd = fromCount/(ratio / 2);

        reactants = calculateReactantsFromTo(process, from, to, fuelSearchStart);
        long resultingOreRequirementStart = reactants.get(from);

        reactants = calculateReactantsFromTo(process, from, to, fuelSearchEnd);
        long resultingOreRequirementEnd = reactants.get(from);

        while(fuelSearchStart != fuelSearchEnd - 1) {
            long fuelSearchMid = (fuelSearchEnd - fuelSearchStart)/2 + fuelSearchStart;
            reactants = calculateReactantsFromTo(process, from, to, fuelSearchMid);
            long resultingOreRequirementMid = reactants.get(from);
            if (resultingOreRequirementMid == fromCount) {
                fuelSearchStart = fuelSearchMid;
                break;
            } else if (resultingOreRequirementMid < fromCount) {
                fuelSearchStart = fuelSearchMid;
            } else {
                fuelSearchEnd = fuelSearchMid;
            }
        }

        return fuelSearchStart;
    }

    public static long calculateFuelRequired(String[] input) {
        ReactionProcess process ;

        process = parseProcess(input);

        return calculateReactantsFromTo(process, "ORE", "FUEL", 1).getOrDefault("ORE",0L);
    }

    public static Map<String, Long> calculateReactantsFromTo(ReactionProcess process, String from, String to, long startCount) {
        Map<String, Long> reactants = new HashMap<>();
        reactants.put(to,startCount);

        Iterator<String> iterator = process.getIterator();
        while(iterator.hasNext()) {
            String source = iterator.next();
            Set<DefaultEdge> outgoing = process.graph.outgoingEdgesOf(source);

            for (DefaultEdge edge : outgoing) {
                long toGenerate = reactants.getOrDefault(source, 0L);
                double resultingMultiple = (double)process.toCount.get(edge);
                long reactionSteps = (int)Math.ceil(toGenerate / resultingMultiple);
                String destination = process.graph.getEdgeTarget(edge);
                long requiredMultiple = process.fromCount.get(edge);
                long newRequirement = reactionSteps * requiredMultiple;
                long existingRequirement = reactants.getOrDefault(destination, 0L);
                reactants.put(destination, existingRequirement + newRequirement);
            }
        }

        return reactants;
    }

    public static ReactionProcess parseProcess(String[] input) {
        ReactionProcess process;
        process = new ReactionProcess();

        for(int i = 0; i < input.length; i++) {
            process.addReaction(input[i]);
        }

        return process;
    }

}
