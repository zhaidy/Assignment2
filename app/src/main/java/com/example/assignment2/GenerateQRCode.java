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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr_code);

        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void btnGenerate_OnClick(View view) {
        if(!editText.getText().toString().isEmpty()){
            EditTextValue = editText.getText().toString();

            try {
                bitmap = TextToImageEncode(EditTextValue);

                imageView.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else{
            editText.requestFocus();
            Toast.makeText(this, "Please Enter Your Scanned Test" , Toast.LENGTH_LONG).show();
        }
    }
    Bitmap TextToImageEncode(String Value) throws WriterException {
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
                    pixels[offset + x] = ContextCompat.getColor(this, R.color.black);
                else pixels[offset + x] = ContextCompat.getColor(this, R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
