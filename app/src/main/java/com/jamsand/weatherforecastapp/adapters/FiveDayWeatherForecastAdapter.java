package com.jamsand.weatherforecastapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jamsand.weatherforecastapp.BR;
import com.jamsand.weatherforecastapp.R;
import com.jamsand.weatherforecastapp.databinding.WeatherforecastItemsBinding;
import com.jamsand.weatherforecastapp.model.WeatherForecastResult;
import com.jamsand.weatherforecastapp.utils.Constants;
import com.jamsand.weatherforecastapp.utils.Utilities;

import java.text.ParseException;
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

        // get last time of each day
        int sizeOfList =weatherForecast.list.size();
        //create another list to compare with original
        ArrayList<String> days = new ArrayList<>();
        for (int i = 0; i < sizeOfList; i++) {
            //   console.log(weatherForecast.list[i].dt_txt);
            try {
                if (Utilities.convertUnixToTime(weatherForecast.list.get(position).dt_txt).equals(Constants.AFTERNOON_TIME)){

                    days.add(Utilities.convertUnixToDate(weatherForecast.list.get(i).dt));

                }

                else {

                    System.out.println(Constants.AFTERNOON_TIME+" === "+Utilities.convertUnixToTime(weatherForecast.list.get(position).dt_txt));
                    days.add(Utilities.convertUnixToDate(weatherForecast.list.get(i).dt));

                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

        for(int i = 0; i < days.size()-1; i++){
            System.out.println("Days of the week "+ days.get(i));
        }


            holder.weatherforecastItemsBinding.txtDate.setText(""+days.get(position).toString());
            holder.weatherforecastItemsBinding.txtDescription.setText(weatherForecast.list
                    .get(position).weather.get(0).description + " temperature");

            Glide.with(context).load("http://openweathermap.org/img/w/" +
                            weatherForecast.list.get(position).weather.get(0).icon + ".png")
                    .into(holder.weatherforecastItemsBinding.weatherThumbnail);

        holder.bind(weatherForecast);

    }

    @Override
    public int getItemCount() {
        if(weatherForecastResult.list != null)
        return weatherForecastResult.list.size();
        else
            return 0;
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
