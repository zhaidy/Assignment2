package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class HistoryQR extends AppCompatActivity {

    private DataManager dm;
    GridView coursesGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_qr);

        coursesGV = findViewById(R.id.idGVcourses);

        ArrayList<HistoryModel> historyModelArrayList = new ArrayList<HistoryModel>();


        dm = new DataManager(this);
        Cursor data = dm.getHistory();

        if(data.getCount() == 0){
            Toast.makeText(this, "There are no history", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
//                String histCol = data.getString(data.getColumnIndex("code"));
//                if(histCol == null){
//                    histCol = "";
//                }
                byte[] imgByte = data.getBlob(3);
                Bitmap img = getBitmapFromBytes(imgByte);
                historyModelArrayList.add(new HistoryModel(
                        data.getString(data.getColumnIndex("_id")),
                        data.getString(data.getColumnIndex("type")),
                        data.getString(data.getColumnIndex("code")),
                        img,
                        data.getString(data.getColumnIndex("date"))
                        ));

                //ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
                //list.setAdapter(listAdapter);
                //tblHist.setAdapter();
            }
        }

        HistoryGVAdapter adapter = new HistoryGVAdapter(this, historyModelArrayList);
        coursesGV.setAdapter(adapter);


        //ListView list = findViewById(R.id.listHist);
        //GridView tblHist = findViewById(R.id.idGVcourses);

//        dm = new DataManager(this);
//        ArrayList<String> history = new ArrayList<>();
//        Cursor data = dm.getHistory();
//
//        if(data.getCount() == 0){
//            Toast.makeText(this, "There are no history", Toast.LENGTH_SHORT).show();
//        }else{
//            while(data.moveToNext()){
//                String histCol = data.getString(data.getColumnIndex("code"));
//                if(histCol == null){
//                    histCol = "";
//                }
//                history.add(histCol);
//
//                //ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
//                //list.setAdapter(listAdapter);
//                //tblHist.setAdapter();
//            }
//        }
    }
    public static Bitmap getBitmapFromBytes(byte[] bytes) {
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);
        }
        return null;
    }
}