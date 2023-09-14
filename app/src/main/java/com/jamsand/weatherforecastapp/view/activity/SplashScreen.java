package com.jamsand.weatherforecastapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import com.jamsand.weatherforecastapp.R;
import com.jamsand.weatherforecastapp.WeatherApplication;
import com.jamsand.weatherforecastapp.databinding.ActivitySplashScreenBinding;
import com.jamsand.weatherforecastapp.utils.Constants;
import android.Manifest;


public class SplashScreen extends AppCompatActivity implements LocationListener,View.OnClickListener {

    private Context context;
  private  LocationManager locationManager;
  private  String locationText = "";
  private  String locationLatitude = "";
  private  String locationLongitude = "";

    private int mInterval = 3000; // 3 seconds by default, can be changed later
    private Handler mHandler;
    private AlertDialog.Builder dailog;
    private AlertDialog.Builder alertDialog2;
    private View view;


    //testing git

    private Button settingsButton;
    public static String[] locationPermissonRequest = new String[]{"android.permission.FINE_LOCATION"};

    public static final int PERMISSION_REQUEST_CODE = 200;
    private ActivitySplashScreenBinding splashScreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_splash_screen);
        splashScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen);
        context = WeatherApplication.getInstance();

        settingsButton = (Button) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(this);


        if(checkInternetConnectivity()) {
            if (ContextCompat.checkSelfPermission(SplashScreen.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)){
                    ActivityCompat.requestPermissions(SplashScreen.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else{
                    ActivityCompat.requestPermissions(SplashScreen.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }



                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        mHandler = new Handler();
                        startRepeatingTask();
                    }
                }, 5000);   //5 seconds


        } else {
            errorMessageforInternetConnectivity();

        }
    }
    // getting location
    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    2000, 5, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    void startRepeatingTask() {
        mStatusChecker.run();
    }
    void stopRepeatingTask() {
        if(mHandler !=null)
            mHandler.removeCallbacks(mStatusChecker);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {


            try {
                getLocation(); //this function can change value of mInterval.

                if (locationText.toString() == "") {
                    Toast.makeText(getApplicationContext(), "Trying to retrieve coordinates...", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent i = new Intent(SplashScreen.this, MainActivity.class);

                    i.putExtra(Constants.LATITUDE, locationLatitude);
                    i.putExtra(Constants.LONGITUDE, locationLongitude);





                    startActivity(i);

                    // close this activity
                    finish();

                    System.out.println("lat == "+locationLatitude); //37.421998333333335
                    System.out.println("lon == "+locationLongitude);//-122.08400000000002

                    Constants.LATITUDE2 = locationLatitude;
                    Constants.LONGITUDE2 = locationLongitude;
                }
            } finally {

                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };
    @Override // test location and internet connections
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(SplashScreen.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    
                }
                return;
            }
        }
    }



    // Called when the location has changed.
    @Override
    public void onLocationChanged(Location location) {

        locationText = location.getLatitude() + "," + location.getLongitude();
        locationLatitude = location.getLatitude() + "";
        locationLongitude = location.getLongitude() + "";

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(SplashScreen.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        alertDialog2 = null;
        stopRepeatingTask();
    }

    // check internet connection for both MOBILE and WIFI
    private boolean checkInternetConnectivity() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //open settings screen from device
    public  void openSettingsFromDevice(){

        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.settings_button:
                openSettingsFromDevice();
                break;
        }
    }

    private void errorMessageforInternetConnectivity(){
        splashScreenBinding.titleTextview.setVisibility(View.VISIBLE);
        splashScreenBinding.bodyTextview.setVisibility(View.VISIBLE);
        splashScreenBinding.settingsButton.setVisibility(View.VISIBLE);
    }
}