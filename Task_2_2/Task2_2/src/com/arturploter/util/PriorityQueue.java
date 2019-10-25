package com.arturploter.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code PriorityQueue} class represents a generic priority queue.
 * Supports the following operations: <em>insert</em> (takes two parameters, <i>key</i> and <em>value</em>)
 * and <i>removeMax</i>.
 * Implemented with the help of a binary heap.
 *
 * @param <K> is a generic type of a key that uniquely identifies the record.
 * @param <V> is a generic type of a mapped value.
 */
public class PriorityQueue<K extends Comparable<K>, V> implements Iterable<K> {
    private IndexedLinkedHashMap<K, V> binaryHeap;

    /**
     * Initializes an empty indexed linked hash table.
     */
    public PriorityQueue() {
        binaryHeap = new IndexedLinkedHashMap<>();
    }

    private PriorityQueue(IndexedLinkedHashMap<K, V> binaryHeap) {
        this.binaryHeap = binaryHeap;
    }

    /**
     * Adds a new key-value pair to the priority queue.
     *
     * @param key the new key to add to the priority queue
     * @param value the value to be added by the specified key to the priority queue
     * @throws IllegalArgumentException
     */
    public void insert(K key, V value) throws IllegalArgumentException {
        if (binaryHeap.containsKey(key)) {
            throw new IllegalArgumentException("An item with the same key \"" + key.toString()
                    + "\" is already in the priority queue.");
        }

        binaryHeap.put(key, value);

        int n = binaryHeap.size();
        swim(n);
    }

    /**
     * Finds the key-value pair with the highest priority, removes it, and returns it.
     *
     * @return a largest key-value pair of the highest key
     * @throws QueueEmptyException if the priority queue is empty
     */
    public Pair<K, V> removeMax() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }

        K rootKey = binaryHeap.getKey(0);
        V rootVal = (V) binaryHeap.get(rootKey);
        Pair<K, V> maxItem = new Pair<>(rootKey, rootVal);

        int n = binaryHeap.size();
        binaryHeap.swap(0, n - 1);
        binaryHeap.remove(rootKey);

        sink(1);

        return maxItem;
    }

    private void swim(int i) {
        while (i > 1 && isLess(i / 2 - 1, i - 1)) {
            binaryHeap.swap(i - 1, i / 2 - 1);
            i /= 2;
        }
    }

    private void sink(int i) {
        int n = binaryHeap.size();

        while (2 * i <= n) {
            int j = 2 * i;

            if (j < n && isLess(j - 1, j)) {
                j++;
            }

            if (!isLess(i - 1, j - 1)) {
                break;
            }

            binaryHeap.swap(i - 1, j - 1);
            i = j;
        }
    }

    private boolean isLess(int i, int j) {
        return binaryHeap.getKey(i).compareTo(binaryHeap.getKey(j)) < 0;
    }

    private boolean isEmpty() {
        return binaryHeap.size() == 0;
    }

    /**
     * Returns an iterator that iterates over the keys on the priority queue in decreasing order.
     *
     * @return an iterator that iterates over the keys on the priority queue in decreasing order
     */
    @Override
    public Iterator<K> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<K> {
        private PriorityQueue<K, V> copy;

        private HeapIterator() {
            copy = new PriorityQueue<>(binaryHeap);
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException("The requested operation is not supported.");
        }

        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The collection or iterator is empty.");
            }

            K key = null;
            try {
                key = copy.removeMax().getFirst();
            } catch (QueueEmptyException e) {
                System.out.println(e.getMessage());
            }

            return key;
        }
    }
}
