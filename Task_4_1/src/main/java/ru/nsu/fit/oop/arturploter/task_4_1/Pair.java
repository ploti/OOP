package ru.nsu.fit.oop.arturploter.task_4_1;

class Pair {
    private final int first;
    private int second;

    Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    int getFirst() {
        return first;
    }

    int getSecond() {
        return second;
    }

    void decrementSecond() {
        second--;
    }
}