package ru.nsu.fit.oop.arturploter.task_5_1;

import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;

class PrimeUnitTests {
    private static final int COPIES = 400_000;
    private long start, end;

    @Nested
    @DisplayName("Class to test methods of Prime on an array of prime randomNumbers.")
    class TestsOnAnArrayOfPrimeNumbers {
        private final int[] numbers = { 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73 };

        @Test
        void computeIteratively() {
            start = System.currentTimeMillis();
            Assertions.assertFalse(Prime.computeIteratively(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeIteratively() in TestsOnAnArrayOfPrimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeSequentially() {
            start = System.currentTimeMillis();
            Assertions.assertFalse(Prime.computeSequentially(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeSequentially() in TestsOnAnArrayOfPrimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeUsingParallelStreams() {
            start = System.currentTimeMillis();
            Assertions.assertFalse(Prime.computeUsingParallelStreams(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeUsingParallelStreams() in TestsOnAnArrayOfPrimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeUsingForkJoinFramework() {
            start = System.currentTimeMillis();
            Assertions.assertFalse(Prime.computeUsingForkJoinFramework(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeUsingForkJoinFramework() in TestsOnAnArrayOfPrimeNumbers took "
                    + (end - start) + " milliseconds.");
        }
    }

    @Nested
    @DisplayName("Class to test methods of Prime on a large array of prime randomNumbers.")
    class TestsOnALargeArrayOfPrimeNumbers {
        private final List<Integer> numbersList = Collections.nCopies(COPIES, 10002199);
        private final int[] numbers = numbersList.stream().mapToInt(i -> i).toArray();

        @Test
        void computeIteratively() {
            start = System.currentTimeMillis();
            Assertions.assertFalse(Prime.computeIteratively(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeIteratively() in TestsOnALargeArrayOfPrimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeSequentially() {
            start = System.currentTimeMillis();
            Assertions.assertFalse(Prime.computeSequentially(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeSequentially() in TestsOnALargeArrayOfPrimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeUsingParallelStreams() {
            start = System.currentTimeMillis();
            Assertions.assertFalse(Prime.computeUsingParallelStreams(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeUsingParallelStreams() in TestsOnALargeArrayOfPrimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeUsingForkJoinFramework() {
            start = System.currentTimeMillis();
            Assertions.assertFalse(Prime.computeUsingForkJoinFramework(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeUsingForkJoinFramework() in TestsOnALargeArrayOfPrimeNumbers took "
                    + (end - start) + " milliseconds.");
        }
    }

    @Nested
    @DisplayName("Class to test methods of Prime on an array with nonprime randomNumbers.")
    class TestsOnAnArrayWithNonprimeNumbers {
        private final int[] numbers = { 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, -61, -67, -71, -73 };

        @Test
        void computeIteratively() {
            start = System.currentTimeMillis();
            Assertions.assertTrue(Prime.computeIteratively(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeIteratively() in TestsOnAnArrayWithNonprimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeSequentially() {
            start = System.currentTimeMillis();
            Assertions.assertTrue(Prime.computeSequentially(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeSequentially() in TestsOnAnArrayWithNonprimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeUsingParallelStreams() {
            start = System.currentTimeMillis();
            Assertions.assertTrue(Prime.computeUsingParallelStreams(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeUsingParallelStreams() in TestsOnAnArrayWithNonprimeNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeUsingForkJoinFramework() {
            start = System.currentTimeMillis();
            Assertions.assertTrue(Prime.computeUsingForkJoinFramework(numbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeUsingForkJoinFramework() in TestsOnAnArrayWithNonprimeNumbers took "
                    + (end - start) + " milliseconds.");
        }
    }

    @Nested
    @DisplayName("Class to test methods of Prime on a large array with nonprime randomNumbers.")
    class TestsOnALargeArrayWithRandomNumbers {
        final int[] randomNumbers = new Random().ints(COPIES, Integer.MIN_VALUE, Integer.MAX_VALUE).toArray();


        @Test
        void computeIteratively() {
            start = System.currentTimeMillis();
            Assertions.assertTrue(Prime.computeIteratively(randomNumbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeIteratively() in TestsOnALargeArrayWithRandomNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeSequentially() {
            start = System.currentTimeMillis();
            Assertions.assertTrue(Prime.computeSequentially(randomNumbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeSequentially() in TestsOnALargeArrayWithRandomNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeUsingParallelStreams() {
            start = System.currentTimeMillis();
            Assertions.assertTrue(Prime.computeUsingParallelStreams(randomNumbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeUsingParallelStreams() in TestsOnALargeArrayWithRandomNumbers took "
                    + (end - start) + " milliseconds.");
        }

        @Test
        void computeUsingForkJoinFramework() {
            start = System.currentTimeMillis();
            Assertions.assertTrue(Prime.computeUsingForkJoinFramework(randomNumbers));
            end = System.currentTimeMillis();

            System.out.println("DEBUG: computeUsingForkJoinFramework() in TestsOnALargeArrayWithRandomNumbers took "
                    + (end - start) + " milliseconds.");
        }
    }
}