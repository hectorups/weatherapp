package com.example.hectormonserrate.weatherapp.di;

import com.example.hectormonserrate.weatherapp.activities.SelectionActivityTest;
import dagger.Component;
import javax.inject.Singleton;
import okhttp3.mockwebserver.MockWebServer;

@Singleton @Component(modules = { MockHostModule.class, NetModule.class })
public interface TestNetComponent extends NetComponent {
  void inject(SelectionActivityTest test);
}
