package com.example.hectormonserrate.weatherapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.hectormonserrate.weatherapp.R;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.example.hectormonserrate.weatherapp.fragments.WeatherDayFragment;
import java.util.List;

public class WeatherDayAdapter extends FragmentPagerAdapter {
  private List<Forecast> forecasts;
  final private Context context;

  public WeatherDayAdapter(@NonNull Context context, @NonNull FragmentManager fragmentManager,
      @NonNull List<Forecast> forecasts) {
    super(fragmentManager);
    this.context = context;
    this.forecasts = forecasts;
  }

  @Override public Fragment getItem(int position) {
    return WeatherDayFragment.newInstance(forecasts.get(position));
  }

  @Override public int getCount() {
    return forecasts.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    return context.getString(R.string.tab_title, position + 1);
  }
}
