package ru.nsu.fit.oop.arturploter.task_3_1;

public class MyGregorianCalendar implements MyCalendar {
    private static final int[] maxDayInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private final MyGregorianDate date;
    private MyGregorianDate dateOfBirth;

    public MyGregorianCalendar(MyGregorianDate date) {
        this.date = date;
    }

    @Override
    public void addDays(int days) {
        validateDateNumber(days);
        addDays(date, days);
    }

    @Override
    public void subtractDays(int days) {
        validateDateNumber(days);

        int d = date.getDay();
        int m = date.getMonth();
        int y = date.getYear();

        if ((d - days) > 0) {
            date.setDay(d - days);
            return;
        }

        d = days - d;
        m -= 1;

        if (m < 1) {
            m = 12;
            y -= 1;
        }

        int maxDayInCurMonth = (maxDayInMonth[m] + (m == 2 && isLeapYear(y) ? 1 : 0));

        while (d >= 0) {
            d -= maxDayInCurMonth;

            if (d > 0) {
                m -= 1;

                if (m < 1) {
                    m = 12;
                    y -= 1;
                }
            }

            maxDayInCurMonth = (maxDayInMonth[m] + (m == 2 && isLeapYear(y) ? 1 : 0));
        }

        date.setDay(-d);
        date.setMonth(m);
        date.setYear(y);
    }

    @Override
    public MyGregorianDate getDateInXDays(int days) {
        MyGregorianDate date = new MyGregorianDate(this.date);
        addDays(date, days);

        return date;
    }

    @Override
    public int getDayOfTheWeekInXDays(int days) {
        validateDateNumber(days);

        MyGregorianDate date = getDateInXDays(days);

        return date.getDayOfTheWeek();
    }

    @Override
    public int getMonthInXWeeks(int weeks) {
        validateDateNumber(weeks);

        MyGregorianDate date = new MyGregorianDate(this.date);
        addDays(date, weeks * 7);
        return date.getMonth();
    }

    @Override
    public int getDayOfTheWeekIWasBorn() {
        if (dateOfBirth == null) {
            throw new IllegalStateException("dateOfBirth is not set.");
        }

        return dateOfBirth.getDayOfTheWeek();
    }

    @Override
    public DateDifference getYearsMonthsDaysPassedSince(MyDate date) {
        if (this.date.compareTo(date) < 0) {
            throw new IllegalArgumentException("date must be less than or equal to the current date.");
        }

        int startDay = date.getDay();
        int startMonth = date.getMonth();
        int startYear = date.getYear();

        int endDay = this.date.getDay();
        int endMonth = this.date.getMonth();
        int endYear = this.date.getYear();

        if (endDay < startDay) {
            int previousMonth = endMonth - 1;
            int previousYear = endYear;

            if (previousMonth < 1) {
                previousMonth = 12;
                previousYear -= 1;
            }

            endDay += (maxDayInMonth[previousMonth] + (previousMonth == 2 && isLeapYear(previousYear) ? 1 : 0));
            endMonth -= 1;

            if (endMonth < 1) {
                endMonth += 12;
                endYear -= 1;
            }
        }

        if (endMonth < startMonth) {
            endMonth += 12;
            endYear -= 1;
        }

        return new DateDifference(endYear - startYear, endMonth - startMonth, endDay - startDay);
    }

    @Override
    public int getDaysUntilNewYearsEve() {
        return (int) getDaysBetweenTwoDates(new MyGregorianDate(31, 12, date.getYear()));
    }

    @Override
    public long getDaysUntilNewYearsEve(int year) {
        if (year < date.getYear()) {
            throw new IllegalArgumentException("year must be greater or equal to the current year in the calendar.");
        }

        return getDaysBetweenTwoDates(new MyGregorianDate(31, 12, year));
    }

    @Override
    public MyGregorianDate getNextFridayThe13th() {
        int d = date.getDay();
        int m = date.getMonth();
        int y = date.getYear();
        int w = date.getDayOfTheWeek();

        while (d != 13 || w != 5) {
            w = ((w + 1) % 7);

            int maxDayInCurMonth = (maxDayInMonth[m] + (m == 2 && isLeapYear(y) ? 1 : 0));

            if (d == maxDayInCurMonth) {
                d = 1;

                if (m == 12) {
                    y += 1;
                    m = 1;
                } else {
                    m += 1;
                }
            } else {
                d += 1;
            }
        }

        return new MyGregorianDate(d, m, y);
    }

    @Override
    public MyGregorianDate getDate() {
        return date;
    }

    @Override
    public MyGregorianDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDateOfBirth(MyDate dateOfBirth) {
        if (date.compareTo(
                new MyGregorianDate(dateOfBirth.getDay(), dateOfBirth.getMonth(), dateOfBirth.getYear() + 100)) >= 0
                || date.compareTo(new MyGregorianDate(
                        dateOfBirth.getDay(), dateOfBirth.getMonth(), dateOfBirth.getYear() + 5)) < 0) {

            throw new IllegalArgumentException("Invalid date.");
        }

        this.dateOfBirth = (MyGregorianDate) dateOfBirth;
    }

    private void addDays(MyGregorianDate date, int days) {
        int d = date.getDay() + days;
        int m = date.getMonth();
        int y = date.getYear();

        int maxDayInCurMonth = (maxDayInMonth[m] + (m == 2 && isLeapYear(y) ? 1 : 0));

        while (d > maxDayInCurMonth) {
            d -= maxDayInCurMonth;

            m += 1;
            if (m > 12) {
                m = 1;
                y += 1;
            }

            maxDayInCurMonth = (maxDayInMonth[m] + (m == 2 && isLeapYear(y) ? 1 : 0));
        }

        date.setDay(d);
        date.setMonth(m);
        date.setYear(y);
    }

    private long getDaysBetweenTwoDates(MyGregorianDate date) {
        long daysTotalCurDate;
        long daysTotal;

        daysTotalCurDate = this.date.getYear() * 365 + this.date.getDay();
        for (int i = 0; i < this.date.getMonth() - 1; i++) {
            daysTotalCurDate += maxDayInMonth[i];
        }

        daysTotalCurDate += countLeapYears(this.date);

        daysTotal = date.getYear() * 365 + date.getDay();
        for (int i = 0; i < date.getMonth() - 1; i++) {
            daysTotal += maxDayInMonth[i];
        }

        daysTotal += countLeapYears(date);

        return (daysTotal - daysTotalCurDate);
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }

    private int countLeapYears(MyGregorianDate date) {
        int y = date.getYear();

        if (date.getMonth() <= 2) {
            y -= 1;
        }

        return ((y / 400) - (y / 100) + (y / 4));
    }

    private void validateDateNumber(int dateNumber) {
        if (dateNumber <= 0) {
            throw new IllegalArgumentException("The date number must be greater than zero.");
        }
    }
}