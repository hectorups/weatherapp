package com.example.hectormonserrate.weatherapp.di;

import com.example.hectormonserrate.weatherapp.activities.SelectionActivity;
import com.example.hectormonserrate.weatherapp.fragments.WeatherDayFragment;
import com.example.hectormonserrate.weatherapp.presenter.SelectionPresenter;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { NetModule.class }) public interface NetComponent {
  void inject(SelectionActivity activity);

  void inject(SelectionPresenter presenter);

  void inject(WeatherDayFragment fragment);
}
