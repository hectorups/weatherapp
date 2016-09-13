package com.example.hectormonserrate.weatherapp.repositories;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import com.example.hectormonserrate.weatherapp.api.AutocompleteService;
import com.example.hectormonserrate.weatherapp.api.WundergroundService;
import com.example.hectormonserrate.weatherapp.dto.Autocomplete;
import com.example.hectormonserrate.weatherapp.dto.AutocompleteRes;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.example.hectormonserrate.weatherapp.dto.ForecastRes;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class ApiRepository {

  private final WundergroundService wundergroundService;
  private final AutocompleteService autocompleteService;

  public ApiRepository(@NonNull final WundergroundService wundergroundService,
      @NonNull final AutocompleteService autocompleteService) {
    this.wundergroundService = wundergroundService;
    this.autocompleteService = autocompleteService;
  }

  public Observable<Pair<Autocomplete, List<Forecast>>> tenDayForecastForQuery(
      @NonNull String query) {
    /*
     * 1 queries city name
     * 2 takes first city and queries time for that city on pair
     * 3 reduces results to have unique days and returns pair result
     */
    return autocompleteService.getQuery(query)
        .flatMap(new Func1<AutocompleteRes, Observable<Pair<Autocomplete, ForecastRes>>>() {
          @Override
          public Observable<Pair<Autocomplete, ForecastRes>> call(AutocompleteRes autocompleteRes) {
            if (autocompleteRes.results().size() == 0) {
              return Observable.error(new NoResultsException());
            }

            Autocomplete city = autocompleteRes.results().get(0);

            Observable<Autocomplete> cityObservable = Observable.just(city);
            Observable<ForecastRes> forecastsObservable =
                wundergroundService.getForecast10Days(city.lat(), city.lng());

            return Observable.zip(cityObservable, forecastsObservable,
                new Func2<Autocomplete, ForecastRes, Pair<Autocomplete, ForecastRes>>() {
                  @Override public Pair<Autocomplete, ForecastRes> call(Autocomplete autocomplete,
                      ForecastRes forecastRes) {
                    return new Pair<>(autocomplete, forecastRes);
                  }
                });
          }
        })
        .map(new Func1<Pair<Autocomplete, ForecastRes>, Pair<Autocomplete, List<Forecast>>>() {
          @Override
          public Pair<Autocomplete, List<Forecast>> call(Pair<Autocomplete, ForecastRes> pair) {
            ForecastRes res = pair.second;

            return new Pair<>(pair.first, res.forecastInfo().simpleforecast().forecasts());
          }
        });
  }
}
