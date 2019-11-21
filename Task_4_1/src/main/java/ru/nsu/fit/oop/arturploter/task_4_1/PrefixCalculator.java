package ru.nsu.fit.oop.arturploter.task_4_1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code PrefixCalculator} class represents a calculator that evaluates prefix expression.
 * It supports some basic arithmetic (+, -, *, /) and can calculate some trigonometric (sin, cos), logarithmic (log),
 * square-root (sqrt) and exponential (pow) expressions.
 *
 * It has methods that can read an expression from Standard Input as well as take a string containing an expression
 * from the user.
 *
 * @author  Artur Ploter
 */
public class PrefixCalculator {
    private static String expression = "";

    /**
     * Reads an expression from Standard Input, evaluates it, and prints the result to Standard Output.
     */
    public static void start() {
        Scanner in = new Scanner(System.in);
        List<String> tokens;

        readIn(in);
        tokens = tokenize();

        while (!tokens.isEmpty() && !tokens.get(0).equals("0")) {
            double result = evaluateExpression(tokens);
            System.out.println("Result: " + result);
            expression = "";

            readIn(in);
            tokens = tokenize();
        }

        System.out.println("Shutting down.");
        in.close();
    }

    /**
     * Takes a string containing an expression, evaluates it and returns the result.
     *
     * @param   expression   an expression to be evaluated.
     * @return  the result of evaluating an expression.
     */
    public double calculate(String expression) {
        PrefixCalculator.expression = expression;

        List<String> tokens = tokenize();
        return evaluateExpression(tokens);
    }

    private static void readIn(Scanner in) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (expression.equals("")) {
                    System.out.println("User input timeout. Shutting down.");
                    System.exit(0);
                }
            }
        };

        timer.schedule(timerTask, 10 * 1000);
        expression = in.nextLine();
        timer.cancel();
    }

    private static List<String> tokenize() {
        final Pattern pattern
                = Pattern.compile("(-?\\d*\\.?\\d+)|(-?sin)|(-?cos)|(-?log)|(-?pow)|(-?sqrt)|(\\+)|(-)|(\\*)|(/)");
        Matcher matcher = pattern.matcher(expression);

        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }

    private static double evaluateExpression(List<String> tokens) {
        List<String> operatorsOneOperand = Arrays.asList("sin", "-sin", "cos", "-cos", "sqrt", "-sqrt");
        Stack<String> evaluation = new Stack<>();
        Stack<Pair> count = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            evaluation.push(token);

            if (getDoubleOrNull(token) == null) {
                if (operatorsOneOperand.contains(token)) {
                    count.push(new Pair(1, 1));
                } else {
                    count.push(new Pair(2, 2));
                }
            } else {
                if (count.isEmpty() && i < tokens.size() - 1) {
                    throw new IllegalArgumentException("The prefix expression cannot start with a number.");
                } else if (count.isEmpty()) {
                    //noinspection ConstantConditions
                    return getDoubleOrNull(token);
                }
                count.peek().decrementSecond();

                while (!count.isEmpty() && count.peek().getSecond() == 0) {
                    double result;

                    if (count.pop().getFirst() == 1) {
                        Double operand = getDoubleOrNull(evaluation.pop());
                        String operator = evaluation.pop();
                        if (operand == null) {
                            throw new IllegalArgumentException("test");
                        }

                        result = evaluateSimpleExpression(operator, operand);
                    } else {
                        Double operand2 = getDoubleOrNull(evaluation.pop());
                        Double operand1 = getDoubleOrNull(evaluation.pop());
                        String operator = evaluation.pop();
                        if (operand1 == null || operand2 == null) {
                            throw new IllegalArgumentException("test");
                        }

                        result = evaluateSimpleExpression(operator, operand2, operand1);
                    }

                    evaluation.push(Double.toString(result));

                    if (!count.isEmpty()) {
                        count.peek().decrementSecond();
                    } else {
                        evaluation.pop();

                        if (i < tokens.size() - 1) {
                            throw new IllegalArgumentException("The prefix expression has too many tokens.");
                        } else {
                            return result;
                        }
                    }
                }
            }
        }

        throw new IllegalArgumentException("The prefix expression does not have enough operands.");
    }

    private static Double getDoubleOrNull(String string) {
        try {
            return Double.valueOf(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static double evaluateSimpleExpression(String operator, double operand) {
        double result = Double.POSITIVE_INFINITY;

        switch (operator) {
            case "sin":
                result = Math.sin(getRadians(operand));
                break;

            case "-sin":
                result = -1 * Math.sin(getRadians(operand));
                break;

            case "cos":
                result = Math.cos(getRadians(operand));
                break;

            case "-cos":
                result = -1 * Math.cos(getRadians(operand));
                break;

            case "sqrt":
                validateSquareRootParameter(operand);
                result = Math.sqrt(operand);
                break;

            case "-sqrt":
                validateSquareRootParameter(operand);
                result = -1 * Math.sqrt(operand);
                break;
        }

        return result;
    }

    private static double getRadians(double operand) {
        if (operand % 1 != 0 || (operand >= -4.0 && operand <= 4.0)) {
            return operand;
        } else {
            return Math.toRadians(operand);
        }
    }

    private static void validateSquareRootParameter(double operand) {
        if (operand < 0) {
            throw new IllegalArgumentException("The square root of a negative number does not exist among " +
                    "the set of Real Numbers.");
        }
    }

    private static double evaluateSimpleExpression(String operator, double operand2, double operand1) {
        double result = Double.POSITIVE_INFINITY;

        switch(operator) {
            case "+":
                result = operand1 + operand2;
                break;

            case "-":
                result = operand1 - operand2;
                break;

            case "*":
                result = operand1 * operand2;
                break;

            case "/":
                result = operand1 / operand2;
                break;

            case "log":
                validateLogarithmParameters(operand2, operand1);
                result = Math.log(operand2) / Math.log(operand1);
                break;

            case "-log":
                validateLogarithmParameters(operand2, operand1);
                result = -1 * Math.log(operand2) / Math.log(operand1);
                break;

            case "pow":
                result = Math.pow(operand1, operand2);
                break;

            case "-pow":
                result = -1 * Math.pow(operand1, operand2);
                break;
        }

        return result;
    }

    private static void validateLogarithmParameters(double operand2, double operand1) {
        if (operand2 < 0) {
            throw new IllegalArgumentException("The logarithm of a negative number with a base " + operand1
                    + " is undefined.");
        }

        if (operand1 == 1) {
            throw new IllegalArgumentException("The logarithm of " + operand2 + " with a base 1 is undefined.");
        }

        if (operand1 < 0) {
            throw new IllegalArgumentException("The base of a logarithm must be a positive real number.");
        }

        if (operand1 == 0 && operand2 == 0) {
            throw new IllegalArgumentException("The logarithm of 0 with a base 0 is undefined.");
        }
    }
}