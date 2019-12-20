package ru.nsu.fit.oop.arturploter.task_3_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GregorianDateUnitTests {
    private GregorianDate date;

    @Test
    void initGregorianDate_IncorrectDate_IllegalArgumentExceptionThrown1() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new GregorianDate(12, 0, 1224));
        assertEquals("Invalid date.", exception.getMessage());
    }

    @Test
    void initGregorianDate_IncorrectDate_IllegalArgumentExceptionThrown3() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new GregorianDate(0, 1, 1224));
        assertEquals("Invalid date.", exception.getMessage());
    }

    @Test
    void initGregorianDate_IncorrectDate_IllegalArgumentExceptionThrown2() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new GregorianDate(32, 1, 1224));
        assertEquals("Invalid date.", exception.getMessage());
    }

    @Test
    void initGregorianDate_IncorrectDate_IllegalArgumentExceptionThrown4() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new GregorianDate(29, 2, 2013));
        assertEquals("Invalid date.", exception.getMessage());
    }

    @Test
    void getDayOfTheWeek1() {
        GregorianDate date = new GregorianDate(26, 1, 2057);
        assertEquals(5, date.getDayOfTheWeek());
    }

    @Test
    void getDayOfTheWeek2() {
        GregorianDate date = new GregorianDate(4, 2, 2000);
        assertEquals(5, date.getDayOfTheWeek());
    }

    @BeforeEach
    void setDate() {
        date = new GregorianDate(6, 2, 2000);
    }

    @Test
    void getDayOfTheWeek3() {
        assertEquals(0, date.getDayOfTheWeek());
    }

    @Test
    void getDay() {
        assertEquals(6, date.getDay());
    }

    @Test
    void getMonth() {
        assertEquals(2, date.getMonth());
    }

    @Test
    void getYear() {
        assertEquals(2000, date.getYear());
    }
}