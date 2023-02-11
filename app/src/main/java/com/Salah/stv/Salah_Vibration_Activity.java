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
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.Salah.stv.R;
import com.Salah.stv.databinding.ActivityMainBinding;
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
    public Intent intent1,intent2,intent3,intent4,intent5,intent6,intent7,intent8,intent9,intent10;
    public SharedPreferences sharedPreferences;
    private boolean connected;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView fajrText,dhurText,asarText,magribText,ishaText,address,city,country;
     private final static int REQUEST_CODE = 100;
     private  boolean responseCame = false;
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

//            resetButton = (Button)findViewById(R.id.resetBut);

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


            getLocation();
    }



        fajar = (Switch) findViewById(R.id.fajarSwitch);
        zuhar = (Switch) findViewById(R.id.zuharSwitch);
        asar = (Switch) findViewById(R.id.asarSwitch);
        magrib = (Switch) findViewById(R.id.magribSwitch);
        isha = (Switch) findViewById(R.id.ishaSwitch);

       


        sharedPreferences = getSharedPreferences("Value", MODE_PRIVATE);
        fajar.setChecked(sharedPreferences.getBoolean("YESORNO1",false));
        zuhar.setChecked(sharedPreferences.getBoolean("YESORNO2",false));
        asar.setChecked(sharedPreferences.getBoolean("YESORNO3",false));
        magrib.setChecked(sharedPreferences.getBoolean("YESORNO4",false));
        isha.setChecked(sharedPreferences.getBoolean("YESORNO5",false));


        fajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(responseCame){
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
        }
        });

        zuhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(responseCame){
                if (zuhar.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("YESORNO2", true);
                    editor.apply();
                    zuhar.setChecked(true);
                    zuharsharePrefrencevalue(true);



                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("YESORNO2", false);
                    editor.apply();
                    zuhar.setChecked(false);
                    zuharsharePrefrencevalue(false);
                }

            }
            }
        });

        asar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(responseCame) {
                    if (asar.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("YESORNO3", true);
                        editor.apply();
                        asar.setChecked(true);
                        asarsharePrefrencevalue(true);


                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("YESORNO3", false);
                        editor.apply();
                        asar.setChecked(false);
                        asarsharePrefrencevalue(false);
                    }
                }

            }
        });

        magrib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(responseCame) {
                    if (magrib.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("YESORNO4", true);
                        editor.apply();
                        magrib.setChecked(true);
                        magribsharePrefrencevalue(true);


                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("YESORNO4", false);
                        editor.apply();
                        magrib.setChecked(false);
                        magribsharePrefrencevalue(false);
                    }
                }

            }
        });

        isha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(responseCame) {
                    if (isha.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("YESORNO5", true);
                        editor.apply();
                        isha.setChecked(true);
                        ishasharePrefrencevalue(true);


                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("YESORNO5", false);
                        editor.apply();
                        isha.setChecked(false);
                        ishasharePrefrencevalue(false);
                    }
                }

            }
        });

//        resetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(responseCame){
//                    if(sharedPreferences.getBoolean("YESORNO1",false)){
//                        fajarsharePrefrencevalue(false);
//                        fajarsharePrefrencevalue(true);
//                    }
//                    if(sharedPreferences.getBoolean("YESORNO2",false)){
//                        zuharsharePrefrencevalue(false);
//                        zuharsharePrefrencevalue(true);
//                    }
//                    if(sharedPreferences.getBoolean("YESORNO3",false)){
//                        asarsharePrefrencevalue(false);
//                        asarsharePrefrencevalue(true);
//                    }
//                    if(sharedPreferences.getBoolean("YESORNO4",false)){
//                        magribsharePrefrencevalue(false);
//                        magribsharePrefrencevalue(true);
//                    }
//                    if(sharedPreferences.getBoolean("YESORNO5",false)){
//                        ishasharePrefrencevalue(false);
//                        ishasharePrefrencevalue(true);
//                    }
//
//                }
//            }
//        });


    }
    //Slot first start for fajar
    private  void fajarsharePrefrencevalue(boolean commer)
    {
       fajarRunningMethod(commer,fajarTimeStart, fajarTimeEnd);


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
                            responseCame = true;
                            JSONObject  js =  response.getJSONObject("data");
                            JSONObject  js2 =  js.getJSONObject("timings");

                            String fajrTime = (String)js2.get("Fajr");
                            if(fajrTime != null) {
                                fajrText.setText(GetFormattedTime(fajrTime));
                                int[] fajar = GetTime(fajrTime);
                                fajarTimeStart = calenderTiming(fajar[0], fajar[1]);
                                fajarTimeEnd = calenderTiming(fajar[0] + 1, fajar[1]);
                            }
                            String dhuhrTime = (String)js2.get("Dhuhr");
                            if(dhuhrTime != null) {
                                dhurText.setText(GetFormattedTime(dhuhrTime));
                                int[] zuhr = GetTime(dhuhrTime);
                                zuharTimeStart = calenderTiming(zuhr[0],zuhr[1]);
                                zuharTimeEnd = calenderTiming(zuhr[0]+2,zuhr[1]);
                            }

                            String asarTime = (String) js2.get("Asr");
                            if(asarTime!=null) {
                                asarText.setText(GetFormattedTime(asarTime));
                                int[] asr = GetTime(asarTime);
                                asarTimeStart = calenderTiming(asr[0],asr[1]);
                                asarTimeEnd = calenderTiming(asr[0]+1,asr[1]+30);
                            }

                            String magribTime = (String)js2.get("Maghrib");
                            if(magribTime!=null) {
                                magribText.setText(GetFormattedTime(magribTime));
                                int[] maghrib = GetTime(magribTime);
                                magribTimeStart = calenderTiming(maghrib[0],maghrib[1]);
                                magribTimeEnd = calenderTiming(maghrib[0]+1,maghrib[1]);
                            }

                            String ishaTime = (String) js2.get("Isha");
                            if(ishaTime!= null) {
                                ishaText.setText(GetFormattedTime(ishaTime));
                                int[] isha = GetTime(ishaTime);
                                ishaTimeStart = calenderTiming(isha[0],isha[1]);
                                ishaTimeEnd = calenderTiming(isha[0]+2,isha[1]);
                            }

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

    private String GetFormattedTime(String time){
        String[] timeArr = time.split(":");

        if(timeArr[0].charAt(0)=='0'){
            timeArr[0] = String.valueOf(timeArr[0].charAt(1));

        }

        if(Integer.parseInt(timeArr[0]) == 12){
            time = time + " PM ";
            return time;
        }
        else if(Integer.parseInt(timeArr[0]) > 12){
            String exactTime1 = "0"+String.valueOf(+Integer.parseInt(timeArr[0]) - 12)+":"+timeArr[1]+" PM";
            return exactTime1;
        }
        else if(Integer.parseInt(timeArr[0]) < 12){
            String exactTime2 = timeArr[0]+":"+timeArr[1]+" AM";
            return exactTime2;
        }

        return new String();

    }


    private int[] GetTime(String time){
        String[] timeArr = time.split(":");
        int[] exactTime = new int[2];

        if(timeArr[0].charAt(0)=='0'){
            timeArr[0] = String.valueOf(timeArr[0].charAt(1));

        }

        if(timeArr[1].charAt(0)=='0'){
            timeArr[1] = String.valueOf(timeArr[1].charAt(1));
        }


            exactTime[0] = Integer.parseInt(timeArr[0]);
            exactTime[1] = Integer.parseInt(timeArr[1]);

            return exactTime;

    }


}