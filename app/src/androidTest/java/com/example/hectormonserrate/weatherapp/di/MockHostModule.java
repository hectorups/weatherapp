package com.example.hectormonserrate.weatherapp.di;

import com.example.hectormonserrate.weatherapp.ServerInstance;
import com.example.hectormonserrate.weatherapp.api.Endpoint;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import okhttp3.mockwebserver.MockWebServer;

@Module public class MockHostModule {

  @Provides @Named("autocomplete") Endpoint provideAutocompleteEndpoint() {
    String url = ServerInstance.getServer().url("/").toString();
    return new Endpoint(url);
  }

  @Provides @Named("weather") Endpoint provideWeatherEndpoint() {
    String url = ServerInstance.getServer().url("/").toString();
    return new Endpoint(url);
  }
}
