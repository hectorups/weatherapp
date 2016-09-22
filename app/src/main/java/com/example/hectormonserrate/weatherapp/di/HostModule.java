package com.example.hectormonserrate.weatherapp.di;

import com.example.hectormonserrate.weatherapp.api.Endpoint;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class HostModule {

  private String url1;
  private String url2;

  public HostModule(String url1, String url2) {
    this.url1 = url1;
    this.url2 = url2;
  }

  @Provides @Named("autocomplete") Endpoint provideAutocompleteEndpoint() {
    return new Endpoint(url1);
  }

  @Provides @Named("weather") Endpoint provideWeatherEndpoint() {
    return new Endpoint(url2);
  }
}
