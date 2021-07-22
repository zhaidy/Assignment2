package com.example.assignment2;

import android.Manifest;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class GenerateQRCode extends AppCompatActivity {
    public final static int QRcodeWidth = 350 ;
    EditText editText;
    ImageView imageView;
    String EditTextValue ;
    Bitmap bitmap ;
    Button btnSave;
    DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr_code);

        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setVisibility(View.INVISIBLE);

        dm = new DataManager(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Back Arrow
    }

    //To have a Back Arrow Function
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void btnGenerate_OnClick(View view) {
        if(!editText.getText().toString().isEmpty()){
            EditTextValue = editText.getText().toString();
            try {
                bitmap = dm.TextToImageEncode(this, QRcodeWidth, EditTextValue);
                imageView.setImageBitmap(bitmap);
                dm.insertHistory("generate", EditTextValue, bitmap);
                btnSave.setVisibility(View.VISIBLE);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else{
            editText.requestFocus();
            Toast.makeText(this, "Please enter the text to generate" , Toast.LENGTH_LONG).show();
        }
    }

    public void btnSave_OnClick(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]
                    { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        String result = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, EditTextValue, EditTextValue);
        if(result != "" && result != null) {
            Toast.makeText(this, "Image has been saved to gallery successfully.", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Failed to save image.", Toast.LENGTH_LONG).show();
        }
    }


}
