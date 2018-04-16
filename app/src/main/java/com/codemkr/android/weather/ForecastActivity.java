package com.codemkr.android.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codemkr.android.weather.interfaces.OnRefreshStateListener;
import com.codemkr.android.weather.interfaces.OnResponseListener;
import com.codemkr.android.weather.json.Forecast;
import com.codemkr.android.weather.json.Weather;

public class ForecastActivity extends AppCompatActivity implements OnRefreshStateListener {

    private TextView mTemperatureTextView;
    private Button mAddButton;
    private Button mRefreshButton;
    private Weather mWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        mWeather = new Weather();
        mWeather.setLocation("kiev");
        WeatherLab.getInstance().addWeather(mWeather);
        mTemperatureTextView = findViewById(R.id.temperature_text_view);
        mAddButton = findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mRefreshButton = findViewById(R.id.refresh_button);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherLab.getInstance().requestUpdate(mWeather, ForecastActivity.this);
            }
        });
        WeatherLab.getInstance().requestUpdate(mWeather, ForecastActivity.this);
    }


    @Override
    public void refresh() {
        Weather weather = WeatherLab.getInstance().getWeather(mWeather);
        mTemperatureTextView.setText(weather.getQuery().getResults().getChannel().getLastBuildDate());
    }
}
