package ru.nsu.fit.oop.arturploter.task_2_2;

import java.util.Iterator;

/**
 * The {@code PriorityQueueADT} class represents a generic priority queue.
 * The usual {@code insert} and {@code removeMax} operations are provided.
 * The priority queue is implemented using a binary heap.
 *
 * @param  <K>   the generic type of a key that uniquely identifies the record.
 * @param  <V>   the generic type of a mapped value.
 */
public class PriorityQueueADT<K extends Comparable<K>, V> implements Iterable<K> {
    private IndexedLinkedHashMap<K, V> binaryHeap;

    /**
     * Initializes an empty binary heap.
     */
    public PriorityQueueADT() {
        binaryHeap = new IndexedLinkedHashMap<>();
    }

    private PriorityQueueADT(IndexedLinkedHashMap<K, V> binaryHeap) {
        this.binaryHeap = binaryHeap;
    }

    /**
     * Adds a new key-value pair to the priority queue.
     *
     * @param   key   the new key to add to the priority queue
     * @param   value   the value to be added by the specified key to the priority queue
     */
    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
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
     * @return  the largest key-value pair of the highest key
     */
    public Pair<K, V> removeMax() {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }

        K rootKey = binaryHeap.getKey(0);
        //noinspection unchecked
        V rootVal = (V) binaryHeap.get(rootKey);
        Pair<K, V> maxItem = new Pair<>(rootKey, rootVal);

        int n = binaryHeap.size();
        binaryHeap.swap(0, n - 1);
        binaryHeap.remove(rootKey);

        sinkRootNode();

        return maxItem;
    }

    private void swim(int i) {
        while (i > 1 && isLess(i / 2 - 1, i - 1)) {
            binaryHeap.swap(i - 1, i / 2 - 1);
            i /= 2;
        }
    }


    private void sinkRootNode() {
        int i = 1;
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
     * Returns an iterator that iterates over the keys in decreasing order.
     *
     * @return  an iterator that iterates over the keys in decreasing order
     */
    @Override
    public Iterator<K> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<K> {
        private PriorityQueueADT<K, V> copy;

        private HeapIterator() {
            copy = new PriorityQueueADT<>(binaryHeap);
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException("The requested operation is not supported.");
        }

        public K next() {
            if (!hasNext()) {
                throw new QueueEmptyException();
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
