package com.jamsand.weatherforecastapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

//import com.jamsand.weatherforecastapp.Manifest;
//import com.jamsand.weatherforecastapp.view.activity.SplashScreen;
import com.jamsand.weatherforecastapp.WeatherApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;

public class Utilities {

  private static Context context = WeatherApplication.getInstance();

    AlertDialog.Builder dailog;


    // check internet connection for both MOBILE and WIFI t
    public static boolean checkInternetConnectivity() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public void showNoInternetDailog(){
       // dailog = new AlertDialog.Builder(context.SplashScreen.this);
        dailog.setMessage("No internet connection!");
        dailog.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
       //         context.is;
                dialog.dismiss();
            }
        });
        AlertDialog alert = dailog.create();
        alert.show();
    }
    public static void checkPermissions(){

        //Permissions for getting Locations etc.

//        if (ContextCompat.checkSelfPermission(context,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(context,
//                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(context,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//
    }

    //open settings and enable location permissions
    public  static void openSettingsFromDevice(Context context){

        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public static String convertUnixToDate(long d){
        Date date = new Date(d*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd EEE MMM yyyy");
        String formatted = sdf.format(date);
        return formatted;

    }

}
