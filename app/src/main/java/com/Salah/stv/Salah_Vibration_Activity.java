package com.Salah.stv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.Salah.stv.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Salah_Vibration_Activity extends AppCompatActivity {
    private Switch fajar,zuhar,asar,magrib,isha;
    private long fajarTimeStart,fajarTimeEnd,zuharTimeStart,zuharTimeEnd,asarTimeStart,asarTimeEnd,magribTimeStart,magribTimeEnd,ishaTimeStart,ishaTimeEnd,inExactTimeStart,inExactTimeEnd;
    public AlarmManager alarmManager1,alarmManager2,alarmManager3,alarmManager4,alarmManager5,alarmManager6,alarmManager7,alarmManager8,alarmManager9,alarmManager10,alarmManagerInExact1,alarmManagerInExact2;
    public PendingIntent pendingIntent1,pendingIntent2,pendingIntent3,pendingIntent4,pendingIntent5,pendingIntent6,pendingIntent7,pendingIntent8,pendingIntent9,pendingIntent10,pendingIntentInExact1,pendingIntentInExact2;
    public Intent intent1,intent2,intent3,intent4,intent5,intent6,intent7,intent8,intent9,intent10,intentInExact1,intentInExact2;
    public SharedPreferences sharedPreferences;
    private AdView mAdView1,mAdView2;
    private boolean connected;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView fajrText,dhurText,asarText,magribText,ishaText,address,city,country;
    private final static int REQUEST_CODE = 100;
    private String localAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_vibration);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        if (connected)
        {  /*api calling for this part*/
            fajrText = findViewById(R.id.Fajr);
            dhurText = findViewById(R.id.Dhuhr);
            asarText = findViewById(R.id.Asar);
            magribText = findViewById(R.id.Magrib);
            ishaText = findViewById(R.id.Isha);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        mAdView1 = findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        mAdView1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView1.loadAd(adRequest1);

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        mAdView2.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView2.loadAd(adRequest2);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

       getLocation();
    }



        fajar = (Switch) findViewById(R.id.fajarSwitch);
        zuhar = (Switch) findViewById(R.id.zuharSwitch);
        asar = (Switch) findViewById(R.id.asarSwitch);
        magrib = (Switch) findViewById(R.id.magribSwitch);
        isha = (Switch) findViewById(R.id.ishaSwitch);

        inExactTimeStart = calenderTiming(21, 37);
        inExactTimeEnd = calenderTiming(23,0);
        fajarTimeStart = calenderTiming(5, 0);
        fajarTimeEnd = calenderTiming(7,0);
        zuharTimeStart = calenderTiming(12,40);
        zuharTimeEnd = calenderTiming(14,30);
        asarTimeStart = calenderTiming(15,50);
        asarTimeEnd = calenderTiming(17,50);
        magribTimeStart= calenderTiming(18,0);
        magribTimeEnd = calenderTiming(18,30);
        ishaTimeStart = calenderTiming(19,30);
        ishaTimeEnd = calenderTiming(21,0);


        sharedPreferences = getSharedPreferences("Value", MODE_PRIVATE);
        fajar.setChecked(sharedPreferences.getBoolean("YESORNO1",false));
        zuhar.setChecked(sharedPreferences.getBoolean("YESORNO2",false));
        asar.setChecked(sharedPreferences.getBoolean("YESORNO3",false));
        magrib.setChecked(sharedPreferences.getBoolean("YESORNO4",false));
        isha.setChecked(sharedPreferences.getBoolean("YESORNO5",false));


        fajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fajar.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("YESORNO1", true);
                    editor.apply();
                    fajar.setChecked(true);
                    fajarsharePrefrencevalue(true);



                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("YESORNO1", false);
                    editor.apply();
                    fajar.setChecked(false);
                    fajarsharePrefrencevalue(false);
                }

            }
        });

