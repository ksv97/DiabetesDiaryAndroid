package com.example.diabetesdiary;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Класс, который служит основной моделью данных для дневника самоконтроля инсулинотерапии
 */
public class DiaryItem implements Serializable {

    private int id;
    private Calendar date;

    /**
     * Количество инсулина короткого действия.
     *
     * Данное разбиение сделано с целью возможного расширения до добавления инсулина продленного действия
     */
    private float insulinRapidCount;

    /**
     * Количество хлебных единиц (ХЕ), употребленных в пищу
     */
    private float carbUnits;

    /**
     * Текущий уровень глюкозы крови
     */
    private float sugarLevel;
    private String note;


    public DiaryItem() {
        date = GregorianCalendar.getInstance();
        note = "";
    }

    private DiaryItem(int id, float insulinRapidCount, float carbUnits, float sugarLevel, String note) {
        this.id = id;
        this.insulinRapidCount = insulinRapidCount;
        this.carbUnits = carbUnits;
        this.sugarLevel = sugarLevel;
        this.note = note;
    }

    public DiaryItem(int id, float insulinRapidCount, float carbUnits, float sugarLevel, String note,  Calendar date) {
        this(id,insulinRapidCount,carbUnits,sugarLevel,note);
        this.date = date;
    }

    public DiaryItem(int id, float insulinRapidCount, float carbUnits, float sugarLevel, String note,  long dateInMillis) {
        this(id,insulinRapidCount,carbUnits,sugarLevel,note);

        Date date = new Date(dateInMillis);
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(date);
        this.date = c;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public float getInsulinRapidCount() {
        return insulinRapidCount;
    }

    public void setInsulinRapidCount(float insulinRapidCount) {
        this.insulinRapidCount = insulinRapidCount;
    }

    public float getCarbUnits() {
        return carbUnits;
    }

    public void setCarbUnits(float carbUnits) {
        this.carbUnits = carbUnits;
    }

    public float getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(float sugarLevel) {
        this.sugarLevel = sugarLevel;
    }
}
