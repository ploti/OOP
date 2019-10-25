package com.arturploter.util;

import java.util.ArrayList;
import java.util.Arrays;
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

    void testMethodPrintKeys() {
        System.out.println(Arrays.toString(indices.toArray()));
    }

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