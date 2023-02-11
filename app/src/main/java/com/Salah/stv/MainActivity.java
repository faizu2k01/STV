package com.Salah.stv;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;

import com.Salah.stv.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.Salah.stv.databinding.ActivityMainBinding;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    boolean connected = false;
    private InterstitialAd mInterstitialAd1,mInterstitialAd2,mInterstitialAd3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        CarouselView carousel = (CarouselView) findViewById(R.id.carousel);
        carousel.setPageCount(5);
        carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                switch (position){
                    case 0:
                        imageView.setImageResource(R.drawable.seeratrasool);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.abubakr);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.umar);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.ali);
                        break;
                    default:
                        imageView.setImageResource(R.drawable.usmaan);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    public void onMethod(View view)
    {
        Intent intent = new Intent(this,Salah_Vibration_Activity.class);
        startActivity(intent);

    }
    public void onMethod2(View view)
    {
        Intent intent = new Intent(this,Use_STV_Activity.class);
        startActivity(intent);



    }
    public void onMethod3(View view)
    {
        Intent intent = new Intent(this,Compass_Activity.class);
        startActivity(intent);

    }
    public void onExitMethod2(MenuItem menuItem){
        Intent intent = new Intent(MainActivity.this,Privacy_policy_Activity.class);
        startActivity(intent);
    }
    public void onExitMethod(MenuItem menuitem)
    {
        System.exit(0);
    }

    public void onContactMethod(MenuItem contact)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","faizhaque35@gmail.com",null));

        startActivity(Intent.createChooser(intent,"Send Feedback"));
    }
}