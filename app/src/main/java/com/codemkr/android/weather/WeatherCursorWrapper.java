package com.codemkr.android.weather;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.codemkr.android.weather.json.Query;
import com.codemkr.android.weather.json.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.UUID;

import static com.codemkr.android.weather.WeatherDBSchema.*;

public class WeatherCursorWrapper extends CursorWrapper {

    public WeatherCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Weather getWeather() {
        String uuid = getString(getColumnIndex(Cols.UUID));
        String locationKey = getString(getColumnIndex(Cols.LOCATION_KEY));
        int isUpdated = getInt(getColumnIndex(Cols.IS_UPDATED));
        String jsonQuery = getString(getColumnIndex(Cols.JSON_QUERY));

        Weather weather = new Weather();
        weather.setUUID(UUID.fromString(uuid));
        weather.setLocationKey(locationKey);
        weather.setUpdated(isUpdated != 0);
        Query query = new Gson().fromJson(jsonQuery, new TypeToken<Query>(){}.getType());
        weather.setQuery(query);
        return weather;
    }
}
