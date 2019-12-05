package ru.nsu.fit.oop.arturploter.task_4_1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code PrefixCalculator} class represents a calculator that evaluates prefix expressions.
 * It supports some basic arithmetic (+, -, *, /) and can evaluate trigonometric (sin, cos), logarithmic (log),
 * square-root (sqrt) and exponential (pow) expressions.
 *
 * It has the {@code start} method that reads an expression from Standard Input and the {@code evaluate} method
 * that takes a string containing an expression.
 *
 * @author  Artur Ploter
 */
public class PrefixCalculator {
    private final Operations operations;
    private String expression;

    public PrefixCalculator() {
        operations = new Operations();
        expression = "";
    }

    /**
     * Reads an expression from Standard Input, evaluates it, and prints the result to Standard Output.
     */
    public void start() {
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
        this.expression = expression;

        List<String> tokens = tokenize();
        return evaluateExpression(tokens);
    }

    private void readIn(Scanner in) {
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

    private List<String> tokenize() {
        final Pattern pattern
                = Pattern.compile("(-?\\d*\\.?\\d+)|(-?sin)|(-?cos)|(-?log)|(-?pow)|(-?sqrt)|(\\+)|(-)|(\\*)|(/)");
        Matcher matcher = pattern.matcher(expression);

        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }

    private double evaluateExpression(List<String> tokens) {
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

                        //noinspection ConstantConditions
                        result = operations.evaluate(operator, operand);
                    } else {
                        Double operand1 = getDoubleOrNull(evaluation.pop());
                        Double operand2 = getDoubleOrNull(evaluation.pop());
                        String operator = evaluation.pop();

                        //noinspection ConstantConditions
                        result = operations.evaluate(operator, operand1, operand2);
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

    private Double getDoubleOrNull(String string) {
        try {
            return Double.valueOf(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}