package com.codemkr.android.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codemkr.android.weather.interfaces.OnRefreshStateListener;
import com.codemkr.android.weather.json.Forecast;
import com.codemkr.android.weather.json.Weather;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastFragment extends Fragment implements OnRefreshStateListener {

    private static final String TAG = "ForecastFragment";
    private static final String WEATHER_ID_ARG = "weather_id";

    private Weather mWeather;
    private UUID mUUID;
    private ForecastAdapter mForecastAdapter;

    @BindView(R.id.forecast_recycler)
    RecyclerView forecastRecycler;
    @BindView(R.id.details_temperature)
    TextView temperature;
    @BindView(R.id.details_location)
    TextView location;
    @BindView(R.id.details_conditions)
    TextView condition;
    @BindView(R.id.iv_details_conditions)
    ImageView conditionImage;
    @BindView(R.id.details_feels_temperature)
    TextView feelsTemperature;
    @BindView(R.id.details_wind_direction)
    TextView direction;
    @BindView(R.id.details_wind_speed)
    TextView speed;
    @BindView(R.id.details_humidity)
    TextView humidity;
    @BindView(R.id.details_pressure)
    TextView pressure;
    @BindView(R.id.details_sunrise)
    TextView sunrise;
    @BindView(R.id.details_sunset)
    TextView sunset;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUUID = (UUID) getArguments().getSerializable(WEATHER_ID_ARG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_fragment, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WeatherLab.getInstance().requestUpdate(mUUID, ForecastFragment.this);
            }
        });
        refreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        WeatherLab.getInstance().requestUpdate(mUUID, this);
    }

    private void initializeAdapter(Weather weather) {
        List<Forecast> forecastList = weather.getQuery().getResults().getChannel().getItem().getForecast();
        if (mForecastAdapter == null) {
            mForecastAdapter = new ForecastAdapter(getActivity(), forecastList);
            LinearLayoutManager horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            forecastRecycler.setLayoutManager(horizontalLayout);
            forecastRecycler.hasFixedSize();
            forecastRecycler.setAdapter(mForecastAdapter);
        } else {
            mForecastAdapter.updateList(forecastList);
            mForecastAdapter.notifyDataSetChanged();
        }

    }

    private void initializeData(Weather weather) {
        String locationCity = weather.getQuery().getResults().getChannel().getLocation().getCity();
        String locationCountry = weather.getQuery().getResults().getChannel().getLocation().getCountry();
        String locationSummary = locationCity + ", " + locationCountry;
        String temperatureQuery = weather.getQuery().getResults().getChannel().getItem().getCondition().getTemp() + "\u00B0";
        String conditionsQuery = weather.getQuery().getResults().getChannel().getItem().getCondition().getText();
        String codeQuery = weather.getQuery().getResults().getChannel().getItem().getCondition().getCode();
        String feelsTemperatureQuery = tempConverter(weather, weather.getQuery().getResults().getChannel().getWind().getChill()) + "\u00B0";
        String directionQuery = windDirection(weather.getQuery().getResults().getChannel().getWind().getDirection());
        String speedQuery = weather.getQuery().getResults().getChannel().getWind().getSpeed();
        String speedUnits = weather.getQuery().getResults().getChannel().getUnits().getSpeed();
        String speedSummary = speedQuery + " " + speedUnits;
        String humidityQuery = weather.getQuery().getResults().getChannel().getAtmosphere().getHumidity();
        String pressureQuery = pressureDivide(weather.getQuery().getResults().getChannel().getAtmosphere().getPressure());
        String sunriseQuery = weather.getQuery().getResults().getChannel().getAstronomy().getSunrise();
        String sunsetQuery = weather.getQuery().getResults().getChannel().getAstronomy().getSunset();

        location.setText(locationSummary);
        temperature.setText(temperatureQuery);
        condition.setText(conditionsQuery);
        feelsTemperature.setText(feelsTemperatureQuery);
        direction.setText(directionQuery);
        speed.setText(speedSummary);
        humidity.setText(humidityQuery);
        pressure.setText(pressureQuery);
        sunrise.setText(sunriseQuery);
        sunset.setText(sunsetQuery);
        int resource = getResources().getIdentifier("@drawable/ic_" + codeQuery, null, getActivity().getPackageName());
        conditionImage.setImageResource(resource);
    }

    private String tempConverter(Weather weather, String temperature) {
        String units = (weather.getQuery().getResults().getChannel().getUnits().getTemperature()).toLowerCase();
        if (units.equals(Constants.CELSIUS.toLowerCase())) {
            int t = Integer.parseInt(temperature);
            int result = (t - 32) * 5 / 9;
            return String.valueOf(result);
        }
        return temperature;
    }

    private String windDirection(String direction) {
        int d = Integer.parseInt(direction);
        if (d < 45) {
            return "N";
        } else if (d >= 45 && d < 90) {
            return "NE";
        } else if (d >= 90 && d < 135) {
            return "E";
        } else if (d >= 135 && d < 180) {
            return "SE";
        } else if (d >= 180 && d < 225) {
            return "S";
        } else if (d >= 225 && d < 270) {
            return "SW";
        } else if (d >= 270 && d < 315) {
            return "W";
        } else if (d >= 315 && d < 360) {
            return "NW";
        }
        return direction;
    }

    private String pressureDivide(String pressure) {
        float p = Float.parseFloat(pressure) / 1000;
        return String.valueOf(p);
    }

    @Override
    public void updateUI() {
        Weather weather = WeatherLab.getInstance().getWeather(mUUID);
        initializeAdapter(weather);
        initializeData(weather);
    }

    public static Fragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(WEATHER_ID_ARG, id);
        ForecastFragment fragment = new ForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
