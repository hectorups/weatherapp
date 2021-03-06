package com.example.hectormonserrate.weatherapp.di;

import android.content.Context;
import com.example.hectormonserrate.weatherapp.BuildConfig;
import com.example.hectormonserrate.weatherapp.api.AutocompleteService;
import com.example.hectormonserrate.weatherapp.api.Endpoint;
import com.example.hectormonserrate.weatherapp.api.WundergroundService;
import com.example.hectormonserrate.weatherapp.dto.MyAdapterFactory;
import com.example.hectormonserrate.weatherapp.repositories.ApiRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class NetModule {

  private Context context;

  public NetModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton Cache provideOkHttpCache() {
    int cacheSize = 5 * 1024 * 1024;
    return new Cache(context.getCacheDir(), cacheSize);
  }

  @Provides @Singleton Picasso providePicasso() {
    Picasso.Builder builder = new Picasso.Builder(context.getApplicationContext());
    return builder.build();
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(Cache cache) {

    OkHttpClient.Builder builder = (new OkHttpClient()).newBuilder();

    builder.connectTimeout(2, TimeUnit.SECONDS);
    builder.readTimeout(20, TimeUnit.SECONDS);
    builder.writeTimeout(30, TimeUnit.SECONDS);

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

  @Provides @Singleton WundergroundService provideWundergroundService(
      @Named("weather") Endpoint endpoint, Gson gson, OkHttpClient okHttpClient) {
    Retrofit retrofit =
        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(endpoint.getUrl())
            .client(okHttpClient)
            .build();

    return retrofit.create(WundergroundService.class);
  }

  @Provides @Singleton AutocompleteService provideAutocompleteService(
      @Named("autocomplete") Endpoint endpoint, Gson gson, OkHttpClient okHttpClient) {
    Retrofit retrofit =
        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(endpoint.getUrl())
            .client(okHttpClient)
            .build();

    return retrofit.create(AutocompleteService.class);
  }

  @Provides @Singleton ApiRepository provideRepository(WundergroundService wundergroundService,
      AutocompleteService autocompleteService) {
    return new ApiRepository(wundergroundService, autocompleteService);
  }
}
