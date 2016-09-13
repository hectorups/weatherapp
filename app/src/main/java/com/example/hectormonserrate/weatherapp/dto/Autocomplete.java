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

  public static TypeAdapter<Autocomplete> typeAdapter(Gson gson) {
    return new AutoValue_Autocomplete.GsonTypeAdapter(gson);
  }
}
