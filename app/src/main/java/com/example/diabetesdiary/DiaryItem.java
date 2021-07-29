package com.example.diabetesdiary;

import java.util.Calendar;

public class DiaryItem {
    private Calendar date;
    private float insulinRapidCount;
    private float carbUnits;
    private float glucoseLevel;

    public DiaryItem(Calendar date, float insulinRapidCount, float carbUnits, float glucoseLevel) {
        this.date = date;
        this.insulinRapidCount = insulinRapidCount;
        this.carbUnits = carbUnits;
        this.glucoseLevel = glucoseLevel;
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
