package com.example.assignment2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager {
    // This is the actual database
    private SQLiteDatabase db;

    /* Next we have a public static final string for
       each row/table that we need to refer to both
       inside and outside this class
    */
    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_QRCODE = "code";
    public static final String TABLE_ROW_TYPE = "type";

    /*
        Next we have a private static final strings for
        each row/table that we need to refer to just
        inside this class
    */
    private static final String DB_NAME = "QR_DB";
    private static final int DB_VERSION = 1;
    private static final String QR_Table = "QRHistory";

    public DataManager(Context context) {
        // Create an instance of our internal CustomSQLiteOpenHelper class
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        // Get a writable database
        db = helper.getWritableDatabase();
    }

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        // This method only runs the first time the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create a table for photos and all their details
            String newTableQueryString = "create table " + QR_Table + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null," + TABLE_ROW_QRCODE
                    + " text not null,"
                    + TABLE_ROW_TYPE
                    + " text not null);";
            db.execSQL(newTableQueryString);
        }

        // This method only runs when we increment DB_VERSION
        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
        }
    }

}