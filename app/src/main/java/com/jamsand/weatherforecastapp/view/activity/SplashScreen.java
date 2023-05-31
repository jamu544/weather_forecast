package com.jamsand.weatherforecastapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.jamsand.weatherforecastapp.utils.Constants;
import com.jamsand.weatherforecastapp.utils.Utilities;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = WeatherApplication.getInstance();

        settingsButton = (Button) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(this);


        if(checkInternetConnectivity()) {
//
//            //Alert Dialog
//             alertDialog2 = new AlertDialog.Builder(
//                    SplashScreen.this);
//
//            // Setting Dialog Title
//            alertDialog2.setTitle("Notification");
//
//            // Setting Dialog Message
//            String string1 = "Give it seconds for your coordinates to update";
//
//            alertDialog2.setMessage(string1);
//
//            // Setting Icon to Dialog
//            alertDialog2.setIcon(R.drawable.weather_app);
//
//            // Setting Positive "Yes" Btn
//            alertDialog2.setPositiveButton("Continue",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//
//            // Showing Alert Dialog
//            alertDialog2.show();




            //Permissions for getting Locations etc.

//            if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(getApplicationContext(),
//                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
//                                android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//
//            }
            if (checkPermission()){
                requestPermission();
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        mHandler = new Handler();
                        startRepeatingTask();
                    }
                }, 5000);   //5 seconds

            }
        } else {
            showNoInternetDailog();
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

                    System.out.println("lat == "+locationLatitude);
                    System.out.println("lon == "+locationLongitude);
                }
            } finally {

                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };




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
    private void showNoInternetDailog(){
        dailog = new AlertDialog.Builder(SplashScreen.this);
        dailog.setMessage("No internet connection!");
        dailog.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog alert = dailog.create();
        alert.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (locationAccepted)
                        Toast.makeText(getApplicationContext(),  "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();
                    else {

                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data.", Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }
    //check dangerous(location) permission at runtime
  public boolean checkPermission(){
      int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

      return result == PackageManager.PERMISSION_GRANTED;
  }
    //request dangerous(location) permission at runtime
  public void requestPermission(){
      ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
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
}