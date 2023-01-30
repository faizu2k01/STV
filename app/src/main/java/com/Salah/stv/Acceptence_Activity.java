package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.Salah.stv.R;

public class Acceptence_Activity extends AppCompatActivity {
    boolean switchValue, value;
    Switch switch1;
    Button button, button1;
    private final static int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent i = new Intent(Acceptence_Activity.this, Splash_Screen_Activity.class);
        setContentView(R.layout.activity_acceptence);

        NotificationManager num = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !num.isNotificationPolicyAccessGranted()) {

            startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
        }
        askPermission();

        switch1 = (Switch) findViewById(R.id.switch1);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button2);
        SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch1.isChecked()) {
                    editor.putBoolean("TF", true);
                    editor.apply();
                    switch1.setChecked(true);
                } else {
                    editor.putBoolean("TF", false);
                    editor.apply();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(Acceptence_Activity.this, "Please allow the switch and Accept it.", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });
    }

    private void askPermission() {

        ActivityCompat.requestPermissions(Acceptence_Activity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }



}