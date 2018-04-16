package com.codemkr.android.weather.interfaces;

import com.codemkr.android.weather.json.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WeatherRequest {
    @GET()
    Call<Weather> getWeather(@Url String location);
}
