package ru.nsu.fit.oop.arturploter.task_3_1;

/**
 * <p>The {@code MyCalendar} interface represents a calendar that holds the current date and the date of birth.
 * It provides methods to add to or subtract from the current date, find out which date is X days after
 * the current date, get month, day of the week X weeks after the current date, get birth day of the week, find out
 * how many years, month, days has passed since the specified date. It also provides methods to get days
 * until New Year X, where X is the year specified by the user, the next Friday the 13th, the current date, specified
 * date of birth, and method to set your date of birth.
 *
 * @author  Artur Ploter
 */
public interface MyCalendar {

    /**
     * Adds days to the current date.
     *
     * @param  days   the number of days to be added to the current date.
     */
    void addDays(int days);

    /**
     * Subtracts days from the current date.
     *
     * @param  days   the number of days to be subtracted from the current date.
     */
    void subtractDays(int days);

    /**
     * Returns the object that represents the date {@code days} days after the current date.
     *
     * @param   days   the number of days to be added to the current date to get the new date
     * @return  the object that represents the date {@code days} days after the current date
     */
    MyDate getDateInXDays(int days);

    /**
     * Returns a month number of the date created by adding {@code weeks} weeks to the current date.
     *
     * @param   weeks   the number of weeks to be added to the current date
     * @return  a month number of the date
     */
    int getMonthInXWeeks(int weeks);

    /**
     * Returns the day of the week of the date created by adding {@code days} days to the current date.
     *
     * @param   days   the number of days to be added to the current date
     * @return  the day of the week
     */
    int getDayOfTheWeekInXDays(int days);

    /**
     * Returns the day of the week of the date set as the date of birth.
     *
     * @return  the day of the week of the date set as the date of birth
     */
    int getDayOfTheWeekIWasBorn();

    /**
     * Returns the {@code DateDifference} object that represents the number of years, months, days passed since
     * the specified date.
     *
     * @param   date   the start date
     * @return  the {@code DateDifference} object that represents the number of years, months, days
     */
    DateDifference getYearsMonthsDaysPassedSince(MyDate date);

    /**
     * Returns the number of days until New Year's Day.
     *
     * @return  the number of days until New Year's Day
     */
    int getDaysUntilNewYearsEve();

    /**
     * Returns the number of days until New Year {@code year}.
     *
     * @param   year   the New Year's Eve year
     * @return  the number of days until New Year {@code year}
     */
    long getDaysUntilNewYearsEve(int year);

    /**
     * Returns the object that represents the date of the next Friday the 13th.
     *
     * @return  the date of the next Friday the 13th.
     */
    MyDate getNextFridayThe13th();

    /**
     * Returns the object that represents the current date.
     *
     * @return  the current date
     */
    MyDate getDate();

    /**
     * Returns the object that represents the date of birth set by the user.
     *
     * @return  the date that represents the date of birth
     */
    MyDate getDateOfBirth();

    /**
     * Sets the date of birth.
     *
     * @param  dateOfBirth   the date of birth to be set
     */
    void setDateOfBirth(MyDate dateOfBirth);
}
