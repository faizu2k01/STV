package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.Salah.stv.R;
import com.github.barteksc.pdfviewer.PDFView;

public class Pdf2_Activity extends AppCompatActivity {
    PDFView pdfView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pdfView2 = findViewById(R.id.pdfAbubakar);

        pdfView2.fromAsset("abubakarsiddiquera.pdf").load();


    }
}