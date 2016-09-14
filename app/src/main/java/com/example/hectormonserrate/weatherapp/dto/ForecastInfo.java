package com.example.hectormonserrate.weatherapp.dto;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue public abstract class ForecastInfo {
  abstract public Simpleforecas simpleforecast();

  public static TypeAdapter<ForecastInfo> typeAdapter(Gson gson) {
    return new AutoValue_ForecastInfo.GsonTypeAdapter(gson);
  }
}
