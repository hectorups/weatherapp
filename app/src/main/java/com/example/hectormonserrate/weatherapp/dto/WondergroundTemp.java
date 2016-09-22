package com.example.hectormonserrate.weatherapp.dto;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue public abstract class WondergroundTemp implements Parcelable {
  public abstract String celsius();

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_WondergroundTemp.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder setCelsius(String celsius);

    public abstract WondergroundTemp build();
  }

  public static TypeAdapter<WondergroundTemp> typeAdapter(Gson gson) {
    return new AutoValue_WondergroundTemp.GsonTypeAdapter(gson);
  }
}