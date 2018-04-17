package com.codemkr.android.weather;

public class WeatherDBSchema {
    public static class WeatherTable{
    public static final String TABLE_NAME = "forecast_table";
    }
    public static class Cols{
        public static final String UUID = "uuid";
        public static final String LOCATION_REQUEST = "location";
        public static final String LOCATION_CITY = "city";
        public static final String LOCATION_COUNTRY = "country";
        public static final String TEMPERATURE_FORECAST = "temperature_forecast";
        public static final String CONDITIONS = "conditions";
        public static final String CODE = "code";
        public static final String UNIT = "unit";
        public static final String TEMPERATURE_ACTUAL = "temperature_actual";
        public static final String WIND_DIRECTION = "wind_direction";
        public static final String WIND_SPEED = "wind_speed";
        public static final String WIND_SPEED_UNITS = "wind_speed_units";
        public static final String HUMIDITY = "humidity";
        public static final String PRESSURE = "pressure";
        public static final String SUNSET_TIME = "sunset_time";
        public static final String SUNRISE_TIME = "sunrise_time";
    }
}
