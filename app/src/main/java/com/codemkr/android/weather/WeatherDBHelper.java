package com.codemkr.android.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.codemkr.android.weather.WeatherDBSchema.*;

public class WeatherDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "weather_database";

    public WeatherDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + WeatherTable.TABLE_NAME + "(" + " _id integer primary key autoincrement," +
                Cols.UUID + ", " +
                Cols.LOCATION_REQUEST + ", " +
                Cols.LOCATION_CITY + ", " +
                Cols.LOCATION_COUNTRY + ", " +
                Cols.TEMPERATURE_ACTUAL + ", " +
                Cols.TEMPERATURE_FORECAST + ", " +
                Cols.UNIT + ", " +
                Cols.CODE + ", " +
                Cols.CONDITIONS + ", " +
                Cols.HUMIDITY + ", " +
                Cols.PRESSURE + ", " +
                Cols.WIND_DIRECTION + ", " +
                Cols.WIND_SPEED + ", " +
                Cols.WIND_SPEED_UNITS + ", " +
                Cols.SUNRISE_TIME + ", " +
                Cols.SUNSET_TIME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
