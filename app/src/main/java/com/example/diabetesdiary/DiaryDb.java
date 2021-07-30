package com.example.diabetesdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DiaryDb {

    private class DbOpenHelper extends SQLiteOpenHelper {

        public DbOpenHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + DIARY_ITEMS_TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DATE + " INTEGER NOT NULL,"
                    + COLUMN_SUGAR_LEVEL + " REAL,"
                    + COLUMN_CARB_UNITS + " REAL,"
                    + COLUMN_INSULIN_RAPID + " REAL,"
                    + COLUMN_NOTE + " TEXT)";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DIARY_ITEMS_TABLE_NAME);
            onCreate(db);
        }



    }

    public static final String DB_NAME = "diabetes_diary.db";
    public static final int DB_VERSION = 1;
    public static final String DIARY_ITEMS_TABLE_NAME = "DiaryItems";

    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_SUGAR_LEVEL = "SugarLevel";
    public static final String COLUMN_CARB_UNITS = "CarbUnits";
    public static final String COLUMN_INSULIN_RAPID = "InsulinRapid";
    public static final String COLUMN_NOTE = "Note";

    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_DATE = 1;
    public static final int NUM_COLUMN_SUGAR_LEVEL = 2;
    public static final int NUM_COLUMN_CARB_UNITS = 3;
    public static final int NUM_COLUMN_INSULIN_RAPID = 4;
    public static final int NUM_COLUMN_NOTE = 5;

    private SQLiteDatabase database;

    public DiaryDb(Context ctx) {
        database = new DbOpenHelper(ctx).getWritableDatabase();
    }

    public long insert(DiaryItem item) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, item.getDate().getTimeInMillis());
        values.put(COLUMN_SUGAR_LEVEL, item.getSugarLevel());
        values.put(COLUMN_CARB_UNITS, item.getCarbUnits());
        values.put(COLUMN_INSULIN_RAPID, item.getInsulinRapidCount());
        values.put(COLUMN_NOTE, item.getNote());
        return database.insert(DIARY_ITEMS_TABLE_NAME,null,values);
    }

    public void update(DiaryItem item) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, item.getDate().getTimeInMillis());
        values.put(COLUMN_SUGAR_LEVEL, item.getSugarLevel());
        values.put(COLUMN_CARB_UNITS, item.getCarbUnits());
        values.put(COLUMN_INSULIN_RAPID, item.getInsulinRapidCount());
        values.put(COLUMN_NOTE, item.getNote());
        database.update(DIARY_ITEMS_TABLE_NAME, values,COLUMN_ID + " = ?", new String[] {item.getId()+""});
    }

    public void delete(long id) {
        database.delete(DIARY_ITEMS_TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }


    public ArrayList<DiaryItem> selectAll() {
        Cursor cursor = database.query(DIARY_ITEMS_TABLE_NAME,null,null,null,null,null,COLUMN_DATE + " DESC");
        ArrayList<DiaryItem> items = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(NUM_COLUMN_ID);
            long dateInMillis = cursor.getLong(NUM_COLUMN_DATE);
            float sugarLevel = cursor.getFloat(NUM_COLUMN_SUGAR_LEVEL);
            float carbUnits = cursor.getFloat(NUM_COLUMN_CARB_UNITS);
            float insulinRapid = cursor.getFloat(NUM_COLUMN_INSULIN_RAPID);
            String note = cursor.getString(NUM_COLUMN_NOTE);
            DiaryItem item = new DiaryItem(id,insulinRapid, carbUnits, sugarLevel,note,dateInMillis);
            items.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return items;

    }



}
