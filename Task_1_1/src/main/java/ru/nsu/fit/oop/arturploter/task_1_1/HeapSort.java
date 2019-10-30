package ru.nsu.fit.oop.arturploter.task_1_1;

import java.util.ArrayList;
import java.util.Collections;

/**
 *  The {@code HeapSort} class provides a {@code sort} static method for heapsorting a generic list of objects which
 *  classes implement the Comparable interface.
 *
 *  @author  Artur Ploter
 */
public class HeapSort {
    /**
     * Rearranges the list in ascending order, using the natural order.
     *
     * @param   items   the generic list to be sorted
     * @param   <T>   generic parameter T
     * @return  sorted generic list
     */
    public static <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> items) {
        heapify(items, items.size());

        int end = items.size() - 1;
        while (end > 0) {
            Collections.swap(items, 0, end--);
            sink(items, 0, end);
        }

        return new ArrayList<>(items);
    }

    private static <T extends Comparable<T>> void heapify(ArrayList<T> items, int n) {
        for (int i = (n - 2) / 2; i >= 0; i--) {
            sink(items, i, n - 1);
        }
    }

    private static <T extends Comparable<T>> void sink(ArrayList<T> items, int i, int end) {
        while (2 * i + 1 <= end) {
            int child = 2 * i + 1;
            int swapIndex = i;

            if (items.get(swapIndex).compareTo(items.get(child)) < 0) {
                swapIndex = child;
            }

            if (child + 1 <= end && items.get(swapIndex).compareTo(items.get(child + 1)) < 0) {
                swapIndex = child + 1;
            }

            if (swapIndex == i) {
                return;
            }

            Collections.swap(items, i, swapIndex);
            i = swapIndex;
        }
    }
}