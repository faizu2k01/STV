package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.Salah.stv.R;
import com.github.barteksc.pdfviewer.PDFView;

public class Pdf1_Activity extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pdfView = findViewById(R.id.pdfNabi);

        pdfView.fromAsset("pdf1.pdf").load();

    }
}