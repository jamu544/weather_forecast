package com.jamsand.weatherforecastapp.viewmodel;

import android.app.Application;

import com.jamsand.weatherforecastapp.model.WeatherForecastResult;
import com.jamsand.weatherforecastapp.model.WeatherResponse;
import com.jamsand.weatherforecastapp.repository.WeatherRepository;
import com.jamsand.weatherforecastapp.utils.Constants;
import com.jamsand.weatherforecastapp.view.activity.SplashScreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WeatherConditionViewModel extends AndroidViewModel {

    private WeatherRepository weatherResponse;
    private LiveData<WeatherForecastResult> weatherListLiveData;
    public WeatherConditionViewModel(@NonNull Application application) {
        super(application);
        weatherResponse =new WeatherRepository();
        this.weatherListLiveData = weatherResponse.getRepositoryForWeatherForcast(SplashScreen.LATITUDE2,SplashScreen.LONGITUDE2, Constants.API_KEY);
    }

    public LiveData<WeatherForecastResult> getWeatherListLiveData(){
        return weatherListLiveData;
    }
}
