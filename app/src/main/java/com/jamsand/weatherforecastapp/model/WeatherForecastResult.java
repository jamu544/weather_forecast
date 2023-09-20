package com.jamsand.weatherforecastapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherForecastResult {
    @SerializedName("list")
    @Expose
    public ArrayList<MyWeatherForecastList> list;

    public class MyWeatherForecastList {
        public int dt;
        public Main main;
        public ArrayList<Weather> weather;
        public Clouds clouds;
        public Wind wind;
        public int visibility;
        public double pop;
        public Sys sys;
        public String dt_txt;
        public Rain rain;



    }


}
