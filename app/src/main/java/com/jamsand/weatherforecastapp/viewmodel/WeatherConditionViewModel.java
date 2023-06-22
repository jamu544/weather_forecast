package com.jamsand.weatherforecastapp.viewmodel;

import android.app.Application;

import com.jamsand.weatherforecastapp.model.WeatherForecastResult;
import com.jamsand.weatherforecastapp.model.WeatherResponse;
import com.jamsand.weatherforecastapp.repository.WeatherRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WeatherConditionViewModel extends AndroidViewModel {

    private WeatherRepository weatherResponse;
    private LiveData<WeatherForecastResult> weatherListLiveData;
    public String latitude;
    public String longitude;
    public String apiKey;


    public WeatherConditionViewModel(@NonNull Application application) {
        super(application);
        weatherResponse =new WeatherRepository();
        this.weatherListLiveData = weatherResponse.getRepositoryForWeatherForcast(latitude,longitude,apiKey);
    }

    public LiveData<WeatherForecastResult> getWeatherListLiveData(){
        return weatherListLiveData;
    }
}