//        zuhar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (zuhar.isChecked()) {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("YESORNO2", true);
//                    editor.apply();
//                    zuhar.setChecked(true);
//                    zuharsharePrefrencevalue(true);
//
//
//
//                } else {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("YESORNO2", false);
//                    editor.apply();
//                    zuhar.setChecked(false);
//                    zuharsharePrefrencevalue(false);
//                }
//
//            }
//        });
//
//        asar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (asar.isChecked()) {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("YESORNO3", true);
//                    editor.apply();
//                    asar.setChecked(true);
//                    asarsharePrefrencevalue(true);
//
//
//
//                } else {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("YESORNO3", false);
//                    editor.apply();
//                    asar.setChecked(false);
//                    asarsharePrefrencevalue(false);
//                }
//
//            }
//        });
//
//        magrib.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (magrib.isChecked()) {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("YESORNO4", true);
//                    editor.apply();
//                    magrib.setChecked(true);
//                    magribsharePrefrencevalue(true);
//
//
//
//                } else {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("YESORNO4", false);
//                    editor.apply();
//                    magrib.setChecked(false);
//                    magribsharePrefrencevalue(false);
//                }
//
//            }
//        });
//
//        isha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isha.isChecked()) {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("YESORNO5", true);
//                    editor.apply();
//                    isha.setChecked(true);
//                    ishasharePrefrencevalue(true);
//
//
//
//                } else {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("YESORNO5", false);
//                    editor.apply();
//                    isha.setChecked(false);
//                    ishasharePrefrencevalue(false);
//                }
//
//            }
//        });


    }
    //Slot first start for fajar
    private  void fajarsharePrefrencevalue(boolean commer)
    {
//        fajarRunningMethod(commer,fajarTimeStart, fajarTimeEnd);
        inExactRunningMethod(commer,inExactTimeStart,inExactTimeEnd);

    }
    protected void inExactRunningMethod(boolean checker, long time1,long time2) {
        if (checker) {

            inExactsetAlarmVibrateCreate(time1);

        } else {

            inExactsetAlarmVibrateDestroy();
        }
    }

    protected void fajarRunningMethod(boolean checker, long time1,long time2) {
        if (checker) {
            fajarsetAlarmVibrate(time1);
            fajarsetAlarmNormal(time2);

        } else {
            fajarcancelAlarmVibrate();
            fajarcancelAlarmNormal();
        }
    }
    private void fajarsetAlarmNormal(long time2) {
        alarmManager2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent2 = new Intent(this, MySchedulerNormal.class);
        pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager2.setWindow(AlarmManager.RTC_WAKEUP, time2, AlarmManager.INTERVAL_DAY, pendingIntent2);

    }
    private void fajarcancelAlarmNormal()
    {
        alarmManager2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent2 = new Intent(this, MySchedulerNormal.class);
        pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager2.cancel(pendingIntent2);
        pendingIntent2.cancel();
    }



    private void fajarsetAlarmVibrate(long time1) {
        alarmManager1 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent1 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent1= PendingIntent.getBroadcast(this, 1, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager1.setWindow(AlarmManager.RTC_WAKEUP, time1, AlarmManager.INTERVAL_DAY, pendingIntent1);
        Toast.makeText(this,"fajar Automation start",Toast.LENGTH_SHORT).show();
    }


    private void fajarcancelAlarmVibrate()
    {
        alarmManager1 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent1 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent1 = PendingIntent.getBroadcast(this, 1, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager1.cancel(pendingIntent1);
        pendingIntent1.cancel();
        Toast.makeText(this,"fajar Automation cancel",Toast.LENGTH_SHORT).show();
    }
    //Slot One finish for fajar




    //Slot two start for zuhar or juma
    private  void zuharsharePrefrencevalue(boolean commer)
    {
        zuharRunningMethod(commer,zuharTimeStart, zuharTimeEnd);
    }

    protected void zuharRunningMethod(boolean checker, long time1,long time2) {
        if (checker) {
            zuharsetAlarmVibrate(time1);
            zuharsetAlarmNormal(time2);
        } else {
            zuharcancelAlarmVibrate();
            zuharcancelAlarmNormal();
        }
    }
    private void zuharsetAlarmNormal(long time2) {
        alarmManager4 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent4 = new Intent(this, MySchedulerNormal.class);
        pendingIntent4 = PendingIntent.getBroadcast(this, 2, intent4, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager4.setWindow(AlarmManager.RTC_WAKEUP, time2, AlarmManager.INTERVAL_DAY, pendingIntent4);

    }
    private void zuharcancelAlarmNormal()
    {
        alarmManager4 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent4 = new Intent(this, MySchedulerNormal.class);
        pendingIntent4 = PendingIntent.getBroadcast(this, 2, intent4, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager4.cancel(pendingIntent4);
        pendingIntent4.cancel();
    }



    private void zuharsetAlarmVibrate(long time1) {
        alarmManager3 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent3 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent3 = PendingIntent.getBroadcast(this, 3, intent3, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager3.setWindow(AlarmManager.RTC_WAKEUP, time1, AlarmManager.INTERVAL_DAY, pendingIntent3);
        Toast.makeText(this,"Zuhar Automation start",Toast.LENGTH_SHORT).show();
    }


    private void zuharcancelAlarmVibrate()
    {
        alarmManager3 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent3 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent3 = PendingIntent.getBroadcast(this, 3, intent3, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager3.cancel(pendingIntent3);
        pendingIntent3.cancel();
        Toast.makeText(this,"Zuhar Automation cancel",Toast.LENGTH_SHORT).show();
    }
  //Slot two ends here


    //Slot third for asar
    private  void asarsharePrefrencevalue(boolean commer)
    {
        asarRunningMethod(commer,asarTimeStart, asarTimeEnd);
    }

    protected void asarRunningMethod(boolean checker, long time1,long time2) {
        if (checker) {
            asarsetAlarmVibrate(time1);
            asarsetAlarmNormal(time2);
        } else {
            asarcancelAlarmVibrate();
            asarcancelAlarmNormal();
        }
    }
    private void asarsetAlarmNormal(long time2) {
        alarmManager6 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent6 = new Intent(this, MySchedulerNormal.class);
        pendingIntent6 = PendingIntent.getBroadcast(this, 4, intent6, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager6.setWindow(AlarmManager.RTC_WAKEUP, time2, AlarmManager.INTERVAL_DAY, pendingIntent6);

    }
    private void asarcancelAlarmNormal()
    {
        alarmManager6 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent6 = new Intent(this, MySchedulerNormal.class);
        pendingIntent6 = PendingIntent.getBroadcast(this, 4, intent6, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager6.cancel(pendingIntent6);
        pendingIntent6.cancel();
    }



    private void asarsetAlarmVibrate(long time1) {
        alarmManager5 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent5 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent5 = PendingIntent.getBroadcast(this, 5, intent5, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager5.setWindow(AlarmManager.RTC_WAKEUP, time1, AlarmManager.INTERVAL_DAY, pendingIntent5);
        Toast.makeText(this,"Asar Automation start",Toast.LENGTH_SHORT).show();
    }


    private void asarcancelAlarmVibrate()
    {
        alarmManager5 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent5 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent5 = PendingIntent.getBroadcast(this, 5, intent5, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager5.cancel(pendingIntent5);
        pendingIntent5.cancel();
        Toast.makeText(this,"Asar Automation cancel",Toast.LENGTH_SHORT).show();
    }

    //Slot third ends here




    //Slot four start for magrib
    private  void magribsharePrefrencevalue(boolean commer)
    {
        magribRunningMethod(commer,magribTimeStart, magribTimeEnd);
    }

    protected void magribRunningMethod(boolean checker, long time1,long time2) {
        if (checker) {
            magribsetAlarmVibrate(time1);
            magribsetAlarmNormal(time2);
        } else {
            magribcancelAlarmVibrate();
            magribcancelAlarmNormal();
        }
    }
    private void magribsetAlarmNormal(long time2) {
        alarmManager8 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent8 = new Intent(this, MySchedulerNormal.class);
        pendingIntent8 = PendingIntent.getBroadcast(this, 6, intent8, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager8.setWindow(AlarmManager.RTC_WAKEUP, time2, AlarmManager.INTERVAL_DAY, pendingIntent8);

    }
    private void magribcancelAlarmNormal()
    {
        alarmManager8 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent8 = new Intent(this, MySchedulerNormal.class);
        pendingIntent8 = PendingIntent.getBroadcast(this, 6, intent8, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager8.cancel(pendingIntent8);
        pendingIntent8.cancel();
    }



    private void magribsetAlarmVibrate(long time1) {
        alarmManager7 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent7 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent7= PendingIntent.getBroadcast(this, 7, intent7, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager7.setWindow(AlarmManager.RTC_WAKEUP, time1, AlarmManager.INTERVAL_DAY, pendingIntent7);
        Toast.makeText(this,"Magrib Automation start",Toast.LENGTH_SHORT).show();
    }


    private void magribcancelAlarmVibrate()
    {
        alarmManager7 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent7 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent7 = PendingIntent.getBroadcast(this, 7, intent7, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager7.cancel(pendingIntent7);
        pendingIntent7.cancel();
        Toast.makeText(this,"Magrib Automation cancel",Toast.LENGTH_SHORT).show();
    }
    //Slot four end for magrib


    //Slot five for Isha
    private  void ishasharePrefrencevalue(boolean commer)
    {
        ishaRunningMethod(commer,ishaTimeStart, ishaTimeEnd);
    }

    protected void ishaRunningMethod(boolean checker, long time1,long time2) {
        if (checker) {
            ishasetAlarmVibrate(time1);
            ishasetAlarmNormal(time2);
        } else {
            ishacancelAlarmVibrate();
            ishacancelAlarmNormal();
        }
    }
    private void ishasetAlarmNormal(long time2) {
        alarmManager10 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent10 = new Intent(this, MySchedulerNormal.class);
        pendingIntent10 = PendingIntent.getBroadcast(this, 8, intent10, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager10.setWindow(AlarmManager.RTC_WAKEUP, time2, AlarmManager.INTERVAL_DAY, pendingIntent10);

    }
    private void ishacancelAlarmNormal()
    {
        alarmManager10 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent10 = new Intent(this, MySchedulerNormal.class);
        pendingIntent10 = PendingIntent.getBroadcast(this, 8, intent10, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager10.cancel(pendingIntent10);
        pendingIntent10.cancel();
    }



    private void ishasetAlarmVibrate(long time1) {
        alarmManager9 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent9 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent9= PendingIntent.getBroadcast(this, 9, intent9, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager9.setWindow(AlarmManager.RTC_WAKEUP, time1, AlarmManager.INTERVAL_DAY, pendingIntent9);
        Toast.makeText(this,"Isha Automation start",Toast.LENGTH_SHORT).show();
    }


    private void ishacancelAlarmVibrate()
    {
        alarmManager9 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intent9 = new Intent(this, MySchedulerVibrate.class);
        pendingIntent9 = PendingIntent.getBroadcast(this, 9, intent9, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager9.cancel(pendingIntent9);
        pendingIntent9.cancel();
        Toast.makeText(this,"Isha Automation cancel",Toast.LENGTH_SHORT).show();
    }


    //Slot five finsh for isha

    //Slot Six for inExactAlarm
//    private void inExactsetAlarmNormalCreate(long time2) {
//        alarmManagerInExact1 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        intentInExact1 = new Intent(this, DailyAlarmScheduling.class);
//        pendingIntentInExact1 = PendingIntent.getBroadcast(this, 0, intentInExact1, PendingIntent.FLAG_CANCEL_CURRENT);
//        alarmManagerInExact1.setInexactRepeating(AlarmManager.RTC_WAKEUP, time2, AlarmManager.INTERVAL_DAY, pendingIntentInExact1);
//
//    }
//    private void inExactsetAlarmNormalDestroy()
//    {
//        alarmManagerInExact1 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        intentInExact1 = new Intent(this, DailyAlarmCancelScheduling.class);
//        pendingIntentInExact1 = PendingIntent.getBroadcast(this, 0, intentInExact1, PendingIntent.FLAG_CANCEL_CURRENT);
//        alarmManagerInExact1.cancel(pendingIntentInExact1);
//        pendingIntentInExact1.cancel();
//    }
//


    private void inExactsetAlarmVibrateCreate(long time1) {
        alarmManagerInExact2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intentInExact2 = new Intent(this, DailyAlarmScheduling.class);
        pendingIntentInExact2= PendingIntent.getBroadcast(this, 1, intentInExact2, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManagerInExact2.setInexactRepeating(AlarmManager.RTC_WAKEUP, time1, AlarmManager.INTERVAL_DAY, pendingIntent1);
        Toast.makeText(this,"fajar Automation start",Toast.LENGTH_SHORT).show();
    }


    private void inExactsetAlarmVibrateDestroy()
    {
        alarmManagerInExact2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        intentInExact2 = new Intent(this, DailyAlarmCancelScheduling.class);
        pendingIntentInExact2 = PendingIntent.getBroadcast(this, 1, intentInExact2, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManagerInExact2.cancel(pendingIntentInExact2);
        pendingIntentInExact2.cancel();
        Toast.makeText(this,"fajar Automation cancel",Toast.LENGTH_SHORT).show();
    }
    //Slot Six for inExactAlarm finish
    protected long calenderTiming(int hourOfDat, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDat);
        calendar.set(Calendar.MINUTE, n);
        calendar.set(Calendar.SECOND, 0);

        long time = calendar.getTimeInMillis();
        return time;
    }


    public void ClickMethod( String _localAdd)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.aladhan.com/v1/timingsByAddress?address="+_localAdd,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject  js =  response.getJSONObject("data");
                            JSONObject  js2 =  js.getJSONObject("timings");
                            fajrText.setText((CharSequence) js2.get("Fajr"));
                            dhurText.setText((CharSequence) js2.get("Dhuhr"));
                            asarText.setText((CharSequence) js2.get("Asr"));
                            magribText.setText((CharSequence) js2.get("Maghrib"));
                            ishaText.setText((CharSequence) js2.get("Isha"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fajrText.setText("not found");
                dhurText.setText("not found");
                asarText.setText("not found");
                magribText.setText("not found");
                ishaText.setText("not found");
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }


    private void getLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){
                                try {
                                    Geocoder geocoder = new Geocoder(Salah_Vibration_Activity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    localAdd =   (String) addresses.get(0).getAddressLine(0)+addresses.get(0).getLocality()+addresses.get(0).getCountryName();
                                    ClickMethod(localAdd);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    });


        }else {

            askPermission();


        }


    }

    private void askPermission() {

        ActivityCompat.requestPermissions(Salah_Vibration_Activity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                getLocation();

            }else {


                Toast.makeText(Salah_Vibration_Activity.this,"Please provide the  Internet permission", Toast.LENGTH_SHORT).show();

            }



        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void createAlarm(){

        //fajarsharePrefrencevalue(true);
        zuharsharePrefrencevalue(true);
        asarsharePrefrencevalue(true);
        magribsharePrefrencevalue(true);
        ishasharePrefrencevalue(true);


    }

    public void cancelAlarm(){
        //fajarsharePrefrencevalue(false);
        zuharsharePrefrencevalue(false);
        asarsharePrefrencevalue(false);
        magribsharePrefrencevalue(false);
        ishasharePrefrencevalue(false);
    }
}