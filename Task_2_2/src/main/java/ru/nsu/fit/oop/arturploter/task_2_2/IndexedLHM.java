package ru.nsu.fit.oop.arturploter.task_2_2;

import java.util.*;

class IndexedLHM<K, V> extends LinkedHashMap<K, V> implements Cloneable {
    private Map<K, List<V>> linkedHashMap;
    private List<K> indices;

    IndexedLHM() {
        linkedHashMap = new LinkedHashMap<>();
        indices = new ArrayList<>();
    }

    void swap(int i, int j) {
        K tmp = indices.get(i);
        indices.set(i, indices.get(j));
        indices.set(j, tmp);
    }

    Map<K, List<V>> getLinkedHashMap() {
        return linkedHashMap;
    }

    K getKey(int i) {
        return indices.get(i);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    K getKeyByValue(V value) {
        return linkedHashMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(value))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }

    int getListSize(K key) {
        return linkedHashMap.get(key).size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public IndexedLHM<K, V> clone() {
        IndexedLHM<K, V> clone = (IndexedLHM<K, V>) super.clone();

        clone.linkedHashMap = new HashMap<>();
        for (Map.Entry<K, List<V>> entry : linkedHashMap.entrySet()) {
            clone.linkedHashMap.put(entry.getKey(),
                    new ArrayList<>(entry.getValue()));
        }

        clone.indices = new ArrayList<>();
        clone.indices.addAll(indices);

        return clone;
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "unchecked"})
    @Override
    public Object put(Object key, Object value) {
        if (linkedHashMap.containsKey(key)) {
            linkedHashMap.get(key).add((V) value);
            return value;
        }

        indices.add((K) key);
        linkedHashMap.put((K) key, new ArrayList<>(
                Collections.singletonList((V) value)));

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        return linkedHashMap.get(key).get(getListSize((K) key) - 1);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public V remove(Object key) {
        List<V> values = linkedHashMap.get(key);
        int n = values.size();

        if (n > 1) {
            return values.remove(n - 1);
        }

        indices.remove(key);
        return linkedHashMap.remove(key).get(0);
    }

    @Override
    public boolean containsKey(Object key) {
        return linkedHashMap.containsKey(key);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean containsValue(Object value) {
        return linkedHashMap.entrySet()
                .stream()
                .anyMatch(entry -> entry.getValue().contains(value));
    }

    @Override
    public int size() {
        return linkedHashMap.size();
    }
}