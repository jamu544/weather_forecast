package com.jamsand.weatherforecastapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.jamsand.weatherforecastapp.R;
import com.jamsand.weatherforecastapp.databinding.ActivityMainBinding;
import com.jamsand.weatherforecastapp.model.WeatherResponse;
import com.jamsand.weatherforecastapp.network.APIInterface;
import com.jamsand.weatherforecastapp.utils.Constants;

public class MainActivity extends AppCompatActivity {


    private Context context;
    String latitude, longitude;

    public static final String  TAG = "MainActivity";


    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        context = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getString(Constants.LATITUDE );
            longitude = extras.getString( Constants.LONGITUDE );
        }
        getWeatherConditionsForCurrentLocation();
    }

    // get response from the server
    public void getWeatherConditionsForCurrentLocation(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIInterface client = retrofit.create(APIInterface.class);
        Call<WeatherResponse> call = client.getCurrentWeatherData(latitude, longitude, Constants.API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String icon = weatherResponse.weather.get(0).icon;

                    activityMainBinding.locationTextView.setText(weatherResponse.name+","+weatherResponse.sys.country);
                    activityMainBinding.weatherConditionTextView.setText(weatherResponse.weather.get(0).description+"");
                    activityMainBinding.temperatureTextView.setText("" + Math.round((weatherResponse.main.temp - 275.15))+"\u00B0");

                    Glide.with(context).load("http://openweathermap.org/img/w/" +icon+".png").into(activityMainBinding.weatherIconImageView);
                }
            }
            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Log.e(TAG,call.toString()+"   ?????");
                Log.e(TAG,t.toString());
            }
        });
    }

}