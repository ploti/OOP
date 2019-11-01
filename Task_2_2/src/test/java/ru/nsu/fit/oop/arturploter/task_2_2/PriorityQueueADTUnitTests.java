package ru.nsu.fit.oop.arturploter.task_2_2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class PriorityQueueADTUnitTests {
    @Test
    public void givenKeyIntegerValueIntegerPriorityQueue() {
        PriorityQueueADT<Integer, Integer> priorityQueue = new PriorityQueueADT<>();
        Pair<Integer, Integer> max;

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
        putKeysIntoList(priorityQueue, actual);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList(433, 314, 131, 3, 1, 0, -1, -25, -125))));
    }

    @Test
    public void givenKeyCharacterValueBooleanPriorityQueue() {
        PriorityQueueADT<Character, Boolean> priorityQueue = new PriorityQueueADT<>();
        Pair<Character, Boolean> max;

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
        putKeysIntoList(priorityQueue, actual);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList('q', 'f', '`', 'F', ')', ' '))));
    }

    @Test
    public void givenKeyStringValueDoublePriorityQueue() {
        PriorityQueueADT<String, Double> priorityQueue = new PriorityQueueADT<>();
        Pair<String, Double> max;

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
        putKeysIntoList(priorityQueue, actual);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList("Chicago", "Bob", "Abba"))));
    }

    @Test
    public void givenKeyShortValueBytePriorityQueue() {
        PriorityQueueADT<Short, Byte> priorityQueue = new PriorityQueueADT<>();
        Pair<Short, Byte> max;

        priorityQueue.insert((short) 5252, (byte) 124);
        priorityQueue.insert((short) 25, (byte) 215);
        priorityQueue.insert((short) -12541, (byte) 0);
        priorityQueue.insert((short) -32000, (byte) -122);

        max = priorityQueue.removeMax();
        assertTrue(max.isEqual(new Pair<>((short) 5252, (byte) 124)));

        max = priorityQueue.removeMax();
        assertTrue(max.isEqual(new Pair<>((short) 25, (byte) 215)));

        List<Short> actual = new ArrayList<>();
        putKeysIntoList(priorityQueue, actual);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList((short) -12541, (short) -32000))));
    }

    @Test
    public void givenKeyStringValueDateArrayPriorityQueue() {
        PriorityQueueADT<String, Date[]> priorityQueue = new PriorityQueueADT<>();
        Pair<String, Date[]> max;

        Date[] dateArray1 = new Date[]{new Date(), new Date(2114112221L), new Date(1221335552L)};
        Date[] dateArray2 = new Date[]{new Date(1910117236L), new Date()};
        Date[] dateArray3 = new Date[]{new Date(), new Date(), new Date()};

        priorityQueue.insert("physical activity", dateArray1);
        priorityQueue.insert("plan ahead", dateArray2);
        priorityQueue.insert("circadian rhythm", dateArray3);
        priorityQueue.insert("QQC - Quantity, Quality, Consistency", null);

        max = priorityQueue.removeMax();
        assertTrue(max.isEqual(new Pair<>("plan ahead", dateArray2)));

        max = priorityQueue.removeMax();
        assertTrue(max.isEqual(new Pair<>("physical activity", dateArray1)));

        List<String> actual = new ArrayList<>();
        putKeysIntoList(priorityQueue, actual);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList("circadian rhythm", "QQC - Quantity, Quality, Consistency"))));
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void throwsIllegalArgumentExceptionIfTheInsertMethodWasCalledWithTheNullKey() {
        PriorityQueueADT<Integer, Double> priorityQueue = new PriorityQueueADT<>();
        Pair<Integer, Double> max;

        priorityQueue.insert(252, null);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Key cannot be null.");
        priorityQueue.insert(null, 1.2);
    }

    @Test
    public void throwsNoSuchElementExceptionIfTheRemoveMaxMethodWasCalledOnAnEmptyPriorityQueue() {
        PriorityQueueADT<Double, Long> priorityQueue = new PriorityQueueADT<>();
        Pair<Double, Long> max;

        exception.expect(NoSuchElementException.class);
        max = priorityQueue.removeMax();
    }

    @Test
    public void throwsIllegalArgumentExceptionIfTheInsertMethodWasCalledWithTheKeyThatIsInThePriorityQueue() {
        PriorityQueueADT<String, String> priorityQueue = new PriorityQueueADT<>();
        Pair<Double, Long> max;

        priorityQueue.insert("pair", "opinion");
        priorityQueue.insert("proud", "geek");

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("An item with the same key");
        priorityQueue.insert("pair", "dolby");
    }

    @Test
    public void throwsNoSuchElementExceptionIfTheNextMethodWasCalledOnAnEmptyPriorityQueue() {
        PriorityQueueADT<Integer, Integer> priorityQueue = new PriorityQueueADT<>();
        priorityQueue.insert(314, 10200);
        priorityQueue.insert(433, 10100);
        priorityQueue.insert(1616, 25540);
        priorityQueue.insert(131, 10200);

        exception.expect(NoSuchElementException.class);

        Iterator<Integer> i = priorityQueue.iterator();
        while(i.hasNext()) {
            Integer item = i.next();

            if (item == 131) {
                priorityQueue.removeMax();
                i.next();
                break;
            }
        }
    }

    @Test
    public void throwsUnsupportedOperationExceptionIfTheRemoveMethodWasCalled() {
        PriorityQueueADT<Integer, Integer> priorityQueue = new PriorityQueueADT<>();
        priorityQueue.insert(314, 10200);
        priorityQueue.insert(433, 10100);
        priorityQueue.insert(1616, 25540);
        priorityQueue.insert(131, 10200);

        exception.expect(UnsupportedOperationException.class);

        Iterator<Integer> i = priorityQueue.iterator();
        while(i.hasNext()) {
            Integer item = i.next();

            if (item > 450) {
                i.remove();
            }
        }
    }

    private <K extends Comparable<K>, V> void putKeysIntoList(PriorityQueueADT<K, V> priorityQueue, List<K> actual) {
        priorityQueue.forEach(actual::add);
    }
}