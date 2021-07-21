package com.example.assignment2;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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
                            TABLE_ROW_IMAGE  + " blob, " +
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
            byte[] data = getBytes(img);
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
        String query = "SELECT  * FROM " + QR_Table + " order by _id desc;";
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap TextToImageEncode(Context context, int QRcodeWidth, String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                if (bitMatrix.get(x, y))
                    pixels[offset + x] = ContextCompat.getColor(context, R.color.black);
                else pixels[offset + x] = ContextCompat.getColor(context, R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);

        return bitmap;
    }

    public static Bitmap getBitmapFromBytes(byte[] bytes) {
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);
        }
        return null;
    }
    public static void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
