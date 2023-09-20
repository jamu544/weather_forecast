package com.jamsand.weatherforecastapp;

import android.app.Application;

public class WeatherApplication  extends Application {

    public static WeatherApplication appContext;

    public WeatherApplication(){}

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static  WeatherApplication getInstance(){

            return appContext;
    }

}
