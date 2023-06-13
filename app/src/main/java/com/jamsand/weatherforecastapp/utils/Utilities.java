package com.jamsand.weatherforecastapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

//import com.jamsand.weatherforecastapp.Manifest;
//import com.jamsand.weatherforecastapp.view.activity.SplashScreen;
import com.jamsand.weatherforecastapp.WeatherApplication;

import java.text.ParseException;
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

    // convert date from server and convert to day of the week
    public static String convertUnixToDate(long d){
        Date date = new Date(d*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat(" EEEE");
        String formatted = sdf.format(date);
        return formatted;

    }
    public static String convertUnixToTime(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("HH:mm").format(date);
    }

    // try this time {
    public static boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date1.after(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    public static int compareTime(String inputDate, String endtime) {



        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);



        try {
       //     Date formatDateFromServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);

            Date date1 = sdf.parse(inputDate);
            Date date2 = sdf.parse(endtime);

            // Outputs -1 as date1 is before date2
            if(date2.compareTo(date1) == 0){
                System.out.println("Good time");
                return 0;
            }
           } catch (ParseException e){
            e.printStackTrace();
        }
        return -1;
    }



}
