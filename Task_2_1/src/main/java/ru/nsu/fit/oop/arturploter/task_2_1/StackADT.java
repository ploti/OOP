package ru.nsu.fit.oop.arturploter.task_2_1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code StackADT} class implements the stack ADT using a linked list. Supports the <em>push</em>, <em>pop</em>,
 * <em>size</em>, and <em>iteratior</em> operations. The <em>iterator</em> operation returns the {@code Iterator}
 * object to iterate through the items in LIFO order.
 * All operations are performed in constant time.
 *
 * @author  Artur Ploter
 *
 * @param  <T>   the generic type of an object in the stack
 */
public class StackADT<T> implements Iterable<T> {
    private List<T> items;

    /**
     * Constructs an empty stack.
     */
    public StackADT() {
        items = new LinkedList<>();
    }

    /**
     * Inserts an object at the top of the stack.
     *
     * @param  item   object to be inserted
     */
    public void push(T item) {
        items.add(item);
    }

    /**
     * Removes an object at the top of the stack and returns it.
     *
     * @return  an object at the top of the stack
     */
    public T pop() {
        if (items.isEmpty()) {
            throw new StackUnderflowException();
        }

        return items.remove(items.size() - 1);
    }

    /**
     * Returns the number of objects in the stack.
     *
     * @return  the number of objects in the stack
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns an iterator over the stack. The iterator iterates through the objects in the stack in LIFO order.
     *
     * @return  an iterator over the stack
     */
    @Override
    public Iterator<T> iterator() {
        return new StackIterator<T>(size() - 1);
    }

    private class StackIterator<T> implements Iterator<T> {
        int currentIndex;

        public StackIterator(int index) {
            currentIndex = index;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("The requested operations is not supported");
        }

        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new StackUnderflowException();
            }

            //noinspection unchecked
            return (T) items.get(currentIndex--);
        }
    }
}