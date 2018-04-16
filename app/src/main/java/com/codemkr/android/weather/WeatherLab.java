package com.codemkr.android.weather;

import com.codemkr.android.weather.interfaces.OnRefreshStateListener;
import com.codemkr.android.weather.interfaces.OnResponseListener;
import com.codemkr.android.weather.json.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WeatherLab implements OnResponseListener {
    private static WeatherLab sWeatherLab;
    private List<Weather> mWeathers;
    private OnRefreshStateListener mListener;

    private WeatherLab() {
        mWeathers = new ArrayList<>();
    }

    public static WeatherLab getInstance() {
        if (sWeatherLab == null) {
            sWeatherLab = new WeatherLab();
        }
        return sWeatherLab;
    }

    public void addWeather(Weather weather) {
        mWeathers.add(weather);
    }

    public void updateWeather(Weather updatedWeather) {
        UUID id = updatedWeather.getUUID();
        for (int i = 0; i < mWeathers.size(); i++) {
            if (mWeathers.get(i).getUUID().equals(id)) {
                mWeathers.set(i, updatedWeather);
            }
        }
    }

    public void requestUpdate(UUID uuid, OnRefreshStateListener listener) {
        mListener = listener;
        Weather weather = getWeather(uuid);
        WeatherFetcher fetcher = new WeatherFetcher(weather, this);
        fetcher.makeRequest();
    }

    public List<Weather> getWeathers() {
        return mWeathers;
    }

    public Weather getWeather(UUID id) {
        for (Weather weather : mWeathers) {
            if (weather.getUUID().equals(id)) {
                return weather;
            }
        }
        return null;
    }

    @Override
    public void onSuccess(Weather weather) {
        updateWeather(weather);
        mListener.updateUI();
    }

    @Override
    public void onFailure() {

    }
}
