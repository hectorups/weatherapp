package com.example.hectormonserrate.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.hectormonserrate.weatherapp.R;
import com.example.hectormonserrate.weatherapp.di.Injector;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

public class WeatherDayFragment extends Fragment {
  private static final String ARG_FORECAST = "forecast";

  @BindView(R.id.tvHigh) TextView tvHigh;
  @BindView(R.id.tvLow) TextView tvLow;
  @BindView(R.id.tvDescription) TextView tvDescription;
  @BindView(R.id.ivLogo) ImageView ivLogo;

  @Inject Picasso picasso;

  private Forecast forecast;

  public static WeatherDayFragment newInstance(Forecast forecast) {
    WeatherDayFragment fragment = new WeatherDayFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARG_FORECAST, forecast);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Injector.obtainNetComponent(getContext()).inject(this);

    if (getArguments() != null) {
      forecast = getArguments().getParcelable(ARG_FORECAST);
    } else {
      forecast = savedInstanceState.getParcelable(ARG_FORECAST);
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(ARG_FORECAST, forecast);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_weather_day, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    tvHigh.setText(getString(R.string.celsius, forecast.high().celsius()));
    tvLow.setText(getString(R.string.celsius, forecast.low().celsius()));
    tvDescription.setText(forecast.conditions());
    picasso.load(forecast.icon()).into(ivLogo);
  }
}
