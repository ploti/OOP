package ru.nsu.fit.oop.arturploter.task_3_1;

public abstract class MyDate implements Comparable<MyDate> {
    protected int day;
    protected int month;
    protected int year;

    public MyDate(int day, int month, int year) {
        if (!validateDate(day, month, year)) {
            throw new IllegalArgumentException("Invalid date.");
        }

        this.day = day;
        this.month = month;
        this.year = year;
    }

    public MyDate(MyDate date) {
        day = date.getDay();
        month = date.getMonth();
        year = date.getYear();
    }

    /**
     * Returns the day of the week of the date.
     *
     * @return  the day of the week of the date
     */
    abstract public int getDayOfTheWeek();

    abstract protected boolean validateDate(int date, int month, int year);

    /**
     * Returns the day of the date.
     *
     * @return  the day of the date.
     */
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Returns the month of the date.
     *
     * @return  the month of the date.
     */
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Returns the year of the date.
     *
     * @return  the year of the date.
     */
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }

        final MyDate date = (MyDate) obj;

        return (day == date.day && month == date.month && year == date.year);
    }

    @Override
    public int compareTo(MyDate o) {
        if (year != o.year) {
            if (year < o.year) {
                return -1;
            } else {
                return 1;
            }
        } else if (month != o.month) {
            if (month < o.month) {
                return -1;
            } else {
                return 1;
            }
        } else if (day != o.day) {
            if (day < o.day) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }
}