package com.Salah.stv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

public class No_Internet_Activity extends AppCompatActivity {
    private boolean connectivity,pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            pref = bundle.getBoolean("save");
        }
    }

   public void internetCheckingMethod(View view){
       ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
       if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
               connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
       {
           connectivity = true;
       }else{
           connectivity=false;
       }

       Intent intent1 = new Intent(No_Internet_Activity.this,MainActivity.class);
       Intent intent2 = new Intent(No_Internet_Activity.this,Acceptence_Activity.class);

       if(connectivity == true && pref == true)
           startActivity(intent1);
       else if(connectivity == true && pref == false){
           startActivity(intent2);
       }
    }

}