package com.codemkr.android.weather;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codemkr.android.weather.json.Weather;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastActivity extends AppCompatActivity {

    @BindView(R.id.weather_view_pager)
    ViewPager mViewPager;
    private List<Weather> mWeatherList;
    private Weather mWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        mWeather = new Weather();
        mWeather.setLocationKey("kiev");
        WeatherLab.getInstance(this).addWeather(mWeather);

        mWeatherList = WeatherLab.getInstance(this).getWeathers();
        FragmentManager manager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                Weather weather = mWeatherList.get(position);
                return ForecastFragment.newInstance(weather.getUUID());
            }

            @Override
            public int getCount() {
                return mWeatherList.size();
            }
        });

    }
}
