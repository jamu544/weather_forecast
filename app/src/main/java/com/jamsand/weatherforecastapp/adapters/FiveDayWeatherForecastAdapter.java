package com.jamsand.weatherforecastapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamsand.weatherforecastapp.BR;
import com.jamsand.weatherforecastapp.R;
import com.jamsand.weatherforecastapp.databinding.WeatherforecastItemsBinding;
import com.jamsand.weatherforecastapp.model.WeatherData;
import com.jamsand.weatherforecastapp.model.WeatherForecastResult;
import com.jamsand.weatherforecastapp.utils.Constants;
import com.jamsand.weatherforecastapp.utils.Utilities;

import java.text.ParseException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.jamsand.weatherforecastapp.utils.Constants.AFTERNOON_TIME;

public class FiveDayWeatherForecastAdapter extends RecyclerView.Adapter<FiveDayWeatherForecastAdapter.WeatherForecastHolder> {
    public Context context;
    public int adapterPosistion;

    WeatherForecastResult weatherForecastResult;
    public FiveDayWeatherForecastAdapter(WeatherForecastResult weatherList, @NonNull Context context) {
        this.context = context;
        this.weatherForecastResult = weatherList;
    }

    @NonNull
    @Override
    public WeatherForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        WeatherforecastItemsBinding   binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.weatherforecast_items,parent, false
        );
        return new WeatherForecastHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastHolder holder, int position) {
        WeatherForecastResult weatherForecast = weatherForecastResult;


        try {
         //   System.out.println("COmpare time and time again===  "+Utilities.checktimings(Utilities.convertUnixToTime(weatherForecast.list.get(position).dt_txt), "15:00"));
       if (Utilities.checktimings(Utilities.convertUnixToTime(weatherForecast.list.get(position).dt_txt), AFTERNOON_TIME)){
           holder.weatherforecastItemsBinding.txtDate.setVisibility(View.VISIBLE);
           holder.weatherforecastItemsBinding.txtDescription.setVisibility(View.VISIBLE);
           holder.weatherforecastItemsBinding.weatherThumbnail.setVisibility(View.VISIBLE);
           holder.weatherforecastItemsBinding.txtDate.setText(
                   Utilities.convertUnixToDate(weatherForecast.list.get(position).dt));
           holder.weatherforecastItemsBinding.txtDescription.setText(
                   weatherForecast.list.get(position).weather.get(0).description +"  "+
                           Math.round((weatherForecast.list.get(position).main.temp_max - 275.15)) + "\u00B0"+"/" +
                           Math.round((weatherForecast.list.get(position).main.temp_min - 275.15))+ "\u00B0");


           Glide.with(context).load("http://openweathermap.org/img/w/" +
                           weatherForecast.list.get(position).weather.get(0).icon + ".png")
                   .into(holder.weatherforecastItemsBinding.weatherThumbnail);

           holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
       }
       else {
           holder.weatherforecastItemsBinding.txtDate.setVisibility(View.GONE);
           holder.weatherforecastItemsBinding.txtDescription.setVisibility(View.GONE);
           holder.weatherforecastItemsBinding.weatherThumbnail.setVisibility(View.GONE);
           holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
       }
            holder.bind(weatherForecast);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //if () {

     //   }

    }

    @Override
    public int getItemCount() {

            if (weatherForecastResult != null) {
                return weatherForecastResult.list.size();
            } else {
                return 0;
            }

    }
    public void filterWeatherList(WeatherForecastResult filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        weatherForecastResult = filterlist;
        try {
            if (Utilities.checktimings(Utilities.convertUnixToTime(weatherForecastResult.list.get(adapterPosistion).dt_txt), AFTERNOON_TIME)) {


                notifyDataSetChanged();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static class WeatherForecastHolder extends RecyclerView.ViewHolder {
        public WeatherforecastItemsBinding weatherforecastItemsBinding;

        public WeatherForecastHolder(WeatherforecastItemsBinding weatherforecastItemsBinding) {
            super(weatherforecastItemsBinding.getRoot());
            this.weatherforecastItemsBinding = weatherforecastItemsBinding;
        }
        public void bind(Object object){
            weatherforecastItemsBinding.setVariable(BR.weatherforecast,object);
            weatherforecastItemsBinding.executePendingBindings();
        }
    }
}
