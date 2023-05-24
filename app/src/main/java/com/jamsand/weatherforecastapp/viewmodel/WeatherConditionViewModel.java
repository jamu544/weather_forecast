package com.jamsand.weatherforecastapp.viewmodel;

import android.app.Application;

import com.jamsand.weatherforecastapp.model.WeatherResponse;
import com.jamsand.weatherforecastapp.repository.WeatherRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WeatherConditionViewModel extends AndroidViewModel {

    private WeatherRepository weatherResponse;
    private LiveData<WeatherResponse> weatherResponseLiveData;
    private String latitude;
    private String longitude;
    private String apiKey;


    public WeatherConditionViewModel(@NonNull Application application) {
        super(application);
        weatherResponse =new WeatherRepository();
        this.weatherResponseLiveData = weatherResponse.getRepositoryOfWeatherConditions(latitude,longitude,apiKey);
    }

    public LiveData<WeatherResponse> getWeatherResponseLiveData(){
        return weatherResponseLiveData;
    }
}
