package ru.nsu.fit.oop.arturploter.task_1_2;

class DirEdgeWithWeight {
    private final int u;
    private final int v;
    private final int weight;

    DirEdgeWithWeight(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    int from() {
        return u;
    }

    int to() {
        return v;
    }

    int getWeight() {
        return weight;
    }
}