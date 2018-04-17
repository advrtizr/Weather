package com.codemkr.android.weather;

import android.support.annotation.NonNull;
import android.util.Log;

import com.codemkr.android.weather.interfaces.OnResponseListener;
import com.codemkr.android.weather.interfaces.WeatherRequest;
import com.codemkr.android.weather.json.Weather;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFetcher {

    private static final String TAG = "WeatherFetcher";

    private UUID mUUID;
    private String mLocation;
    private Retrofit mRetrofit;
    private OnResponseListener mResponseListener;

    public WeatherFetcher(Weather weather, OnResponseListener listener) {
        mUUID = weather.getUUID();
        mLocation = weather.getLocation();
        mResponseListener = listener;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void makeRequest() {
        String queryString = Constants.LOCATION_PARAM_START + mLocation + Constants.LOCATION_PARAM_END;
        Log.i(TAG, queryString);
        WeatherRequest request = mRetrofit.create(WeatherRequest.class);
        Call<Weather> weatherCallback = request.getWeather(queryString, Constants.FORMAT_PARAM, Constants.UNIT_PARAM);
        weatherCallback.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                Log.i(TAG, call.request().toString());
                if (response.isSuccessful()) {
                    Weather weather = response.body();
                    if (weather != null) {
                        weather.setUUID(mUUID);
                        weather.setLocation(mLocation);
                        mResponseListener.onSuccess(weather);
                    }
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                mResponseListener.onFailure();
                Log.i(TAG, call.toString());
            }
        });
    }
}
