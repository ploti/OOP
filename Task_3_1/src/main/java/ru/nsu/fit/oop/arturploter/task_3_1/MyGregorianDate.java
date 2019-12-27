package ru.nsu.fit.oop.arturploter.task_3_1;

public class MyGregorianDate extends MyDate {
    public MyGregorianDate(int day, int month, int year) {
        super(day, month, year);
    }

    public MyGregorianDate(MyGregorianDate date) {
        super(date);
    }

    @Override
    public int getDayOfTheWeek() {
        int[] monthKeys = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        int y = year;

        if (month < 3) {
            y -= 1;
        }

        return ((y + y / 4 - y / 100 + y / 400 + monthKeys[month - 1] + day) % 7);
    }

    @Override
    protected boolean validateDate(int day, int month, int year) {
        final int LEAP_STEP = 4;

        if (!((day >= 1) && (month >= 1 && month <= 12) && (year >= 0))) {
            return false;
        }

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day < 32;
            case 4:
            case 6:
            case 9:
            case 11:
                return day < 31;
            case 2:
                int modulo100 = year % 100;
                if ((modulo100 == 0 && year % 400 == 0)
                        || (modulo100 != 0 && year % LEAP_STEP == 0)) {
                    return day < 30;
                } else {
                    return day < 29;
                }
            default:
                break;
        }

        return true;
    }
}
