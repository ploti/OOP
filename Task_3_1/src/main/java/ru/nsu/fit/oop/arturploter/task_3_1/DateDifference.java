package ru.nsu.fit.oop.arturploter.task_3_1;

public class DateDifference {
    private final int year;
    private final int month;
    private final int day;

    public DateDifference(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Returns the number of years.
     *
     * @return  the number of years
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the number of months.
     *
     * @return  the number of months.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the number of days.
     *
     * @return  the number of days
     */
    public int getDay() {
        return day;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }

        final DateDifference dateDifference = (DateDifference) obj;

        return (year == dateDifference.year && month == dateDifference.month && day == dateDifference.day);
    }
}
