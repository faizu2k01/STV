package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.Salah.stv.R;

import java.util.ArrayList;

public class Slider_Info_Activity extends AppCompatActivity {
    ViewPager viewPager;
    ArrayList<Integer> images = new ArrayList<>();
    ViewAdapter Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_info);
        viewPager = findViewById(R.id.viewpager1);

        images.add(R.drawable.seeratrasool);
        images.add(R.drawable.abubakr);
        images.add(R.drawable.umar);
        images.add(R.drawable.ali);
        images.add(R.drawable.usmaan);
        Adapter = new ViewAdapter(this,images);
        viewPager.setPadding(100,0,100,0);
        viewPager.setAdapter(Adapter);


    }
}