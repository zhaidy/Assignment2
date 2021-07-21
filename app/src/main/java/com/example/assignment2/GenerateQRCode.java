package com.example.assignment2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
    DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr_code);

        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);

        dm = new DataManager(this);
    }

    public void btnGenerate_OnClick(View view) {
        if(!editText.getText().toString().isEmpty()){
            EditTextValue = editText.getText().toString();

            try {
                bitmap = dm.TextToImageEncode(this, QRcodeWidth, EditTextValue);

                imageView.setImageBitmap(bitmap);

                dm.insertHistory("generate", EditTextValue, bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else{
            editText.requestFocus();
            Toast.makeText(this, "Please Enter Your Scanned Test" , Toast.LENGTH_LONG).show();
        }
    }
}
