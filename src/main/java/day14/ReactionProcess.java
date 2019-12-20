package day14;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReactionProcess {
    public final Graph<String, DefaultEdge> graph;
    public final Map<DefaultEdge, Integer> fromCount;
    public final Map<DefaultEdge, Integer> toCount;

    public ReactionProcess() {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        fromCount = new HashMap<>();
        toCount = new HashMap<>();
    }

    public void addReaction(String reaction) {
        String[] reactionParts = reaction.split("=>");
        if (reactionParts.length != 2) {
            return;
        }

        String[] lhss = reactionParts[0].trim().split(",");
        String rhs = reactionParts[1].trim();
        String[] rhsParts = rhs.split(" ");
        int rhsCount = Integer.parseInt(rhsParts[0]);
        String rhsName = rhsParts[1].trim();
        graph.addVertex(rhsName);

        for (String lhs : Arrays.asList(lhss)) {
            String[] lhsParts = lhs.trim().split(" ");
            int lhsCount = Integer.parseInt(lhsParts[0]);
            String lhsName = lhsParts[1].trim();

            graph.addVertex(lhsName);

            graph.addEdge(rhsName,lhsName);

            DefaultEdge e = graph.getEdge(rhsName,lhsName);

            toCount.put(e, rhsCount);
            fromCount.put(e, lhsCount);
        }
    }

    public GraphIterator<String, DefaultEdge> getIterator() {
        return new TopologicalOrderIterator<>(graph);
    }
}
