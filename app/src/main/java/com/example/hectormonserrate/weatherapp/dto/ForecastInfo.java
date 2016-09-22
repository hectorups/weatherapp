package com.example.hectormonserrate.weatherapp.dto;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.List;

@AutoValue public abstract class ForecastInfo {
  abstract public Simpleforecas simpleforecast();

  public static Builder builder() {
    return new AutoValue_ForecastInfo.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder setSimpleforecast(Simpleforecas simpleforecast);

    public abstract ForecastInfo build();
  }

  public static TypeAdapter<ForecastInfo> typeAdapter(Gson gson) {
    return new AutoValue_ForecastInfo.GsonTypeAdapter(gson);
  }
}
