package com.example.hectormonserrate.weatherapp.dto;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue public abstract class Autocomplete implements Parcelable {
  public abstract String lat();

  @SerializedName("lon") public abstract String lng();

  public abstract String name();

  public static Builder builder() {
    return new AutoValue_Autocomplete.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder setLat(String lat);

    public abstract Builder setName(String name);

    public abstract Builder setLng(String lng);

    public abstract Autocomplete build();
  }

  public static TypeAdapter<Autocomplete> typeAdapter(Gson gson) {
    return new AutoValue_Autocomplete.GsonTypeAdapter(gson);
  }
}
