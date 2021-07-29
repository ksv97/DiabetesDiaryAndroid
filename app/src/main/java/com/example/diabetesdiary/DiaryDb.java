package com.example.diabetesdiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DiaryDb {

    public static final String DB_NAME = "diabetes_diary.db";
    public static final int DB_VERSION = 1;
    public static final String DIARY_ITEMS_TABLE_NAME = "DiaryItems";

    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_SUGAR_LEVEL = "SugarLevel";
    public static final String COLUMN_CARB_UNITS = "CarbUnits";
    public static final String COLUMN_INSULIN = "Insulin";
    public static final String COLUMN_NOTE = "Note";

    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_DATE = 1;
    public static final int NUM_COLUMN_SUGAR_LEVEL = 2;
    public static final int NUM_COLUMN_CARB_UNITS = 3;
    public static final int NUM_COLUMN_INSULIN = 4;
    public static final int NUM_COLUMN_NOTE = 5;

    private SQLiteDatabase database;

    public DiaryDb(Context ctx) {
        database = new DbOpenHelper(ctx).getWritableDatabase();
    }



    private class DbOpenHelper extends SQLiteOpenHelper {

        public DbOpenHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + DIARY_ITEMS_TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DATE + " TEXT NOT NULL,"
                    + COLUMN_SUGAR_LEVEL + " REAL,"
                    + COLUMN_CARB_UNITS + " REAL,"
                    + COLUMN_INSULIN + " REAL,"
                    + COLUMN_NOTE + " TEXT)";
            database.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DIARY_ITEMS_TABLE_NAME);
            onCreate(db);
        }


    }

}
