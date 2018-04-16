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

    private WeatherLab(){
        mWeathers = new ArrayList<>();
    }

    public static WeatherLab getInstance(){
        if(sWeatherLab == null){
            sWeatherLab = new WeatherLab();
        }
        return sWeatherLab;
    }

    public void addWeather(Weather weather){
        mWeathers.add(weather);
    }

    public void updateWeather(Weather updatedWeather){
        UUID id = updatedWeather.getUUID();
        for(Weather weather : mWeathers){
            if(weather.getUUID().equals(id)){
                mWeathers.remove(weather);
                mWeathers.add(updatedWeather);
            }
        }
    }

    public void requestUpdate(Weather weather, OnRefreshStateListener listener){
        mListener = listener;
        WeatherFetcher fetcher = new WeatherFetcher(weather, this);
        fetcher.makeRequest();
    }

    public List<Weather> getWeathers(){
        return mWeathers;
    }

    public Weather getWeather(Weather weather){
        UUID id = weather.getUUID();
        for(Weather w : mWeathers){
            if(w.getUUID().equals(id)){
                return w;
            }
        }
        return null;
    }

    @Override
    public void onSuccess(Weather weather) {
        updateWeather(weather);
        mListener.refresh();
    }

    @Override
    public void onFailure() {

    }
}
