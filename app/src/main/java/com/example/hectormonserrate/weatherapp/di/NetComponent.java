package com.example.hectormonserrate.weatherapp.di;

import com.example.hectormonserrate.weatherapp.activities.SelectionActivity;
import com.example.hectormonserrate.weatherapp.fragments.WeatherDayFragment;
import com.example.hectormonserrate.weatherapp.repositories.ApiRepository;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { HostModule.class, NetModule.class }) public interface NetComponent {
  ApiRepository apiRepository();

  void inject(SelectionActivity activity);

  void inject(WeatherDayFragment fragment);
}
