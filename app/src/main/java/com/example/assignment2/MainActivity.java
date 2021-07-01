package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testing 1 2 3
    }

    public void btnGenerate_OnClick(View view){
        Intent i = new Intent(this, GenerateQRCode.class);
        startActivity(i);
    }

    public void btnScanQRCode_OnClick(View view){
        Intent i = new Intent(this, ScanQRCode.class);
        startActivity(i);
    }
}
