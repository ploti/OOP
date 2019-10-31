package ru.nsu.fit.oop.arturploter.task_1_2;

import java.util.*;

class DirectedCycleGraph {
    private List<Integer> visited;
    private List<Integer> toBeProcessed;
    private boolean hasCycle;

    DirectedCycleGraph(AdjListDigraph graph) {
        visited = new ArrayList<>();
        toBeProcessed = new ArrayList<>();

        for (int v = 0; v < graph.getNumOfVertices(); v++) {
            if (!visited.contains(v)) {
                depthFirstSearch(graph, v);
            }
        }
    }

    boolean hasCycle() {
        return hasCycle;
    }

    private void depthFirstSearch(AdjListDigraph graph, int u) {
        visited.add(u);
        toBeProcessed.add(u);

        for (DirEdgeWithWeight edge : graph.adj(u)) {
            int v = edge.to();

            if (hasCycle) {
                return;
            } else if (!visited.contains(v)) {
                depthFirstSearch(graph, v);
            } else if (toBeProcessed.contains(v)) {
                hasCycle = true;
                return;
            }
        }

        toBeProcessed.remove((Integer) u);
    }
}
