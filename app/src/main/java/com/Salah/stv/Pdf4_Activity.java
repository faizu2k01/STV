package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.Salah.stv.R;
import com.github.barteksc.pdfviewer.PDFView;

public class Pdf4_Activity extends AppCompatActivity {
    PDFView pdfView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf4);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pdfView4 = findViewById(R.id.pdfAli);

        pdfView4.fromAsset("alira.pdf").load();

    }
}