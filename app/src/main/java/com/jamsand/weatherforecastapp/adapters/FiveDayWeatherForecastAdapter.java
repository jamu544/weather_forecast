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
    public int adapterPosistion;

    WeatherForecastResult  weatherForecastResult;

    List<WeatherForecastResult.MyWeatherForecastList> myWeatherForecastListList;

//    public FiveDayWeatherForecastAdapter(WeatherForecastResult weatherList, @NonNull Context context) {
//        this.context = context;
//        this.weatherForecastResult = weatherList;
//    }
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
//        WeatherForecastResult weatherForecast = weatherForecastResult;
//
//
//        try {
//         //   System.out.println("COmpare time and time again===  "+Utilities.checktimings(Utilities.convertUnixToTime(weatherForecast.list.get(position).dt_txt), "15:00"));
//       if (Utilities.checktimings(Utilities.convertUnixToTime(weatherForecast.list.get(position).dt_txt), AFTERNOON_TIME)){
//
//
//
//           holder.weatherforecastItemsBinding.txtDate.setVisibility(View.VISIBLE);
//           holder.weatherforecastItemsBinding.txtDescription.setVisibility(View.VISIBLE);
//           holder.weatherforecastItemsBinding.weatherThumbnail.setVisibility(View.VISIBLE);
//           holder.weatherforecastItemsBinding.txtDate.setText(
//                   Utilities.convertUnixToDate(weatherForecast.list.get(position).dt));
//
//           String description = new StringBuilder().append(weatherForecast.list.get(position).weather.get(0).description)
//                   .append(context.getString(R.string.blank)).append(Math.round((weatherForecast.list.get(position)
//                           .main.temp_max - 275.15))).append("\u00B0").append("/").
//                   append(Math.round((weatherForecast.list.get(position).main.temp_min - 275.15)))
//                   .append("\u00B0").toString();
//
//           holder.weatherforecastItemsBinding.txtDescription.setText(description);
//
//
//           Glide.with(context).load("http://openweathermap.org/img/w/" +
//                           weatherForecast.list.get(position).weather.get(0).icon + ".png")
//                   .into(holder.weatherforecastItemsBinding.weatherThumbnail);
//
//           holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//       }
//       else {
//           holder.weatherforecastItemsBinding.txtDate.setVisibility(View.GONE);
//           holder.weatherforecastItemsBinding.txtDescription.setVisibility(View.GONE);
//           holder.weatherforecastItemsBinding.weatherThumbnail.setVisibility(View.GONE);
//           holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//       }
//            holder.bind(weatherForecast);
//
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }

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

//            if (weatherForecastResult != null) {
//                return weatherForecastResult.list.size();
//            } else {
//                return 0;
//            }
        return myWeatherForecastListList == null? 0:myWeatherForecastListList.size();

    }
    public boolean filterWeatherList(WeatherForecastResult filterlist,int position) {
//        // below line is to add our filtered
//        // list in our course array list.
//        weatherForecastResult = filterlist;
//        try {
//            if (Utilities.checktimings(Utilities.convertUnixToTime(weatherForecastResult.list.get(position).dt_txt), AFTERNOON_TIME)) {
//
//
//
//                notifyDataSetChanged();
//                return true;
//
//            }
//            else {
//
//                    return false;
//
//            }
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
        return true;
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
