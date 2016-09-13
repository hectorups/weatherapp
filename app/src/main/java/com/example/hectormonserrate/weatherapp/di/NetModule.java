package com.example.hectormonserrate.weatherapp.di;

import android.app.Application;
import com.example.hectormonserrate.weatherapp.BuildConfig;
import com.example.hectormonserrate.weatherapp.api.AutocompleteService;
import com.example.hectormonserrate.weatherapp.api.WundergroundService;
import com.example.hectormonserrate.weatherapp.dto.MyAdapterFactory;
import com.example.hectormonserrate.weatherapp.repositories.ApiRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class NetModule {

  private String baseUrl;
  private String baseUrl2;
  private Application application;

  public NetModule(Application application, String baseUrl, String baseUrl2) {
    this.baseUrl = baseUrl;
    this.baseUrl2 = baseUrl2;
    this.application = application;
  }

  @Provides @Singleton Cache provideOkHttpCache() {
    int cacheSize = 5 * 1024 * 1024;
    return new Cache(application.getCacheDir(), cacheSize);
  }

  @Provides @Singleton Picasso providePicasso() {
    Picasso.Builder builder = new Picasso.Builder(application.getApplicationContext());
    return builder.build();
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(Cache cache) {

    OkHttpClient.Builder builder = (new OkHttpClient()).newBuilder();

    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(logging);
    }

    return builder.cache(cache).build();
  }

  @Provides @Singleton Gson provideGson() {
    return new GsonBuilder().registerTypeAdapterFactory(MyAdapterFactory.create()).create();
  }

  @Provides @Singleton WundergroundService provideWundergroundService(Gson gson,
      OkHttpClient okHttpClient) {
    Retrofit retrofit =
        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build();

    return retrofit.create(WundergroundService.class);
  }

  @Provides @Singleton AutocompleteService provideAutocompleteService(Gson gson,
      OkHttpClient okHttpClient) {
    Retrofit retrofit =
        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(baseUrl2)
            .client(okHttpClient)
            .build();

    return retrofit.create(AutocompleteService.class);
  }

  @Provides @Singleton ApiRepository provideRepository(WundergroundService wundergroundService,
      AutocompleteService autocompleteService) {
    return new ApiRepository(wundergroundService, autocompleteService);
  }
}
