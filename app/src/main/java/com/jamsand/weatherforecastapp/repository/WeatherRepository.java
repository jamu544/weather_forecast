package com.jamsand.weatherforecastapp.repository;

import android.util.Log;

import com.jamsand.weatherforecastapp.model.WeatherForecastResult;
import com.jamsand.weatherforecastapp.model.WeatherResponse;
import com.jamsand.weatherforecastapp.network.APIClient;
import com.jamsand.weatherforecastapp.network.APIInterface;
import com.jamsand.weatherforecastapp.utils.Constants;

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

    public LiveData<WeatherForecastResult> getRepositoryForWeatherForcast(String lat,String lon, String appID){

        final MutableLiveData<WeatherForecastResult> data = new MutableLiveData<>();
        apiInterface.getWeatherForecast(lat, lon, appID)
                .enqueue(new Callback<WeatherForecastResult>() {
                    @Override
                    public void onResponse(Call<WeatherForecastResult> call, Response<WeatherForecastResult> response) {
                        Log.d(TAG, "onResponse weather::_____> " + response);
                        if (response.body() != null) {
                            data.setValue(response.body());
                            Log.d(TAG, "weather five day:: " + response.body());
                            Log.d(TAG, "size:: :" + response.body().list.size());
                        }

                    }

                    @Override
                    public void onFailure(Call<WeatherForecastResult> call, Throwable t) {
                                data.setValue(null);
                    }
                });

        return data;
    }


}
