package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

import com.Salah.stv.R;

public class Splash_Screen_Activity extends AppCompatActivity {
    private boolean connectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);





        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {




                    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                    {
                        connectivity = true;
                    }else{
                        connectivity=false;
                    }


                    Intent intent1 = new Intent(Splash_Screen_Activity.this, Acceptence_Activity.class);
                    Intent intent2 = new Intent(Splash_Screen_Activity.this, MainActivity.class);
                    Intent intent3 = new Intent(Splash_Screen_Activity.this, No_Internet_Activity.class);

                    SharedPreferences pre = getSharedPreferences("save", MODE_PRIVATE);
                    boolean value56 = pre.getBoolean("TF", false);

                    if (value56 == false && connectivity == false)
                        startActivity(intent3);
                    else if(value56 == true && connectivity == false)
                        startActivity(intent3);
                    else if(connectivity == true && value56==false ) {
                        intent1.putExtra("save", value56);
                        startActivity(intent1);
                    }
                    else if(connectivity == true && value56==true)
                        startActivity(intent2);

                }
            }
        };
        thread.start();

    }

    protected void onPause() {
        super.onPause();
        finish();

    }


}