package ru.nsu.fit.oop.arturploter.task_4_1;

import java.util.HashMap;
import java.util.Map;

class Operations {
    private final Map<String, OperationOneOperand> operationsOneOperand;
    private final Map<String, OperationTwoOperands> operationsTwoOperands;

    Operations() {
        operationsOneOperand = new HashMap<>();
        operationsTwoOperands = new HashMap<>();

        initBasicOperations();
    }

    void addOperation(String name, OperationOneOperand operation) {
        operationsOneOperand.put(name, operation);
    }

    void addOperation(String name, OperationTwoOperands operation) {
        operationsTwoOperands.put(name, operation);
    }

    double evaluate(String operation, double operand) {
        if (operation.charAt(0) == '-' && operation.length() > 1) {
            return (-1 * operationsOneOperand.get(operation.substring(1)).operation(operand));
        } else {
            return operationsOneOperand.get(operation).operation(operand);
        }
    }

    double evaluate(String operation, double operand1, double operand2) {
        if (operation.charAt(0) == '-' && operation.length() > 1) {
            return (-1 * operationsTwoOperands.get(operation.substring(1)).operation(operand1, operand2));
        } else {
            return operationsTwoOperands.get(operation).operation(operand1, operand2);
        }
    }

    private void initBasicOperations() {
        operationsOneOperand.put("sin",
                (operand -> Math.sin(getRadians(operand))));
        operationsOneOperand.put("cos",
                (operand -> Math.cos(getRadians(operand))));
        operationsOneOperand.put("sqrt",
                (operand -> {
                    validateSquareRootParameter(operand);
                    return Math.sqrt(operand);
                }));

        operationsTwoOperands.put("+", Double::sum);
        operationsTwoOperands.put("-",
                ((operand1, operand2) -> operand2 - operand1));
        operationsTwoOperands.put("*",
                ((operand1, operand2) -> operand1 * operand2));
        operationsTwoOperands.put("/",
                ((operand1, operand2) -> {
                    validateDivisor(operand1);
                    return operand2 / operand1;
                }));
        operationsTwoOperands.put("log",
                ((operand1, operand2) -> {
                    validateLogarithmParameters(operand1, operand2);
                    return Math.log(operand1) / Math.log(operand2);
                }));
        operationsTwoOperands.put("pow",
                ((operand1, operand2) -> Math.pow(operand2, operand1)));
    }

    private double getRadians(double operand) {
        if (operand % 1 != 0 || (operand >= -4.0 && operand <= 4.0)) {
            return operand;
        } else {
            return Math.toRadians(operand);
        }
    }

    private void validateSquareRootParameter(double operand) {
        if (operand < 0) {
            throw new IllegalArgumentException("The square root of a negative number does not exist among " +
                    "the set of Real Numbers.");
        }
    }

    private void validateDivisor(double divisor) {
        if (divisor == 0.0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
    }

    private void validateLogarithmParameters(double operand1, double operand2) {
        if (operand1 < 0) {
            throw new IllegalArgumentException("The logarithm of a negative number with a base " + operand2
                    + " is undefined.");
        }

        if (operand2 == 1) {
            throw new IllegalArgumentException("The logarithm of " + operand1 + " with a base 1 is undefined.");
        }

        if (operand2 < 0) {
            throw new IllegalArgumentException("The base of a logarithm must be a positive real number.");
        }

        if (operand1 == 0 && operand2 == 0) {
            throw new IllegalArgumentException("The logarithm of 0 with a base 0 is undefined.");
        }
    }
}