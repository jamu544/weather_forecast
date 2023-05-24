package com.jamsand.weatherforecastapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamsand.weatherforecastapp.R;
import com.jamsand.weatherforecastapp.WeatherApplication;
import com.jamsand.weatherforecastapp.model.WeatherResponse;
import com.jamsand.weatherforecastapp.network.WeatherService;
import com.jamsand.weatherforecastapp.utils.Constants;

public class MainActivity extends AppCompatActivity {


    private Context context;
    String latitude, longitude;
    //api.openweathermap.org/data/2.5/find?q=London&units=metric

    public TextView locationTextView;
    public TextView weatherDescription;
    public TextView degressTextView;
    public ImageView weatherIconImageView;
    public static final String  TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.weather_app);


        locationTextView = (TextView) findViewById(R.id.locationTextView);
        weatherDescription = (TextView) findViewById(R.id.weatherConditionTextView);
        degressTextView = (TextView) findViewById(R.id.temperatureTextView);
        weatherIconImageView = (ImageView) findViewById(R.id.weather_icon_ImageView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getString("latitude" );
            longitude = extras.getString( "longitude" );
        }


        getWeatherConditionsForCurrentLocation();
  //      getWeatherConditionsForCurrentLocation2();

    }

    // get response from the server
    public void getWeatherConditionsForCurrentLocation(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(latitude, longitude, Constants.API_KEY);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String icon = weatherResponse.weather.get(0).icon;

                    locationTextView.setText(weatherResponse.name+","+weatherResponse.sys.country);
                    weatherDescription.setText(weatherResponse.weather.get(0).description+"");
                    degressTextView.setText("" + Math.round((weatherResponse.main.temp - 275.15))+"\u00B0");

                    Glide.with(context).load("http://openweathermap.org/img/w/" +icon+".png").into(weatherIconImageView);
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