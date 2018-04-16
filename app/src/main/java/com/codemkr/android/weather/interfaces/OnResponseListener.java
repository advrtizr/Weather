package com.codemkr.android.weather.interfaces;

import com.codemkr.android.weather.json.Weather;

public interface OnResponseListener {

    void onSuccess(Weather weather);
    void onFailure();
}
