package ydrasaal.alligregator.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lchazal on 13/10/15.
 */
public class DateConverter {

    public static final String TIMESTAMP_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

    public static Calendar parseTimestamp(String timestamp) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.FRANCE);

        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(dateFormat.parse(timestamp));
        } catch (ParseException e) {
            Log.d("DateHelper", "Failed to convert string " + timestamp);
            return null;
        }
        return calendar;
    }

    public static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static boolean isSameDay(Calendar firstDate, Calendar secondDate) {
        boolean sameDay = firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR) &&
                firstDate.get(Calendar.DAY_OF_YEAR) == secondDate.get(Calendar.DAY_OF_YEAR);

        return sameDay;
    }

    public static int dayComparator(Calendar firstDate, Calendar secondDate) {
        return firstDate.compareTo(secondDate); //-1 if firstDate is before, 1 if firstDate is after, 0 if equal
    }
}
