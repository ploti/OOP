package ru.nsu.fit.oop.arturploter.task_2_2;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The {@code MaxPQ} class represents a generic priority queue.
 * The usual {@code put} and {@code remove} operations are provided.
 * The priority queue is implemented using a binary heap.
 *
 * @param  <P>   the generic type of a priority that uniquely identifies the record.
 * @param  <V>   the generic type of a mapped value.
 */
public class MaxPQ<P extends Comparable<P>, V> implements Collection<V>, Iterable<V> {
    private IndexedLHM<P, V> indexedLHM;

    /**
     * Initializes an empty binary heap.
     */
    public MaxPQ() {
        indexedLHM = new IndexedLHM<>();
    }

    private MaxPQ(IndexedLHM<P, V> indexedLHM) {
        this.indexedLHM = indexedLHM;
    }

    /**
     * Adds a new priority-value pair to the priority queue.
     *
     * @param   priority   the new priority to be added to the priority queue
     * @param   value   the value to be added by the specified priority to the priority queue
     */
    public void put(P priority, V value) {
        if (priority == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }

        if (indexedLHM.put(priority, value) == null) {
            swim(indexedLHM.size());
        }
    }

    /**
     * Finds the value with the highest priority, removes it, and returns it.
     *
     * @return  the value with the highest priority
     */
    public V remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty.");
        }

        P rootPriority = indexedLHM.getKey(0);
        V rootVal;

        if (indexedLHM.getListSize(rootPriority) == 1) {
            indexedLHM.swap(0, indexedLHM.size() - 1);
            rootVal = indexedLHM.remove(rootPriority);
            sinkRootNode();
        } else {
            rootVal = indexedLHM.remove(rootPriority);
        }

        return rootVal;
    }

    /**
     * Returns the priority of the specified object.
     *
     * @param   o   the object whose priority is to be returned
     * @return  the priority of the specified object
     */
    public P getObjectPriority(V o) {
        return indexedLHM.getKeyByValue(o);
    }

    private void swim(int i) {
        while (i > 1 && isLess(i / 2 - 1, i - 1)) {
            indexedLHM.swap(i - 1, i / 2 - 1);
            i /= 2;
        }
    }

    private void sinkRootNode() {
        int i = 1;
        int n = indexedLHM.size();

        while (2 * i <= n) {
            int j = 2 * i;

            if (j < n && isLess(j - 1, j)) {
                j++;
            }

            if (!isLess(i - 1, j - 1)) {
                break;
            }

            indexedLHM.swap(i - 1, j - 1);
            i = j;
        }
    }

    private boolean isLess(int i, int j) {
        return indexedLHM.getKey(i).compareTo(indexedLHM.getKey(j)) < 0;
    }

    @Override
    public int size() {
        return indexedLHM.size();
    }

    @Override
    public boolean isEmpty() {
        return indexedLHM.size() == 0;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean contains(Object o) {
        return indexedLHM.containsValue(o);
    }

    /**
     * Returns an iterator that iterates over the keys in decreasing order.
     *
     * @return  an iterator that iterates over the keys in decreasing order
     */
    @Override
    public Iterator<V> iterator() {
        return new HeapIterator();
    }

    @Override
    public Object[] toArray() {
//        List<List<V>> values = new ArrayList<>(indexedLHM.getLinkedHashMap().values());
        return (indexedLHM.getLinkedHashMap().values()).stream()
                .flatMap(List::stream).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        indexedLHM = new IndexedLHM<>();

    }

    private class HeapIterator implements Iterator<V> {
        private final MaxPQ<P, V> priorityQueueCopy;

        private HeapIterator() {
            priorityQueueCopy = new MaxPQ<>(indexedLHM.clone());
        }

        public boolean hasNext() {
            return !priorityQueueCopy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The priority queue is empty.");
            }

            return priorityQueueCopy.remove();
        }
    }
}