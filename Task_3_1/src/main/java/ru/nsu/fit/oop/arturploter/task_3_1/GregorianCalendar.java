package ru.nsu.fit.oop.arturploter.task_3_1;

public class GregorianCalendar implements MyCalendar {
    private static final int[] maxDayInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private final GregorianDate date;
    private GregorianDate dateOfBirth;

    public GregorianCalendar(GregorianDate date) {
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
    public GregorianDate getDateInXDays(int days) {
        GregorianDate date = new GregorianDate(this.date);
        addDays(date, days);

        return date;
    }

    @Override
    public int getDayOfTheWeekInXDays(int days) {
        validateDateNumber(days);

        GregorianDate date = getDateInXDays(days);

        return date.getDayOfTheWeek();
    }

    @Override
    public int getMonthInXWeeks(int weeks) {
        validateDateNumber(weeks);

        GregorianDate date = new GregorianDate(this.date);
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
    public DateDifference getYearsMonthsDaysPassedSince(GregorianDate date) {
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
        return (int) getDaysBetweenTwoDates(new GregorianDate(31, 12, date.getYear()));
    }

    @Override
    public long getDaysUntilNewYearsEve(int year) {
        if (year < date.getYear()) {
            throw new IllegalArgumentException("year must be greater or equal to the current year in the calendar.");
        }

        return getDaysBetweenTwoDates(new GregorianDate(31, 12, year));
    }

    @Override
    public GregorianDate getNextFridayThe13th() {
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

        return new GregorianDate(d, m, y);
    }

    @Override
    public GregorianDate getDate() {
        return date;
    }

    @Override
    public GregorianDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDateOfBirth(GregorianDate dateOfBirth) {
        if (date.compareTo(
                new GregorianDate(dateOfBirth.getDay(), dateOfBirth.getMonth(), dateOfBirth.getYear() + 100)) >= 0
                || date.compareTo(new GregorianDate(
                        dateOfBirth.getDay(), dateOfBirth.getMonth(), dateOfBirth.getYear() + 5)) < 0) {

            throw new IllegalArgumentException("Invalid date.");
        }

        this.dateOfBirth = dateOfBirth;
    }

    private void addDays(GregorianDate date, int days) {
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

    private long getDaysBetweenTwoDates(GregorianDate date) {
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

    private int countLeapYears(GregorianDate date) {
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