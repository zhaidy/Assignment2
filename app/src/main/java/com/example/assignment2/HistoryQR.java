package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryQR extends AppCompatActivity {

    private DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_qr);

        ListView list = findViewById(R.id.listHist);

        dm = new DataManager(this);
        ArrayList<String> history = new ArrayList<>();
        Cursor data = dm.getHistory();

        if(data.getCount() == 0){
            Toast.makeText(this, "There are no history", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                String histCol = data.getString(data.getColumnIndex("code"));
                history.add(histCol);

                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
                list.setAdapter(listAdapter);
            }
        }
    }
}