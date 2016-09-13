package com.example.hectormonserrate.weatherapp.api;

import com.example.hectormonserrate.weatherapp.dto.AutocompleteRes;
import com.example.hectormonserrate.weatherapp.dto.ForecastRes;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface WundergroundService {

  @GET("forecast10day/q/{lat},{lng}.json") Observable<ForecastRes> getForecast10Days(
      @Path("lat") String lat, @Path("lng") String lng);
}
