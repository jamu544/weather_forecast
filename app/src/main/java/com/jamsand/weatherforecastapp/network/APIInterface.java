package com.jamsand.weatherforecastapp.network;

import com.jamsand.weatherforecastapp.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherData(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("APPID") String app_id);
}
