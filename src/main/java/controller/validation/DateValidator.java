package controller.validation;

import java.util.Arrays;

public class DateValidator {
    private static final String DECLIMER = "\\.";

    public boolean verify(String date) {
        String[] splittedDate = date.split(DECLIMER);

        if (splittedDate.length != 3) {
            return false;
        }
        int day, month, year;
        try {
            day = Integer.valueOf(splittedDate[0]);
            month = Integer.valueOf(splittedDate[1]);
            year = Integer.valueOf(splittedDate[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        if (year < 1000 || year > 9999) {
            return false;
        }
        if (month < 1 || month > 12 || day < 1 || year < 0) {
            return false;
        }
        Integer[] thirtyOneDays = {1, 3, 5, 7, 8, 10, 12};
        if (Arrays.asList(thirtyOneDays).contains(month)) {
            if (day > 31) {
                return false;
            }
        } else if (month == 2) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {  //leap year
                if (day > 29) {
                    return false;
                }
            } else {
                if (day > 28) {
                    return false;
                }
            }
        } else {
            if (day > 30) {
                return false;
            }
        }

        return true;
    }
}
