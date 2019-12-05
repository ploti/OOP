package ru.nsu.fit.oop.arturploter.task_4_1;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("PrefixCalculator unit tests")
class PrefixCalculatorUnitTests {
    private static final InputStream systemInBackup = System.in;
    private static final PrintStream systemOutBackup = System.out;
    private final PrefixCalculator calculator = new PrefixCalculator();

    @AfterAll
    static void restoreStreams() {
        System.setIn(systemInBackup);
        System.setOut(systemOutBackup);
    }

    @Nested
    @DisplayName("Tests for the start method")
    class StartTests {

        @Nested
        @DisplayName("Correct input tests")
        class CorrectInputTests {
            private ByteArrayOutputStream byteArrayOutputStream;

            @BeforeEach
            void initializeByteArrayOutputStream() {
                byteArrayOutputStream = new ByteArrayOutputStream();
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredExpressions() {
                String inputString = "- + 6 -2 3" + System.getProperty("line.separator")
                        + "-+6-2 3" + System.getProperty("line.separator") + "0" + System.getProperty("line.separator");

                String outputString = "Result: 1.0" + System.getProperty("line.separator") + "Result: 1.0"
                        + System.getProperty("line.separator") + "Shutting down."
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));
                System.setOut(new PrintStream(byteArrayOutputStream));

                calculator.start();

                assertEquals(outputString, byteArrayOutputStream.toString());
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredDifficultExpressions()  {
                String inputString = "- * + sin 5 sqrt(24) cos sin 1 * - cos sin cos sin 1 sin 1 + pow(2,12) pow(12,2)"
                        + System.getProperty("line.separator")
                        + "-*+sin5sqrt(24)cossin1*- cossincossin1sin1+pow(2,12)pow(12,2)"
                        + System.getProperty("line.separator") + "0" + System.getProperty("line.separator");

                String outputString = "Result: 115.72404166832604" + System.getProperty("line.separator")
                        + "Result: 115.72404166832604" + System.getProperty("line.separator") + "Shutting down."
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));
                System.setOut(new PrintStream(byteArrayOutputStream));

                calculator.start();

                assertEquals(outputString, byteArrayOutputStream.toString());
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredTrigonometricExpressions1() {
                String inputString = "- cos -1 -sin1"
                        + System.getProperty("line.separator") + "- cos-1-sin 1" + System.getProperty("line.separator")
                        + "cos(-5)" + System.getProperty("line.separator") + "0" + System.getProperty("line.separator");

                String outputString = "Result: 1.3817732906760363" + System.getProperty("line.separator")
                        + "Result: 1.3817732906760363" + System.getProperty("line.separator")
                        + "Result: 0.9961946980917455" + System.getProperty("line.separator") + "Shutting down."
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));
                System.setOut(new PrintStream(byteArrayOutputStream));

                calculator.start();

