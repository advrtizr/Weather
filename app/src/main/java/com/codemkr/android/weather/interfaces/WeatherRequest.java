package com.codemkr.android.weather.interfaces;

import com.codemkr.android.weather.json.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherRequest {
    @GET("v1/public/yql")
    Call<Weather> getWeather(@Query("q") String location,
                             @Query("format") String format,
                             @Query("u") String unit);
}
