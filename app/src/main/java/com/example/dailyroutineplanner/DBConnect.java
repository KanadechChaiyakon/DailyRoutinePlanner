package com.example.dailyroutineplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConnect extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public DBConnect(@Nullable Context context) {
        super(context,"Activity.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("CREATE TABLE 'activity' ('activityID' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,'activityname' TEXT, 'day' TEXT, 'date' TEXT, 'location' TEXT, 'detail' TEXT, 'startTime' TEXT, 'endTime' TEXT)");
            sqLiteDatabase.execSQL("CREATE TABLE 'summary' ('summaryID' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'success' INTEGER, 'miss' INTEGER)");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
