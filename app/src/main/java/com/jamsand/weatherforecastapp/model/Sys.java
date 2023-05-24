package com.jamsand.weatherforecastapp.model;

import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public long sunrise;
    @SerializedName("sunset")
    public long sunset;
    @SerializedName("city")
    public String city;
}
