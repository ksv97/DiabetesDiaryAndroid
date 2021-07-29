package com.example.diabetesdiary;

import java.io.Serializable;
import java.util.Calendar;

public class DiaryItem implements Serializable {

    private int id;
    private Calendar date;
    private float insulinRapidCount;
    private float carbUnits;
    private float glucoseLevel;
    private String note;


    public DiaryItem(int id, Calendar date, float insulinRapidCount, float carbUnits, float glucoseLevel, String note) {
        this.id = id;
        this.date = date;
        this.insulinRapidCount = insulinRapidCount;
        this.carbUnits = carbUnits;
        this.glucoseLevel = glucoseLevel;
        this.note = note;
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

    public float getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(float glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }
}
