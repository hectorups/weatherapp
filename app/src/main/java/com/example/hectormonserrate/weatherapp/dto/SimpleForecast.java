package com.example.hectormonserrate.weatherapp.dto;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@AutoValue public abstract class Simpleforecast {
  @SerializedName("forecastday") abstract public List<Forecast> forecasts();

  public static TypeAdapter<Simpleforecast> typeAdapter(Gson gson) {
    return new AutoValue_Simpleforecast.GsonTypeAdapter(gson);
  }
}
