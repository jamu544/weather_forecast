package com.jamsand.weatherforecastapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.jamsand.weatherforecastapp.adapters.FiveDayWeatherForecastAdapter;
import com.jamsand.weatherforecastapp.databinding.ActivityMainBinding;
import com.jamsand.weatherforecastapp.model.WeatherData;
import com.jamsand.weatherforecastapp.model.WeatherForecastResult;
import com.jamsand.weatherforecastapp.model.WeatherResponse;
import com.jamsand.weatherforecastapp.network.APIInterface;
import com.jamsand.weatherforecastapp.utils.Constants;
import com.jamsand.weatherforecastapp.viewmodel.WeatherConditionViewModel;
import com.jamsand.weatherforecastapp.viewmodel.WeatherViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Context context;
    String latitude, longitude;

    public static final String  TAG = "MainActivity";
    String unit = "metric";

   private ActivityMainBinding activityMainBinding;

    //testing 5day weather (try uniits
    private String fiveDay = "https://api.openweathermap.org/data/2.5/forecast?q=mumbai&APPID=482cf2ce25f8841f70e5c870e59183a6";
    private String city = "mumbai";


    private RecyclerView recyclerView;
    private FiveDayWeatherForecastAdapter adapter;
    private WeatherConditionViewModel weatherViewModel;


      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getString(Constants.LATITUDE );
            longitude = extras.getString( Constants.LONGITUDE );
        }
        init();
        getWeatherConditionsForCurrentLocation();
        getWeatherForecastLiveData();

    }

    // get response from the server
    //change later to use singleton
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

    private void init(){
        context = this;
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        weatherViewModel = new ViewModelProvider(this).get(WeatherConditionViewModel.class);
    }

    private void getWeatherForecastLiveData(){
        weatherViewModel.getWeatherListLiveData().observe( this, weatherForecastResult -> {
            if ( weatherForecastResult != null && weatherForecastResult.list != null && !weatherForecastResult.list.isEmpty()){
                WeatherForecastResult forecastResult = weatherForecastResult;
                adapter = new FiveDayWeatherForecastAdapter(forecastResult,context);
                recyclerView = findViewById(R.id.recycler_forecast);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);


            }
        });
    }
}