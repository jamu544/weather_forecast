package com.jamsand.weatherforecastapp.viewmodel;

import android.app.Application;

import com.jamsand.weatherforecastapp.model.WeatherForecastResult;
import com.jamsand.weatherforecastapp.repository.WeatherRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WeatherViewModel extends AndroidViewModel {
//    private WeatherRepository weatherResponse;
//    private LiveData<WeatherForecastResult> weatherForecastResultLiveData;
//
//    private String lat,lon,appID;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
//        weatherResponse = new WeatherRepository();
//        this.weatherForecastResultLiveData = weatherResponse.getRepositoryForWeatherForcast(lat,lon,appID);
    }

//    public LiveData<WeatherForecastResult> getWeatherForecastResultLiveData() {
//        return weatherForecastResultLiveData;
//    }
}
