package ru.nsu.fit.oop.arturploter.task_2_1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class StackADTUnitTests {
    @Test
    public void givenIntegerStack() {
        StackADT<Integer> stack = new StackADT<>();
        List<Integer> actual;

        stack.push(1254);
        stack.push(125415);
        stack.push(14);
        stack.push(12);
        stack.push(0);
        stack.push(-25);

        assertThat(stack.pop(), is(-25));
        assertThat(stack.pop(), is(0));
        assertThat(stack.pop(), is(12));

        actual = new ArrayList<>();
        stack.forEach(actual::add);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList(14, 125415, 1254))));

        assertThat(stack.pop(), is(14));
        assertThat(stack.pop(), is(125415));

        actual = new ArrayList<>();
        stack.forEach(actual::add);
        assertThat(actual, is(new ArrayList<>(
                Collections.singletonList(1254))));

        assertThat(stack.pop(), is(1254));

        actual = new ArrayList<>();
        addStackContentsIntoList(stack, actual);
        assertThat(actual, is(new ArrayList<Integer>()));
    }

    @Test
    public void givenStringStack() {
        StackADT<String> stack = new StackADT<>();
        List<String> actual;

        stack.push("week-long vacation");
        stack.push("falling");
        stack.push("fallen");
        stack.push("step");
        stack.push("story");
        stack.push("twist");
        stack.push("of");
        stack.push("fade");
        stack.push("Week-long visit");
        stack.push(null);

        assertThat(stack.pop(), is(nullValue()));
        assertThat(stack.pop(), is("Week-long visit"));
        assertThat(stack.pop(), is("fade"));
        assertThat(stack.pop(), is("of"));

        actual = new ArrayList<>();
        addStackContentsIntoList(stack, actual);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList("twist", "story", "step", "fallen", "falling", "week-long vacation"))));

        assertThat(stack.pop(), is("twist"));
        assertThat(stack.pop(), is("story"));
        assertThat(stack.pop(), is("step"));
        assertThat(stack.pop(), is("fallen"));

        actual = new ArrayList<>();
        stack.forEach(actual::add);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList("falling", "week-long vacation"))));
    }

    @Test
    public void givenDateStack() {
        StackADT<Date> stack = new StackADT<>();
        Date date = new Date();

        stack.push(date);
        stack.push(new Date(2113221112L));
        stack.push(null);
        stack.push(new Date(2111111111L));

        assertThat(stack.pop(), is(new Date(2111111111L)));
        assertThat(stack.pop(), is(nullValue()));
        assertThat(stack.pop(), is(new Date(2113221112L)));

        List<Date> actual = new ArrayList<>();
        addStackContentsIntoList(stack, actual);
        assertThat(actual, is(new ArrayList<>(
                Collections.singletonList(date))));
    }

    @Test
    public void givenCharacterArrayStack() {
        StackADT<Character[]> stack = new StackADT<>();
        Character[] charArr1 = new Character[]{'a', 'b', 'c'};
        Character[] charArr2 = new Character[]{'p', 'o', 'p', 'p', 'a'};

        stack.push(charArr1);
        stack.push(charArr2);
        stack.push(new Character[]{'c', 'h', 'o', 'p', 'p', 'a'});
        stack.push(new Character[]{'g', 'r', 'a', '-', 't', 'a', '-', 't', 'a', '\n'});

        assertThat(stack.pop(), is(new Character[]{'g', 'r', 'a', '-', 't', 'a', '-', 't', 'a', '\n'}));
        assertThat(stack.pop(), is(new Character[]{'c', 'h', 'o', 'p', 'p', 'a'}));

        List<Character[]> actual = new ArrayList<>();
        stack.forEach(actual::add);
        assertThat(actual, is(new ArrayList<>(
                Arrays.asList(charArr2, charArr1))));
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void throwsStackUnderflowExceptionIfThePopMethodWasCalledOnTheEmptyStack() {
        StackADT<Integer> stack = new StackADT<>();

        exception.expect(StackUnderflowException.class);
        exception.expectMessage("Stack Underflow");
        stack.pop();
    }

    @Test
    public void throwsStackUnderflowExceptionIfTheNextMethodWasCalledOnTheEmptyStack() {
        StackADT<Double> stack = new StackADT<>();
        stack.push(25.2);
        stack.push(32.212);
        stack.push(24.992);
        stack.push(25.000012);
        stack.push(31.1);
        stack.push(22.0);

        exception.expect(StackUnderflowException.class);

        Iterator<Double> i = stack.iterator();
        while(i.hasNext()) {
            Double item = i.next();

            if (item == 25.2) {
                stack.pop();
                i.next();
                break;
            }
        }
    }

    @Test
    public void throwsUnsupportedOperationExceptionIfTheRemoveMethodWasCalled() {
        StackADT<Double> stack = new StackADT<>();
        stack.push(25.2);
        stack.push(32.212);
        stack.push(24.992);
        stack.push(25.000012);
        stack.push(31.1);
        stack.push(22.0);

        exception.expect(UnsupportedOperationException.class);

        Iterator<Double> i = stack.iterator();
        while(i.hasNext()) {
            Double item = i.next();

            if (item > 30) {
                i.remove();
            }
        }
    }

    private <T> void addStackContentsIntoList(StackADT<T> stack, List<T> actual) {
        stack.forEach(actual::add);
    }
}