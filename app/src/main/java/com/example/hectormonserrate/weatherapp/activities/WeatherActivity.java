package com.example.hectormonserrate.weatherapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.hectormonserrate.weatherapp.R;
import com.example.hectormonserrate.weatherapp.adapters.WeatherDayAdapter;
import com.example.hectormonserrate.weatherapp.dto.Autocomplete;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {
  private final static String AUTOCOMPLETE_EXTRA = "autocomplete";
  private final static String FORECASTS_EXTRA = "forecasts";

  @BindView(R.id.vpPager) ViewPager vpPager;

  private List<Forecast> forecasts;
  private Autocomplete autocomplete;
  private FragmentPagerAdapter adapter;

  public static void launch(@NonNull Context context, @NonNull Autocomplete autocomplete,
      @NonNull List<Forecast> forecasts) {
    Intent intent = new Intent(context, WeatherActivity.class);
    intent.putExtra(AUTOCOMPLETE_EXTRA, autocomplete);
    intent.putParcelableArrayListExtra(FORECASTS_EXTRA, new ArrayList<Parcelable>(forecasts));

    context.startActivity(intent);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weather);

    ButterKnife.bind(this);

    if (savedInstanceState == null) {
      forecasts = getIntent().getExtras().getParcelableArrayList(FORECASTS_EXTRA);
      autocomplete = getIntent().getExtras().getParcelable(AUTOCOMPLETE_EXTRA);
    } else {
      forecasts = savedInstanceState.getParcelableArrayList(FORECASTS_EXTRA);
      autocomplete = savedInstanceState.getParcelable(AUTOCOMPLETE_EXTRA);
    }

    setTitle(autocomplete.name());
    adapter = new WeatherDayAdapter(this, getSupportFragmentManager(), forecasts);
    vpPager.setAdapter(adapter);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putParcelableArrayList(FORECASTS_EXTRA, new ArrayList<Parcelable>(forecasts));
    outState.putParcelable(AUTOCOMPLETE_EXTRA, autocomplete);
  }
}
