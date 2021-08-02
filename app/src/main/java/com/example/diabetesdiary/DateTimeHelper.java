package com.example.diabetesdiary;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeHelper {

    /**
     * Устанавливает время calendar в формате HH:mm в поле view
     *
     */
    public static void updateTimeViewLabel(TextView view, Calendar calendar) {
        String timeFormatString = "HH:mm";
        SimpleDateFormat timeFormat = new SimpleDateFormat(timeFormatString, Locale.getDefault());
        timeFormat.setTimeZone(calendar.getTimeZone());
        view.setText(timeFormat.format(calendar.getTime()));
    }

    /**
     * Устанавливает дату calendar в формате EEE, d MMM yyyy в поле view
     *
     */
    public static void updateDateViewLabel(TextView view, Calendar calendar) {
        String dateFormatString = "EEE, d MMM yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString, Locale.getDefault());
        view.setText(dateFormat.format(calendar.getTime()));
    }

}
