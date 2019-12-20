import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.GraphDelegator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class Day6 {
    public static void main(String[] args) {
        Stream<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day6/input"))).lines();

        Graph<String, DefaultEdge> inputGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);

        addEdges(input,inputGraph);

        System.out.println(countOrbits(inputGraph));
        System.out.println(orbitalTransfersToSanta(inputGraph));

    }

    static void addEdges(Stream<String> edges, Graph<String, DefaultEdge> graph) {
        edges.forEach((edge) -> addEdge(edge,graph));
    }

    static void addEdge(String edge, Graph<String, DefaultEdge> graph) {
        String[] nodes = edge.split("\\)");
        graph.addVertex(nodes[0]);
        graph.addVertex(nodes[1]);
        graph.addEdge(nodes[0], nodes[1]);
    }

    static int countOrbits(Graph<String, DefaultEdge> orbitGraph) {
        DijkstraShortestPath<String, DefaultEdge> dsp = new DijkstraShortestPath<>(orbitGraph);
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> orbitPaths = dsp.getPaths("COM");
        return orbitGraph.vertexSet().stream().mapToInt((v) -> countOrbit(v,orbitPaths)).sum();
    }

    private static int countOrbit(String v, ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> orbitPaths) {
        return orbitPaths.getPath(v).getLength();
    }


    public static int orbitalTransfersToSanta(Graph<String, DefaultEdge> orbitGraph) {
        DijkstraShortestPath<String, DefaultEdge> dsp = new DijkstraShortestPath<>(new AsUndirectedGraph<>(orbitGraph));
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> transferPaths = dsp.getPaths("YOU");
        return transferPaths.getPath("SAN").getLength()-2;
    }
}