                assertEquals(outputString, byteArrayOutputStream.toString());
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredTrigonometricExpressions2() {
                String inputString = "sin(-5.2)" + System.getProperty("line.separator") + "-sin(-5.2)"
                        + System.getProperty("line.separator") + "sin-5.2" + System.getProperty("line.separator")
                        + "-sin(2.2)" + System.getProperty("line.separator") + "sin          2"
                        + System.getProperty("line.separator") + "0" + System.getProperty("line.separator");

                String outputString = "Result: 0.8834546557201531" + System.getProperty("line.separator")
                        + "Result: -0.8834546557201531" + System.getProperty("line.separator")
                        + "Result: 0.8834546557201531" + System.getProperty("line.separator")
                        + "Result: -0.8084964038195901" + System.getProperty("line.separator")
                        + "Result: 0.9092974268256817" + System.getProperty("line.separator") + "Shutting down."
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));
                System.setOut(new PrintStream(byteArrayOutputStream));

                calculator.start();

                assertEquals(outputString, byteArrayOutputStream.toString());
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredDifficultTrigonometricExpressions() {
                String inputString
                        = "- * + -sin 5 sqrt(24) cos -sin 1 * - cos -sin cos -sin 1 -sin 1 + pow(2,12) pow(12,2)"
                        + System.getProperty("line.separator")
                        + "- * + -sin5sqrt(24) cos -sin 1 * - cos -sin cos -sin 1 -sin1 + pow( 2 , 12 ) pow12 2"
                        + System.getProperty("line.separator") + "0" + System.getProperty("line.separator");

                String outputString = "Result: -7020.06606487991" + System.getProperty("line.separator")
                        + "Result: -7020.06606487991" + System.getProperty("line.separator") + "Shutting down."
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));
                System.setOut(new PrintStream(byteArrayOutputStream));

                calculator.start();

                assertEquals(outputString, byteArrayOutputStream.toString());
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredLogarithmicExpressions() {
                String inputString = "log 25 - 25 25"
                        + System.getProperty("line.separator") + "log25- 25 25" + System.getProperty("line.separator")
                        + "0" + System.getProperty("line.separator");

                String outputString = "Result: -Infinity" + System.getProperty("line.separator") + "Result: -Infinity"
                        + System.getProperty("line.separator") + "Shutting down."
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));
                System.setOut(new PrintStream(byteArrayOutputStream));

                calculator.start();

                assertEquals(outputString, byteArrayOutputStream.toString());
            }
        }

        @Nested
        @DisplayName("Exceptions tests")
        class ExceptionsTests {

            @Test
            void should_ThrowIllegalArgumentException_When_AttemptedToDivideByZero() {
                String inputString = "/17 0" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("Cannot divide by zero.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_LogarithmOfZeroWithBaseZeroIsToBeCalculated() {
                String inputString = "log0 0" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The logarithm of 0 with a base 0 is undefined.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_LogarithmWithBaseOneIsToBeCalculated() {
                String inputString = "-log(1, 12)" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The logarithm of 12.0 with a base 1 is undefined.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_LogarithmWithNegativeBaseIsToBeCalculated() {
                String inputString = "log(-25, 5)" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals(
                        "The base of a logarithm must be a positive real number.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_LogarithmOfNegativeNumberIsToBeCalculated() {
                String inputString = "log(6, -25)" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The logarithm of a negative number with a base 6.0 is undefined.",
                        exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_SquareRootOfNegativeNumberIsToBeCalculated() {
                String inputString = "sqrt -5" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals(
                        "The square root of a negative number does not exist among the set of Real Numbers.",
                        exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionDoesNotHaveEnoughOperands1() {
                String inputString = "- * sin 5 log(2,4)" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The prefix expression does not have enough operands.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionHasTooManyOperands() {
                String inputString = "- * 2 5 3 sing 5 2" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The prefix expression has too many tokens.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionDoesNotHaveEnoughOperands2() {
                String inputString = "cos sin" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The prefix expression does not have enough operands.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionStartsWithANumber() {
                String inputString = "25 6" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The prefix expression cannot start with a number.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionHasTooManyTokens1() {
                String inputString = "- 25 5 -" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The prefix expression has too many tokens.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionHasTooManyTokens2() {
                String inputString = "- 25 5 5" + System.getProperty("line.separator") + "0"
                        + System.getProperty("line.separator");

                System.setIn(new ByteArrayInputStream(inputString.getBytes()));

                Exception exception = assertThrows(IllegalArgumentException.class, calculator::start);
                assertEquals("The prefix expression has too many tokens.", exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Tests for the evaluate method")
    class CalculateTests {
        private final PrefixCalculator calculator = new PrefixCalculator();

        @Nested
        @DisplayName("Correct input tests")
        class CorrectInputTests {

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredExpression() {
                assertEquals(calculator.calculate("- + 6 -2 3"), 1.0);
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredDifficultExpression()  {
                assertEquals(
                        calculator.calculate("-*+sin5sqrt(24)cossin1*- cossincossin1sin1+pow(2,12)pow(12,2)"),
                        115.72404166832604);
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredTrigonometricExpressions1() {
                assertEquals(calculator.calculate("- cos -1 -sin1"), 1.3817732906760363);
                assertEquals(calculator.calculate("- cos-1-sin 1"), 1.3817732906760363);
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredTrigonometricExpressions2() {
                assertEquals(calculator.calculate("sin(-5.2)"), 0.8834546557201531);
                assertEquals(calculator.calculate("sin-5.2"), 0.8834546557201531);
                assertEquals(calculator.calculate("-sin -5.2"), -0.8834546557201531);
            }

            @Test
            void should_PrintCorrectAnswer_When_UserEnteredLogarithmicExpressions() {
                assertEquals(calculator.calculate("log 25 - 25 25"), Double.NEGATIVE_INFINITY);
                assertEquals(calculator.calculate("log25- 25 25"), Double.NEGATIVE_INFINITY);
            }
        }

        @Nested
        @DisplayName("Exceptions tests")
        class ExceptionsTests {

            @Test
            void should_ThrowIllegalArgumentException_When_AttemptedToDivideByZero() {
                Exception exception
                        = assertThrows(IllegalArgumentException.class, () -> calculator.calculate("/51 0"));

                assertEquals("Cannot divide by zero.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_LogarithmOfZeroWithBaseZeroIsToBeCalculated() {
                Exception exception
                        = assertThrows(IllegalArgumentException.class, () -> calculator.calculate("log0 0"));

                assertEquals("The logarithm of 0 with a base 0 is undefined.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_SquareRootOfNegativeNumberIsToBeCalculated() {
                Exception exception
                        = assertThrows(IllegalArgumentException.class, () -> calculator.calculate("sqrt -5"));

                assertEquals(
                        "The square root of a negative number does not exist among the set of Real Numbers.",
                        exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionDoesNotHaveEnoughOperands1() {
                Exception exception = assertThrows(IllegalArgumentException.class,
                        () -> calculator.calculate("- * sin 5 log(2,4)"));

                assertEquals("The prefix expression does not have enough operands.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionHasTooManyTokens1() {
                Exception exception = assertThrows(IllegalArgumentException.class,
                        () -> calculator.calculate("- * 2 5 3 sing 5 2"));

                assertEquals("The prefix expression has too many tokens.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionDoesNotHaveEnoughOperands2() {
                Exception exception
                        = assertThrows(IllegalArgumentException.class, () -> calculator.calculate("cos sin"));

                assertEquals("The prefix expression does not have enough operands.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionStartsWithANumber() {
                Exception exception
                        = assertThrows(IllegalArgumentException.class, () -> calculator.calculate("26 7"));

                assertEquals("The prefix expression cannot start with a number.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionHasTooManyTokens2() {
                Exception exception
                        = assertThrows(
                                IllegalArgumentException.class, () -> calculator.calculate("- 25 5 -"));

                assertEquals("The prefix expression has too many tokens.", exception.getMessage());
            }

            @Test
            void should_ThrowIllegalArgumentException_When_ExpressionHasTooManyTokens3() {
                Exception exception
                        = assertThrows(
                                IllegalArgumentException.class, () -> calculator.calculate("- 25 5 5"));

                assertEquals("The prefix expression has too many tokens.", exception.getMessage());
            }
        }
    }
}