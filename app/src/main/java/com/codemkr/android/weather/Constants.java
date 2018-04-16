package com.codemkr.android.weather;

public class Constants {

    public static final String BASE_URL = "https://query.yahooapis.com/";
//    public static final String LOCATION_PARAM_START = "select*from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"";
    public static final String LOCATION_PARAM_START = "v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22";
    public static final String LOCATION_PARAM_END = "%22)&format=json";
}
