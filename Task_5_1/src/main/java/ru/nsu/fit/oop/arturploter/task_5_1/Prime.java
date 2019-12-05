package ru.nsu.fit.oop.arturploter.task_5_1;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * The {@code Prime} class provides static methods to check if a given array contains at least one nonprime number.
 * The functions differ only in how they process the task: iteratively, sequentially, in parallel, or using
 * the fork/join framework.
 *
 * author: Artur Ploter
 */
public class Prime {
    /**
     * Returns true if a given array has at least one nonprime number.
     * The function processes the task iteratively.
     *
     * @param   numbers   an array of numbers
     * @return  {@code true} if a given array has at least one nonprime number;
     *          {@code false} otherwise
     */
    public static boolean computeIteratively(int[] numbers) {
        for (int number : numbers) {
            if (!PrimalityTest.isPrime(number)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if a given array has at least one nonprime number.
     * The function processes the task sequentially.
     *
     * @param   numbers   an array of numbers
     * @return  {@code true} if a given array has at least one nonprime number;
     *          {@code false} otherwise
     */
    public static boolean computeSequentially(int[] numbers) {
        return !Arrays.stream(numbers).allMatch(PrimalityTest::isPrime);
    }

    /**
     * Returns true if a given array has at least one nonprime number.
     * The function processes the task in parallel.
     *
     * @param   numbers   an array of numbers
     * @return  {@code true} if a given array has at least one nonprime number;
     *          {@code false} otherwise
     */
    public static boolean computeUsingParallelStreams(int[] numbers) {
        return !Arrays.stream(numbers).parallel().allMatch(PrimalityTest::isPrime);
    }

    /**
     * Returns true if a given array has at least one nonprime number.
     * The function processes the fork/join framework.
     *
     * @param   numbers   an array of numbers
     * @return  {@code true} if a given array has at least one nonprime number;
     *          {@code false} otherwise
     */
    public static boolean computeUsingForkJoinFramework(int[] numbers) {
        ForkJoinTask<Boolean> task = new ForkJoinPrime(numbers.clone());
        return new ForkJoinPool().invoke(task);
    }
}
