package com.arturploter.util;

class Pair<T, V> {
    private final T first;
    private final V second;

    Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    T getFirst() {
        return first;
    }

    V getSecond() {
        return second;
    }

    boolean isEqual(Pair<T, V> pair) {
        return this.first.equals(pair.first) && this.second.equals(pair.second);
    }
}
