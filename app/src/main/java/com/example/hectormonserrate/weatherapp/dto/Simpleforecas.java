package com.example.hectormonserrate.weatherapp.dto;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@AutoValue public abstract class Simpleforecas {
  @SerializedName("forecastday") abstract public List<Forecast> forecasts();

  public static TypeAdapter<Simpleforecas> typeAdapter(Gson gson) {
    return new AutoValue_Simpleforecas.GsonTypeAdapter(gson);
  }
}
