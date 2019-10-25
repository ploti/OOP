package com.arturploter.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class PriorityQueueUnitTests {

    @Test
    public void testKeyIntegerValueInteger() {
        PriorityQueue<Integer, Integer> priorityQueue = new PriorityQueue<>();
        Pair<Integer, Integer> max;

        try {
            priorityQueue.insert(15, 11111);
            priorityQueue.insert(-25, 21111);
            priorityQueue.insert(5, 91111);
            priorityQueue.insert(0, 11111);
            priorityQueue.insert(1, 11111);
            priorityQueue.insert(-1, 10042);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>(15, 11111)));

            priorityQueue.insert(3, 25555);
            priorityQueue.insert(-125, 43333);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>(5, 91111)));

            priorityQueue.insert(314, 10200);
            priorityQueue.insert(433, 10100);
            priorityQueue.insert(1616, 25540);
            priorityQueue.insert(131, 10200);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>(1616, 25540)));

            List<Integer> actual = new ArrayList<>();
            List<Integer> expected = new ArrayList<>();

            expected.add(433);
            expected.add(314);
            expected.add(131);
            expected.add(3);
            expected.add(1);
            expected.add(0);
            expected.add(-1);
            expected.add(-25);
            expected.add(-125);

            putKeysIntoList(priorityQueue, actual);

            assertThat(actual, is(expected));
        } catch (IllegalArgumentException | QueueEmptyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKeyCharacterValueBoolean() {
        PriorityQueue<Character, Boolean> priorityQueue = new PriorityQueue<>();
        Pair<Character, Boolean> max;

        try {
            priorityQueue.insert('`', true);
            priorityQueue.insert('f', false);
            priorityQueue.insert('~', true);
            priorityQueue.insert('F', false);
            priorityQueue.insert('q', false);
            priorityQueue.insert('r', true);
            priorityQueue.insert(' ', false);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>('~', true)));

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>('r', true)));

            priorityQueue.insert('}', true);
            priorityQueue.insert(')', false);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>('}', true)));

            List<Character> actual = new ArrayList<>();
            List<Character> expected = new ArrayList<>();

            expected.add('q');
            expected.add('f');
            expected.add('`');
            expected.add('F');
            expected.add(')');
            expected.add(' ');

            putKeysIntoList(priorityQueue, actual);

            assertThat(actual, is(expected));
        } catch (QueueEmptyException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKeyStringValueDouble() {
        PriorityQueue<String, Double> priorityQueue = new PriorityQueue<>();
        Pair<String, Double> max;

        try {
            priorityQueue.insert("Abba", 1.2);
            priorityQueue.insert("Bob", 12.224);
            priorityQueue.insert("Cop", 2.14);
            priorityQueue.insert("zack", 16.5);
            priorityQueue.insert("yup", 110.1);
            priorityQueue.insert("xor", 160.2);
            priorityQueue.insert("NLE", 16.5);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("zack", 16.5)));

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("yup", 110.1)));

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("xor", 160.2)));

            priorityQueue.insert("Chiraq", -0.2);
            priorityQueue.insert("Chinatown", -140.1452);
            priorityQueue.insert("Chicago", 25.555);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("NLE", 16.5)));

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("Cop", 2.14)));

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("Chiraq", -0.2)));

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("Chinatown", -140.1452)));

            priorityQueue.insert("chief keef", 999.9);
            priorityQueue.insert("___.__.", -24.1);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("chief keef", 999.9)));

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>("___.__.", -24.1)));

            List<String> actual = new ArrayList<>();
            List<String> expected = new ArrayList<>();

            expected.add("Chicago");
            expected.add("Bob");
            expected.add("Abba");

            putKeysIntoList(priorityQueue, actual);

            assertThat(actual, is(expected));
        } catch (IllegalArgumentException | QueueEmptyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKeyShortValueByte() {
        PriorityQueue<Short, Byte> priorityQueue = new PriorityQueue<>();
        Pair<Short, Byte> max;

        try {
            priorityQueue.insert((short) 5252, (byte) 124);
            priorityQueue.insert((short) 25, (byte) 215);
            priorityQueue.insert((short) -12541, (byte) 0);
            priorityQueue.insert((short) -32000, (byte) -122);

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>((short) 5252, (byte) 124)));

            max = priorityQueue.removeMax();
            assertTrue(max.isEqual(new Pair<>((short) 25, (byte) 215)));

            List<Short> actual = new ArrayList<>();
            List<Short> expected = new ArrayList<>();

            expected.add((short) -12541);
            expected.add((short) -32000);

            putKeysIntoList(priorityQueue, actual);

            assertThat(actual, is(expected));
        } catch (IllegalArgumentException | QueueEmptyException e) {
            e.printStackTrace();
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testEmptyQueue() throws QueueEmptyException {
        PriorityQueue<Double, Long> priorityQueue = new PriorityQueue<>();
        Pair<Double, Long> max;

        thrown.expect(QueueEmptyException.class);
        max = priorityQueue.removeMax();
    }

    @Test
    public void testQueueIllegalArgumentException() throws IllegalArgumentException {
        PriorityQueue<String, String> priorityQueue = new PriorityQueue<>();
        Pair<Double, Long> max;

        priorityQueue.insert("pair", "opinion");
        priorityQueue.insert("proud", "geek");

        thrown.expect(IllegalArgumentException.class);
        priorityQueue.insert("pair", "dolby");
    }

    private <K extends Comparable<K>, V> void putKeysIntoList(PriorityQueue<K, V> priorityQueue, List<K> actual) {
        for (K key : priorityQueue) {
            actual.add(key);
        }
    }
}