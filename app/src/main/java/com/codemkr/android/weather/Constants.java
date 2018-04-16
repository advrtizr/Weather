package com.codemkr.android.weather;

public class Constants {

    public static final String BASE_URL = "https://query.yahooapis.com/";
    public static final String LOCATION_PARAM_START = "select*from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"";
    public static final String LOCATION_PARAM_END = "\")";
    public static final String FORMAT_PARAM = "json";
}
