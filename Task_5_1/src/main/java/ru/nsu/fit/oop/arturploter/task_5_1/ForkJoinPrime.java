package ru.nsu.fit.oop.arturploter.task_5_1;

import java.util.concurrent.RecursiveTask;

class ForkJoinPrime extends RecursiveTask<Boolean> {
    private static final int THRESHOLD = 10_000;
    private final int[] numbers;
    private final int start;
    private final int end;

    ForkJoinPrime(int[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinPrime(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Boolean compute() {
        int length = end - start;
        if (length < THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinPrime leftTask = new ForkJoinPrime(numbers, start, start + length / 2);
        leftTask.fork();

        ForkJoinPrime rightTask = new ForkJoinPrime(numbers, start + length / 2, end);
        boolean rightResult = rightTask.compute();
        boolean leftResult = leftTask.join();

        return leftResult && rightResult;
    }

    private boolean computeSequentially() {
        for (int i = start; i < end; i++) {
            if (!PrimalityTest.isPrime(numbers[i])) {
                return true;
            }
        }

        return false;
    }
}