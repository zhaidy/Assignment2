package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataManager{
    // This is the actual database
    private SQLiteDatabase db;

    /* Next we have a public static final string for
       each row/table that we need to refer to both
       inside and outside this class
    */
    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_SCANNEDQRCODE = "code";
    public static final String TABLE_ROW_TYPE = "type";
    public static final String TABLE_ROW_IMAGE = "image";
    public static final String TABLE_ROW_DATE = "date";

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
            db.execSQL(
                    "create table " +  QR_Table + "(" +
                            TABLE_ROW_ID + " integer primary key autoincrement not null, " +
                            TABLE_ROW_SCANNEDQRCODE   + " text, " +
                            TABLE_ROW_TYPE  + " text, " +
                            TABLE_ROW_IMAGE  + " text, " +
                            TABLE_ROW_DATE + " date " + ")"
            );
        }

        // This method only runs when we increment DB_VERSION
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + QR_Table + "");
            onCreate(db);
        }
    }

//    public void insertHistory(String type, String code, Bitmap img){
//        String query = "INSERT INTO " + QR_Table + " (" + TABLE_ROW_SCANNEDQRCODE + ") " + "VALUES (" + "'" + code + "'" + ");";
//        Log.i("insert() + ", query);
//        db.execSQL(query);
//    }

    public boolean insertHistory(String type, String code, Bitmap img)
    {
        // Prepare the row to insert
        ContentValues contentValues = new ContentValues();

        contentValues.put(TABLE_ROW_TYPE, type);
        if(code != null && code != "") {
            contentValues.put(TABLE_ROW_SCANNEDQRCODE, code);
        }
        if(img != null) {
            byte[] data = getBitmapAsByteArray(img);
            contentValues.put(TABLE_ROW_IMAGE, data);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        contentValues.put(TABLE_ROW_DATE, dateFormat.format(date));

        // Insert the row
        db.insert(QR_Table, null, contentValues);
        return true;
    }

    public Cursor getHistory(){
        String query = "SELECT  * FROM " + QR_Table + ";";
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
