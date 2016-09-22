package com.example.hectormonserrate.weatherapp;

import android.support.annotation.NonNull;
import com.example.hectormonserrate.weatherapp.di.DaggerTestNetComponent;
import com.example.hectormonserrate.weatherapp.di.Injector;
import com.example.hectormonserrate.weatherapp.di.MockHostModule;
import com.example.hectormonserrate.weatherapp.di.NetModule;
import com.example.hectormonserrate.weatherapp.di.TestNetComponent;

public class TestWeatherApp extends WeatherApp {

  private TestNetComponent testNetComponent;

  @Override public void onCreate() {
    super.onCreate();
    testNetComponent = DaggerTestNetComponent.builder()
        .mockHostModule(new MockHostModule())
        .netModule(new NetModule(this))
        .build();
  }

  @Override public Object getSystemService(@NonNull String name) {
    if (Injector.matchesService(name)) {
      return testNetComponent;
    }
    return super.getSystemService(name);
  }
}
