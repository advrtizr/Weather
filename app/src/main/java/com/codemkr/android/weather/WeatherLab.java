package com.codemkr.android.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.codemkr.android.weather.interfaces.OnRefreshStateListener;
import com.codemkr.android.weather.interfaces.OnResponseListener;
import com.codemkr.android.weather.json.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.codemkr.android.weather.WeatherDBSchema.*;

public class WeatherLab implements OnResponseListener {
    private static WeatherLab sWeatherLab;
    private SQLiteDatabase mWeatherDatabase;
    private List<Weather> mWeathers;
    private OnRefreshStateListener mListener;

    private WeatherLab(Context context) {
        mWeathers = new ArrayList<>();
        mWeatherDatabase = new WeatherDBHelper(context).getWritableDatabase();
    }

    public static WeatherLab getInstance(Context context) {
        if (sWeatherLab == null) {
            sWeatherLab = new WeatherLab(context);
        }
        return sWeatherLab;
    }

    public void addWeather(Weather weather) {
        mWeathers.add(weather);
        ContentValues values = getValues(weather);
        mWeatherDatabase.insert(WeatherTable.TABLE_NAME, null, values);
    }

    private void updateWeather(Weather updatedWeather) {
        UUID id = updatedWeather.getUUID();
        for (int i = 0; i < mWeathers.size(); i++) {
            if (mWeathers.get(i).getUUID().equals(id)) {
                mWeathers.set(i, updatedWeather);
            }
        }
        String uuid = updatedWeather.getUUID().toString();
        ContentValues values = getValues(updatedWeather);
        mWeatherDatabase.update(WeatherTable.TABLE_NAME, values, Cols.UUID + " = ?", new String[]{uuid});
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

    private ContentValues getValues(Weather weather){
        ContentValues values = new ContentValues();
        values.put(Cols.UUID, weather.getUUID().toString());
        values.put(Cols.LOCATION_REQUEST, weather.getLocation());
        values.put(Cols.LOCATION_CITY, weather.getQuery().getResults().getChannel().getLocation().getCity());
        values.put(Cols.LOCATION_COUNTRY, weather.getQuery().getResults().getChannel().getLocation().getCountry());
        values.put(Cols.TEMPERATURE_FORECAST, weather.getQuery().getResults().getChannel().getItem().getCondition().getTemp());
        values.put(Cols.TEMPERATURE_ACTUAL, weather.getQuery().getResults().getChannel().getWind().getChill());
        values.put(Cols.CONDITIONS, weather.getQuery().getResults().getChannel().getItem().getCondition().getText());
        values.put(Cols.CODE, weather.getQuery().getResults().getChannel().getItem().getCondition().getCode());
        values.put(Cols.UNIT, weather.getQuery().getResults().getChannel().getUnits().getTemperature());
        values.put(Cols.WIND_DIRECTION, weather.getQuery().getResults().getChannel().getWind().getDirection());
        values.put(Cols.WIND_SPEED, weather.getQuery().getResults().getChannel().getWind().getSpeed());
        values.put(Cols.WIND_SPEED_UNITS, weather.getQuery().getResults().getChannel().getUnits().getSpeed());
        values.put(Cols.HUMIDITY, weather.getQuery().getResults().getChannel().getAtmosphere().getHumidity());
        values.put(Cols.PRESSURE, weather.getQuery().getResults().getChannel().getAtmosphere().getPressure());
        values.put(Cols.SUNRISE_TIME, weather.getQuery().getResults().getChannel().getAstronomy().getSunrise());
        values.put(Cols.SUNSET_TIME, weather.getQuery().getResults().getChannel().getAstronomy().getSunset());
        return values;
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
