package com.codemkr.android.weather;

public class Constants {

    public static final String BASE_URL = "https://query.yahooapis.com/";
    public static final String LOCATION_PARAM_START = "select*from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"";
    public static final String LOCATION_PARAM_END = "\")";
    public static final String UNIT_PARAM = "u='c'";
    public static final String FORMAT_PARAM = "json";

    public static final String CELSIUS = "C";
    public static final String FAHRENHEIT = "F";
    public static final String UNIT = "unit";
    public static final String DEGREE = "\u00B0";
}
