package com.example.hectormonserrate.weatherapp.api;

import com.example.hectormonserrate.weatherapp.dto.AutocompleteRes;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface AutocompleteService {

  @GET("aq") Observable<AutocompleteRes> getQuery(@Query("query") String q);
}
