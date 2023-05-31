package com.jamsand.weatherforecastapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamsand.weatherforecastapp.BR;
import com.jamsand.weatherforecastapp.R;
import com.jamsand.weatherforecastapp.databinding.WeatherforecastItemsBinding;
import com.jamsand.weatherforecastapp.model.AdapterTestAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FiveDayWeatherForecastAdapter extends RecyclerView.Adapter<FiveDayWeatherForecastAdapter.WeatherForecastHolder> {
    public Context context;

    public List<AdapterTestAdapter> weatherList;
    public FiveDayWeatherForecastAdapter(ArrayList<AdapterTestAdapter> weatherList,  @NonNull Context context) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        WeatherforecastItemsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.five_day_weather_forecast,parent, false
        );
        return new WeatherForecastHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastHolder holder, int position) {
        AdapterTestAdapter weatherForecast = weatherList.get(position);
        holder.weatherforecastItemsBinding.dateTextView.setText(weatherForecast.weatherCondition);
        holder.weatherforecastItemsBinding.weatherConditionAdapter.setText(weatherForecast.weatherCondition);
        //.pokemonID = position+1;

//        Picasso.with(context).
//                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
//                        +pokemon.pokemonID+".png")
//                .into(holder.itemBinding.imageViewThumbnail);

        holder.bind(weatherForecast);

    }

    @Override
    public int getItemCount() {
        return weatherList == null? 0:weatherList.size();
    }
    public void filterList(ArrayList<AdapterTestAdapter> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        weatherList = filterlist;
        notifyDataSetChanged();

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
