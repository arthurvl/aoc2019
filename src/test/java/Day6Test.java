import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {
    @Test
    public void readsSingleEdge() {
        Graph<String, DefaultEdge> minimalGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);
        Day6.addEdge("COM)B", minimalGraph);
        assertEquals("(COM : B)",((DefaultEdge)minimalGraph.edgeSet().toArray()[0]).toString());
    }

    @Test
    public void readsMultipleEdges() {
        Graph<String, DefaultEdge> minimalGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);
        addEdges("COM)B\nB)C", minimalGraph);
        assertEquals(2,minimalGraph.edgeSet().size());
        assertEquals(3,minimalGraph.vertexSet().size());
    }

    private void addEdges(String input, Graph<String, DefaultEdge> graph) {
        Stream<String> edges = Arrays.stream(input.split("\n"));
        Day6.addEdges(edges,graph);
    }

    @Test
    public void countsEdges() {
        Graph<String, DefaultEdge> minimalGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);
        addEdges("COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L", minimalGraph);
        assertEquals(42, Day6.countOrbits(minimalGraph));
    }

    @Test
    public void transfersToSantaQuickly() {
        Graph<String, DefaultEdge> minimalGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);
        addEdges("COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L\nK)YOU\nI)SAN", minimalGraph);
        assertEquals(4, Day6.orbitalTransfersToSanta(minimalGraph));
    }
}