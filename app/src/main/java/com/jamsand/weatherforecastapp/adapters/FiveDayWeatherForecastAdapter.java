package com.jamsand.weatherforecastapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jamsand.weatherforecastapp.BR;
import com.jamsand.weatherforecastapp.R;
import com.jamsand.weatherforecastapp.databinding.WeatherforecastItemsBinding;
import com.jamsand.weatherforecastapp.model.WeatherForecastResult;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FiveDayWeatherForecastAdapter extends RecyclerView.Adapter<FiveDayWeatherForecastAdapter.WeatherForecastHolder> {
    public Context context;

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
       // holder.weatherforecastItemsBinding..setText(weatherForecast.list.get(position).dt);
        holder.weatherforecastItemsBinding.txtDescription.setText(weatherForecast.list
                .get(position).weather.get(0).description+" temperature");

        Glide.with(context).load(new StringBuilder("http://openweathermap.org/img/w/")
                .append(weatherForecast.list.get(position).weather.get(0).icon).append(".png"))
                .into(holder.weatherforecastItemsBinding.weatherThumbnail);

        System.out.println("Date :"+ weatherForecast.list.get(position).dt);
        System.out.println("Description  :"+ weatherForecast.list.get(position).weather.get(0).description);
        System.out.println("Image  :"+ weatherForecast.list.get(position).weather.get(0).icon);


        holder.bind(weatherForecast);

    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public class WeatherForecastHolder extends RecyclerView.ViewHolder {

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
