package com.example.hectormonserrate.weatherapp;

import android.app.Application;
import android.support.annotation.NonNull;
import com.example.hectormonserrate.weatherapp.di.DaggerNetComponent;
import com.example.hectormonserrate.weatherapp.di.Injector;
import com.example.hectormonserrate.weatherapp.di.NetComponent;
import com.example.hectormonserrate.weatherapp.di.NetModule;

public class WeatherApp extends Application {

  private NetComponent netComponent;

  @Override public void onCreate() {
    super.onCreate();

    netComponent = DaggerNetComponent.builder()
        .netModule(new NetModule(this, Constants.WUNDER_BASE_URL, Constants.AUTOCOMPLETE_BASE_URL))
        .build();
  }

  @Override public Object getSystemService(@NonNull String name) {
    if (Injector.matchesService(name)) {
      return netComponent;
    }
    return super.getSystemService(name);
  }
}
