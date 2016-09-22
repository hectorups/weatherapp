package com.example.hectormonserrate.weatherapp.dto;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@AutoValue public abstract class ForecastRes {
  @SerializedName("forecast") abstract public ForecastInfo forecastInfo();

  public static Builder builder() {
    return new AutoValue_ForecastRes.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder setForecastInfo(ForecastInfo forecastInfo);

    public abstract ForecastRes build();
  }

  public static TypeAdapter<ForecastRes> typeAdapter(Gson gson) {
    return new AutoValue_ForecastRes.GsonTypeAdapter(gson);
  }
}
