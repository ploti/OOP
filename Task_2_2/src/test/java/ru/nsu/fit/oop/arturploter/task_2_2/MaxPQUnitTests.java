package ru.nsu.fit.oop.arturploter.task_2_2;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MaxPQUnitTests {
    private Exception exception;

    @Test
    void put_Remove_GetObjectPriority_Size_IsEmpty_Contains_Iterator_ToArray_Clear_StreamAPIMethods() {
        MaxPQ<Integer, String> pq = new MaxPQ<>();

        pq.put(4, "val: 4");
        pq.put(-2, "val: -2");
        pq.put(-25, "val: -25");
        pq.put(0, "val: 0");

        pq.put(101, "val: 101");
        pq.put(25, "val: 25");
        pq.put(101, "val: 101v2");

        pq.put(25, "val: 25v2");
        pq.put(25, "val: 25v3");
        pq.put(25, "val: 25v4");
        pq.put(25, "val: 25v5");
        pq.put(25, "val: 25v6");

        pq.put(-2, "val: -2v2");
        pq.put(-2, "val: -2v3");
        pq.put(-2, "val: -2v4");
        pq.put(-2, "val: -2v5");

        pq.put(101, "val: 101v3");
        pq.put(24, "val: 24");
        pq.put(101, "val: 101v4");

        assertEquals("val: 101v4", pq.remove());
        assertEquals("val: 101v3", pq.remove());
        assertEquals("val: 101v2", pq.remove());
        assertEquals("val: 101", pq.remove());
        assertEquals("val: 25v6", pq.remove());

        List<String> actual = new ArrayList<>(pq);

        assertEquals(new ArrayList<>(
                Arrays.asList("val: 4", "val: -2", "val: -2v2", "val: -2v3", "val: -2v4", "val: -2v5", "val: -25",
                        "val: 0", "val: 25", "val: 25v2", "val: 25v3", "val: 25v4", "val: 25v5", "val: 24")),
                actual);

        assertEquals("val: 25v5", pq.remove());
        assertEquals("val: 25v4", pq.remove());
        assertEquals("val: 25v3", pq.remove());

        actual.clear();
        actual.addAll(pq);

        assertEquals(new ArrayList<>(
                Arrays.asList("val: 4", "val: -2", "val: -2v2", "val: -2v3", "val: -2v4", "val: -2v5", "val: -25",
                        "val: 0", "val: 25", "val: 25v2", "val: 24")),
                actual);

        assertEquals(-2, pq.getObjectPriority("val: -2v2"));
        assertEquals(-2, pq.getObjectPriority("val: -2v3"));
        assertEquals(4, pq.getObjectPriority("val: 4"));
        assertEquals(24, pq.getObjectPriority("val: 24"));

        exception = assertThrows(NoSuchElementException.class, () -> pq.getObjectPriority("101v2"));
        assertEquals("No value present", exception.getMessage());

        assertEquals(6, pq.size());

        assertFalse(pq.isEmpty());

        assertFalse(pq.contains("val: 5"));
        assertTrue(pq.contains("val: 4"));

        assertArrayEquals(new String[] {
                "val: 4", "val: -2", "val: -2v2", "val: -2v3", "val: -2v4", "val: -2v5", "val: -25", "val: 0",
                        "val: 25", "val: 25v2", "val: 24" },
                pq.toArray());

        assertThrows(UnsupportedOperationException.class, () -> pq.add("no"));

        actual.clear();

        pq.stream()
                .filter(s -> s.contains("-"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(actual::add);

        assertEquals(new ArrayList<>(
                Arrays.asList("VAL: -2", "VAL: -25", "VAL: -2V2", "VAL: -2V3", "VAL: -2V4", "VAL: -2V5")),
                actual);

        assertTrue(pq
                .stream()
                .anyMatch(entry -> entry.contains("val: -2")));

        assertEquals(new HashSet<>(
                Collections.singletonList("val: -2v4")),
                pq.stream()
                        .filter(entry -> entry.endsWith("v4"))
                        .collect(Collectors.toSet()));

        assertEquals(new HashMap<Integer, List<String>>() {{
            put(6, new ArrayList<>(
                    Arrays.asList("val: 4", "val: 0")));
            put(7, new ArrayList<>(
                    Arrays.asList("val: 25", "val: 24", "val: -2")));
            put(8, new ArrayList<>(
                    Collections.singletonList("val: -25")));
            put(9, new ArrayList<>(
                    Arrays.asList("val: 25v2", "val: -2v5", "val: -2v4", "val: -2v3", "val: -2v2")));
        }},
                pq.stream()
                        .collect(Collectors.groupingBy(String::length)));

        pq.clear();

        assertEquals(0, pq.size());

        exception = assertThrows(NoSuchElementException.class, pq::remove);
        assertEquals("The priority queue is empty.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> pq.put(null, "15"));
        assertEquals("Key cannot be null.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> pq.put(15, null));
        assertEquals("Value cannot be null.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> pq.put(15, null));
        assertEquals("Value cannot be null.", exception.getMessage());
    }

    @Test
    void put_Remove_Contains_Iterator_ToArray_Add_Remove_AddAll_RemoveAll_RetainAll() {
        MaxPQ<String, Double> pq = new MaxPQ<>();

        pq.put("Abba", 1.2);
        pq.put("Bob", 12.224);
        pq.put("Cop", 2.14);
        pq.put("zack", 16.5);
        pq.put("yup", 110.1);
        pq.put("Bob", 1102.1);
        pq.put("xor", 160.2);
        pq.put("NLE", 16.5);
        pq.put("NLE", 17.5);
        pq.put("NLE", 18.5);

        assertEquals(16.5, pq.remove());
        assertEquals(110.1, pq.remove());
        assertEquals(160.2, pq.remove());

        pq.put("Chiraq", -0.2);
        pq.put("Chinatown", -140.1452);
        pq.put("Chicago", 25.555);

        assertEquals(18.5, pq.remove());
        assertEquals(17.5, pq.remove());
        assertEquals(16.5, pq.remove());

        List<Double> actual = new ArrayList<>(pq);

        assertEquals(new ArrayList<>(
                        Arrays.asList(1.2, 12.224, 1102.1, 2.14, -0.2, -140.1452, 25.555)),
                actual);

        assertThrows(UnsupportedOperationException.class, () -> {
            Double[] a = new Double[pq.size()];
            pq.toArray(a);
        });

        assertThrows(UnsupportedOperationException.class, () -> pq.add(155.5));

        assertThrows(UnsupportedOperationException.class, () -> pq.remove(155.5));

        assertThrows(UnsupportedOperationException.class, () -> pq.addAll(new ArrayList<>(
                Arrays.asList(155.5112, 251.0))));

        assertThrows(UnsupportedOperationException.class, () -> pq.removeAll(new ArrayList<>(
                Arrays.asList(-2521.25, 52.5, 153.3))));

        assertThrows(UnsupportedOperationException.class, () -> pq.retainAll(new ArrayList<>(
                Arrays.asList(-245.4, 53.2, -23.5))));
    }

    @Test
    void put_Remove_Iterator_StreamAPIMethods() {
        MaxPQ<Double, String> pq = new MaxPQ<>();
        List<String> actual = new ArrayList<>();

        pq.put(42.6, "murda");
        pq.put(-2.2, "y r u gae");
        pq.put(-2.2, "why are you gae?");
        pq.put(0.22, "Pasta is quiet, Pasta will you be quiet?");
        pq.put(-2.2, "wai ah you gae");
        pq.put(0.22, "I am outraged");
        pq.put(12.4, "confusion of da highest orda");

        assertEquals("murda", pq.remove());
        assertEquals("confusion of da highest orda", pq.remove());
        assertEquals("I am outraged", pq.remove());
        assertEquals("Pasta is quiet, Pasta will you be quiet?", pq.remove());

        pq.stream()
                .filter(s -> s.contains("gae"))
                .map(String::toLowerCase)
                .sorted()
                .forEach(actual::add);

        assertEquals(new ArrayList<>(
                        Arrays.asList("wai ah you gae", "why are you gae?", "y r u gae")),
                actual);

        assertTrue(pq
                .stream()
                .anyMatch(entry -> entry.contains("wai")));

        assertEquals(new HashSet<>(
                        Arrays.asList("wai ah you gae", "y r u gae")),
                pq.stream()
                        .filter(entry -> entry.endsWith("gae"))
                        .collect(Collectors.toSet()));

        assertEquals(new HashMap<Integer, List<String>>() {{
                         put(16, new ArrayList<>(
                                 Collections.singletonList("why are you gae?")));
                         put(9, new ArrayList<>(
                                 Collections.singletonList("y r u gae")));
                         put(14, new ArrayList<>(
                                 Collections.singletonList("wai ah you gae")));
        }},
                pq.stream()
                        .collect(Collectors.groupingBy(String::length)));

        pq.stream()
                .filter(entry -> entry.contains("gae"))
                .forEach(entry -> pq.remove());

        actual.clear();
        actual.addAll(pq);

        assertEquals(new ArrayList<>(), actual);

        pq.put(52.6, "who ses am gae");
        pq.put(1.001, "They use a gadget called banana");
        pq.put(52.6, "you ah gae");
        pq.put(-13.2, "Is there only one way to have children?");
        pq.put(-13.2, "Yes, there is!");

        assertEquals(3,
                pq.stream()
                        .map(String::toLowerCase)
                        .map(String::toUpperCase)
                        .map(entry -> entry.substring(0,3))
                        .count());

    }

    @Test
    void putRemoveIterator_WhenTheNextMethodWasCalledOnAnEmptyPriorityQueue_NoSuchElementExceptionThrown() {
        MaxPQ<Integer, Integer> pq = new MaxPQ<>();

        pq.put(314, 10200);
        pq.put(433, 10100);
        pq.put(1616, 25540);
        pq.put(131, 10200);
        pq.put(131, 10201);
        pq.put(-15, -25444);

        Iterator<Integer> i = pq.iterator();
        while(i.hasNext()) {
            Integer item = i.next();

            if (item == -15) {
                pq.remove();

                exception = assertThrows(NoSuchElementException.class, i::next);
                assertEquals("The priority queue is empty.", exception.getMessage());
            }
        }
    }

}