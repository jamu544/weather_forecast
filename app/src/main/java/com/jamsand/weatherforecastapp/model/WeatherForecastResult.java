package com.jamsand.weatherforecastapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherForecastResult {

//    public String cod;
//    public double message;
//    public int cont;

   // https://api.openweathermap.org/data/2.5/forecast?q=mumbai&APPID=482cf2ce25f8841f70e5c870e59183a6
    //In MVVM, the data request from Repository => API service => ViewModel => Activity/Fragment.

    //to implement the api below
 //   https://pro.openweathermap.org/data/2.5/forecast/hourly?lat={lat}&lon={lon}&appid={API key}


//    public City city;
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
