package com.example.assignment2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.assignment2.DataManager.setClipboard;

public class ScanQRCode extends AppCompatActivity {
    public final static int QRcodeWidth = 350 ;
    TextView tv_qr_readTxt;
    Button btnScan;
    Bitmap bitmap;
    ImageView imgScan;

    // This is our DataManager instance
    private DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr_code);

        dm = new DataManager(this);

        btnScan = (Button)findViewById(R.id.btnScan);
        imgScan = (ImageView)findViewById(R.id.imgScan);
        tv_qr_readTxt = (TextView) findViewById(R.id.txtScanResult);

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

    public void btnScan_OnClick(View view) {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned");

                tv_qr_readTxt.setText(result.getContents());

                try {
                    bitmap = dm.TextToImageEncode(this, QRcodeWidth,tv_qr_readTxt.getText().toString());
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                imgScan.setImageBitmap(bitmap);
                dm.insertHistory("scan", tv_qr_readTxt.getText().toString(), bitmap);
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void clickResult(View view){
        String code = tv_qr_readTxt.getText().toString();
        if (!code.startsWith("http://") && !code.startsWith("https://")) {
            setClipboard(this, code);
            Toast.makeText(this, code + " has been copied to clipboard", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(code));
            startActivity(browserIntent);
        }
    }
}
