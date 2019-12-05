package ru.nsu.fit.oop.arturploter.task_5_1;

class PrimalityTest {
    static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }

        for (long factor = 2; factor * factor <= n; factor++) {
            if (n % factor == 0) {
                return false;
            }
        }

        return true;
    }
}
