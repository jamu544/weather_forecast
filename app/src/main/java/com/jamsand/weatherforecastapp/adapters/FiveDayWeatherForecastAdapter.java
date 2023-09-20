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
import com.jamsand.weatherforecastapp.model.MyWeatherForecastList;
import com.jamsand.weatherforecastapp.model.WeatherData;
import com.jamsand.weatherforecastapp.model.WeatherForecastResult;
import com.jamsand.weatherforecastapp.utils.Constants;
import com.jamsand.weatherforecastapp.utils.Utilities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.jamsand.weatherforecastapp.utils.Constants.AFTERNOON_TIME;

public class FiveDayWeatherForecastAdapter extends RecyclerView.Adapter<FiveDayWeatherForecastAdapter.WeatherForecastHolder> {
    public Context context;

    List<WeatherForecastResult.MyWeatherForecastList> myWeatherForecastListList;

    public FiveDayWeatherForecastAdapter(ArrayList<WeatherForecastResult.MyWeatherForecastList> myWeatherForecastListList, @NonNull Context context) {
        this.context = context;
        this.myWeatherForecastListList = myWeatherForecastListList;
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
        WeatherForecastResult.MyWeatherForecastList weather = myWeatherForecastListList.get(position);

        holder.weatherforecastItemsBinding.txtDate.setText(Utilities.convertUnixToDate(weather.dt));

        String description = new StringBuilder().append(weather.weather.get(0).description)
                .append(context.getString(R.string.blank)).append(Math.round((weather.main.temp_max - 275.15))).append("\u00B0").append("/").
                append(Math.round((weather.main.temp_min - 275.15)))
                .append("\u00B0").toString();

        holder.weatherforecastItemsBinding.txtDescription.setText(description);

        Glide.with(context).load("http://openweathermap.org/img/w/" +
                        weather.weather.get(0).icon + ".png")
                .into(holder.weatherforecastItemsBinding.weatherThumbnail);

    }
    @Override
    public int getItemCount() {

        return myWeatherForecastListList == null? 0:myWeatherForecastListList.size();

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
