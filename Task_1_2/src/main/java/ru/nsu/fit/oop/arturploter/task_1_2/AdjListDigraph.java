package ru.nsu.fit.oop.arturploter.task_1_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class AdjListDigraph {
    private final int numOfVertices;
    private Map<Integer, ArrayList<DirEdgeWithWeight>> outgoingEdges;

    AdjListDigraph(int numOfVertices) {
        this.numOfVertices = numOfVertices;
        outgoingEdges = new HashMap<>();
    }

    void addEdge(int u, int v, int weight) {
        outgoingEdges.computeIfAbsent(u, ignored -> new ArrayList<>()).add(new DirEdgeWithWeight(u, v, weight));
    }

    void addEdge(DirEdgeWithWeight edge) {
        outgoingEdges.computeIfAbsent(edge.from(), ignored -> new ArrayList<>()).add(edge);
    }

    int getNumOfVertices() {
        return numOfVertices;
    }

    Iterable<DirEdgeWithWeight> adj(int vertex) {
        return outgoingEdges.getOrDefault(vertex, new ArrayList<>());
    }
}