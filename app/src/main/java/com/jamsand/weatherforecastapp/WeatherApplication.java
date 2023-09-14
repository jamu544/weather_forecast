package com.jamsand.weatherforecastapp;

import android.app.Application;

public class WeatherApplication  extends Application {

    private static WeatherApplication appContext;

    private WeatherApplication(){}

    public static synchronized WeatherApplication getInstance(){
        if (appContext != null) {
            return appContext;
        }
        appContext = new WeatherApplication();
        return appContext;
    }

}
