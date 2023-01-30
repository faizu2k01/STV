package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.Salah.stv.R;
import com.github.barteksc.pdfviewer.PDFView;

public class Pdf5_Activity extends AppCompatActivity {
    PDFView pdfView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf5);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pdfView5 = findViewById(R.id.pdfUsman);

        pdfView5.fromAsset("usmanganira.pdf").load();
    }
}