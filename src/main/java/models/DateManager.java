package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateManager {
    public static String convertDateToString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String stringDate = calendar.get(Calendar.DAY_OF_MONTH) + "." +
                (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);

        return stringDate;
    }

    public static Date convertStringToDate(String sDate) {
        Date date;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            date = dateFormatter.parse(sDate);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        String dateToday = dtf.format(now);
        return dateToday;
    }

    public static long getDaysBetweenDates(Date dateOne, Date dateTwo) {
        long diff = dateOne.getTime() - dateTwo.getTime();
        long daysBetweenDates = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return daysBetweenDates;
    }

    public static boolean isDateBetween(Date date, Date start, Date end) {
        return start.compareTo(date) * date.compareTo(end) > 0;
    }
}
