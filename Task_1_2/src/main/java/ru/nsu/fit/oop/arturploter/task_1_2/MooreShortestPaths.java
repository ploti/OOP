package ru.nsu.fit.oop.arturploter.task_1_2;

import java.util.*;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

class MooreShortestPaths {
    private final List<Long> distTo;
    private final Map<Integer, DirEdgeWithWeight> edgeTo;
    private final Queue<Integer> queue;
    private int timesRelaxed;
    private boolean hasNegativeCycle;

    MooreShortestPaths(AdjListDigraph graph, int source) {
        distTo = LongStream.range(0, graph.getNumOfVertices()).mapToObj(i -> Long.MAX_VALUE).collect(toList());
        edgeTo = new HashMap<>();
        queue = new LinkedList<>();
        timesRelaxed = 0;
        hasNegativeCycle = false;

        distTo.set(source, 0L);
        queue.add(source);

        while (!queue.isEmpty() && !hasNegativeCycle) {
            int vertex = queue.remove();
            relax(graph, vertex);
        }
    }

    long distTo(int dest) {
        if (hasNegativeCycle) {
            throw new UnsupportedOperationException("Negative cycle is reachable from the source vertex.");
        }
        if (distTo.get(dest) == Long.MAX_VALUE) {
            throw new UnsupportedOperationException(Integer.sum(dest, 1)
                    + " is unreachable from the source vertex.");
        }

        return distTo.get(dest);
    }

    private void relax(AdjListDigraph graph, int u) {
        for (DirEdgeWithWeight edge : graph.adj(u)) {
            int v = edge.to();
            if (distTo.get(v) > distTo.get(u) + edge.getWeight()) {
                distTo.set(v, distTo.get(u) + edge.getWeight());
                edgeTo.put(v, edge);

                if (!queue.contains(v)) {
                    queue.add(v);
                }
            }

            if (++timesRelaxed % graph.getNumOfVertices() == 0) {
                findNegativeCycle(graph.getNumOfVertices());
                if (hasNegativeCycle) {
                    return;
                }
            }
        }
    }

    private void findNegativeCycle(int numOfVertices) {
        AdjListDigraph shortestPathTree = new AdjListDigraph(numOfVertices);
        for (int v = 0; v < numOfVertices; v++) {
            if (edgeTo.containsKey(v)) {
                shortestPathTree.addEdge(edgeTo.get(v));
            }
        }

        DirectedCycleGraph cycleGraph = new DirectedCycleGraph(shortestPathTree);
        hasNegativeCycle = cycleGraph.hasCycle();
    }
}