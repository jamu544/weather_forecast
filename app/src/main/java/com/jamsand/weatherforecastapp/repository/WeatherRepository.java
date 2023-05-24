package com.jamsand.weatherforecastapp.repository;

import android.util.Log;

import com.jamsand.weatherforecastapp.model.WeatherResponse;
import com.jamsand.weatherforecastapp.network.APIClient;
import com.jamsand.weatherforecastapp.network.APIInterface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private static final String TAG = WeatherRepository.class.getSimpleName();
    private final APIInterface apiInterface;

    public WeatherRepository (){
        apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
    }
    public LiveData<WeatherResponse> getRepositoryOfWeatherConditions(String lat,String lon,String apiKey){
        final MutableLiveData<WeatherResponse> data = new MutableLiveData<>();
        apiInterface.getCurrentWeatherData( lat,lon,apiKey).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.d(TAG, "onResponse response:: " + response);
                if (response.body() != null) {
                    data.setValue(response.body());

                    Log.d(TAG, "weather datails:: " + response.body());

                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }
        });

        return data;
    }


}
