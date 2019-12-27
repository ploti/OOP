package ru.nsu.fit.oop.arturploter.task_3_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyGregorianCalendarUnitTests {
    private final MyGregorianDate date = new MyGregorianDate(17, 12, 2019);
    private MyGregorianCalendar calendar;

    @BeforeEach
    void setCalendar() {
        calendar = new MyGregorianCalendar(date);
    }

    @Test
    void addDaysAndGetDate1() {
        calendar.addDays(14);
        assertEquals(new MyGregorianDate(31, 12, 2019), calendar.getDate());
    }

    @Test
    void addDaysAndGetDate2() {
        calendar.addDays(1624);
        assertEquals(new MyGregorianDate(28, 5, 2024), calendar.getDate());
    }

    @Test
    void addDaysAndGetDate3() {
        calendar.addDays(13555);
        assertEquals(new MyGregorianDate(26, 1, 2057), calendar.getDate());
    }

    @Test
    void addDaysAndGetDate_DaysIsLessThanOrEqualToOne_IllegalArgumentExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calendar.addDays(-56));
        assertEquals("The date number must be greater than zero.", exception.getMessage());
    }

    @Test
    void subtractDaysAndGetDate1() {
        calendar.subtractDays(11);
        assertEquals(new MyGregorianDate(6, 12, 2019), calendar.getDate());
    }

    @Test
    void subtractDaysAndGetDate2() {
        calendar.subtractDays(243);
        assertEquals(new MyGregorianDate(18, 4, 2019), calendar.getDate());
    }

    @Test
    void subtractDaysAndGetDate3() {
        calendar.subtractDays(13555);
        assertEquals(new MyGregorianDate(6, 11, 1982), calendar.getDate());
    }

    @Test
    void subtractDaysAndGetDate_DaysIsLessThanOrEqualToOne_IllegalArgumentExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calendar.subtractDays(-2));
        assertEquals("The date number must be greater than zero.", exception.getMessage());
    }

    @Test
    void getDateInXDays1() {
        assertEquals(new MyGregorianDate(28, 12, 2019), calendar.getDateInXDays(11));
    }

    @Test
    void getDateInXDays2() {
        assertEquals(new MyGregorianDate(21, 7, 2023), calendar.getDateInXDays(1312));
    }

    @Test
    void getDateInXDays3() {
        assertEquals(new MyGregorianDate(20, 10, 2059), calendar.getDateInXDays(14552));
    }

    @Test
    void getMonthInXWeeks1() {
        assertEquals(1, calendar.getMonthInXWeeks(4));
    }

    @Test
    void getMonthInXWeeks2() {
        assertEquals(10, calendar.getMonthInXWeeks(42));
    }

    @Test
    void getMonthInXWeeks3() {
        assertEquals(12, calendar.getMonthInXWeeks(522));
    }

    @Test
    void getMonthInXWeeks4() {
        assertEquals(3, calendar.getMonthInXWeeks(1211));
    }

    @Test
    void getMonthInXWeeks5() {
        assertEquals(4, calendar.getMonthInXWeeks(4245));
    }

    @Test
    void getMonthInXWeeks6() {
        assertEquals(5, calendar.getMonthInXWeeks(7432));
    }

    @Test
    void getMonthInXWeeks7() {
        assertEquals(10, calendar.getMonthInXWeeks(15122));
    }

    @Test
    void getMonthInXWeeks_WeeksIsLessThanOrEqualToOne_IllegalArgumentExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calendar.getMonthInXWeeks(0));
        assertEquals("The date number must be greater than zero.", exception.getMessage());
    }

    @Test
    void getDayOfTheWeekInXDays1() {
        assertEquals(2, calendar.getDayOfTheWeekInXDays(14));
    }

    @Test
    void getDayOfTheWeekInXDays2() {
        assertEquals(2, calendar.getDayOfTheWeekInXDays(1624));
    }

    @Test
    void getDayOfTheWeekInXDays3() {
        assertEquals(5, calendar.getDayOfTheWeekInXDays(13555));
    }

    @Test
    void getDayOfTheWeekInXDays_DaysIsLessThanOrEqualToOne_IllegalArgumentExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calendar.getDayOfTheWeekInXDays(-4));
        assertEquals("The date number must be greater than zero.", exception.getMessage());
    }

    @Test
    void getDayOfTheWeekIWasBornGetDateOfBirthAndSetDateOfBirth1() {
        calendar.setDateOfBirth(new MyGregorianDate(4, 2, 2000));
        assertEquals(new MyGregorianDate(4, 2, 2000), calendar.getDateOfBirth());
        assertEquals(5, calendar.getDayOfTheWeekIWasBorn());
    }

    @Test
    void getDayOfTheWeekIWasBornGetDateOfBirthAndSetDateOfBirth2() {
        calendar.setDateOfBirth(new MyGregorianDate(3, 12, 1925));
        assertEquals(new MyGregorianDate(3, 12, 1925), calendar.getDateOfBirth());
        assertEquals(4, calendar.getDayOfTheWeekIWasBorn());
    }

    @Test
    void getDayOfTheWeekIWasBorn_DateOfBirthIsNotSet_IllegalStateExceptionThrown() {
        Exception exception = assertThrows(IllegalStateException.class, () -> calendar.getDayOfTheWeekIWasBorn());
        assertEquals("dateOfBirth is not set.", exception.getMessage());
    }

    @Test
    void setDateOfBirth_IncorrectDateOfBirth_IllegalArgumentExceptionThrown1() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> calendar.setDateOfBirth(new MyGregorianDate(2, 12, 1900)));
        assertEquals("Invalid date.", exception.getMessage());
    }

    @Test
    void setDateOfBirth_IncorrectDateOfBirth_IllegalArgumentExceptionThrown2() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> calendar.setDateOfBirth(new MyGregorianDate(2, 12, 2020)));
        assertEquals("Invalid date.", exception.getMessage());
    }

    @Test
    void getYearsMonthsDaysPassedSince1() {
        assertEquals(new DateDifference(74, 7, 8),
                calendar.getYearsMonthsDaysPassedSince(new MyGregorianDate(9, 5, 1945)));
    }

    @Test
    void getYearsMonthsDaysPassedSince2() {
        assertEquals(new DateDifference(3, 1, 5),
                calendar.getYearsMonthsDaysPassedSince(new MyGregorianDate(12, 11, 2016)));
    }

    @Test
    void getYearsMonthsDaysPassedSince3() {
        assertEquals(new DateDifference(1, 11, 20),
                calendar.getYearsMonthsDaysPassedSince(new MyGregorianDate(27, 12, 2017)));
    }

    @Test
    void getYearsMonthsDaysPassedSince4() {
        assertEquals(new DateDifference(1, 11, 20),
                calendar.getYearsMonthsDaysPassedSince(new MyGregorianDate(27, 12, 2017)));
    }

    @Test
    void getYearsMonthsDaysPassedSince5() {
        calendar = new MyGregorianCalendar(new MyGregorianDate(5, 1, 2010));

        assertEquals(new DateDifference(9, 0, 27),
                calendar.getYearsMonthsDaysPassedSince(new MyGregorianDate(9, 12, 2000)));
    }

    @Test
    void getYearsMonthsDaysPassedSince_DateIsGreaterOrEqualToTheDateSetInTheCalendar_IllegalArgumentExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> calendar.getYearsMonthsDaysPassedSince(new MyGregorianDate(9, 12, 2020)));
        assertEquals("date must be less than or equal to the current date.", exception.getMessage());
    }

    @Test
    void getDaysUntilNewYearsEve1() {
        assertEquals(14, calendar.getDaysUntilNewYearsEve());
    }

    @Test
    void getDaysUntilNewYearsEve2() {
        assertEquals(745, calendar.getDaysUntilNewYearsEve(2021));
    }

    @Test
    void getDaysUntilNewYearsEve3() {
        assertEquals(1841, calendar.getDaysUntilNewYearsEve(2024));
    }

    @Test
    void getDaysUntilNewYearsEve_YearLessThanTheCurrentYear_IllegalArgumentExceptionThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> calendar.getDaysUntilNewYearsEve(2018));
        assertEquals("year must be greater or equal to the current year in the calendar.",
                exception.getMessage());
    }

    @Test
    void getNextFridayThe13th1() {
        assertEquals(new MyGregorianDate(13, 3, 2020), calendar.getNextFridayThe13th());
    }

    @Test
    void getNextFridayThe13th2() {
        calendar = new MyGregorianCalendar(new MyGregorianDate(1, 5, 2019));
        assertEquals(new MyGregorianDate(13, 9, 2019), calendar.getNextFridayThe13th());
    }

    @Test
    void getNextFridayThe13th3() {
        calendar = new MyGregorianCalendar(new MyGregorianDate(11, 11, 2019));
        assertEquals(new MyGregorianDate(13, 12, 2019), calendar.getNextFridayThe13th());
    }

    @Test
    void getNextFridayThe13th4() {
        calendar = new MyGregorianCalendar(new MyGregorianDate(11, 12, 2020));
        assertEquals(new MyGregorianDate(13, 8, 2021), calendar.getNextFridayThe13th());
    }
}