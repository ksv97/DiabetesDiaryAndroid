package com.example.diabetesdiary;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DiaryItemChartModel {

    private float sugarLevel;
    private Calendar date;

    public DiaryItemChartModel(float sugarLevel, long dateInMillis) {
        this.sugarLevel = sugarLevel;
        Date dateFromMills = new Date(dateInMillis);
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(dateFromMills);
        this.date = c;
    }

    public float getSugarLevel() {
        return sugarLevel;
    }

    public Calendar getDate() {
        return date;
    }

}
