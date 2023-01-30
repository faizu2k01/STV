package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.Salah.stv.R;
import com.github.barteksc.pdfviewer.PDFView;

public class Pdf3_Activity extends AppCompatActivity {
    PDFView pdfView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pdfView3 = findViewById(R.id.pdfUmar);

        pdfView3.fromAsset("umarra.pdf").load();
    }
}