package ru.nsu.fit.oop.arturploter.task_2_2;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class IndexedLinkedHashMap<K, V> extends LinkedHashMap {
    private LinkedHashMap<K, V> linkedHashMap;
    private ArrayList<K> indices;

    IndexedLinkedHashMap() {
        linkedHashMap = new LinkedHashMap<>();
        indices = new ArrayList<>();
    }

    K getKey(int i) {
        return indices.get(i);
    }

    void swap(int i, int j) {
        K tmp = indices.get(i);
        indices.set(i, indices.get(j));
        indices.set(j, tmp);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        indices.add((K) key);
        return linkedHashMap.put((K) key, (V) value);
    }

    @Override
    public Object get(Object key) {
        return linkedHashMap.get(key);
    }

    @Override
    public Object remove(Object key) {
        //noinspection SuspiciousMethodCalls
        indices.remove(key);

        return linkedHashMap.remove(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return linkedHashMap.containsKey(key);
    }

    @Override
    public int size() {
        return linkedHashMap.size();
    }
}