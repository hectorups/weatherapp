package com.example.hectormonserrate.weatherapp.dto;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue public abstract class WondergroundTemp implements Parcelable {
  public abstract String celsius();

  public static TypeAdapter<WondergroundTemp> typeAdapter(Gson gson) {
    return new AutoValue_WondergroundTemp.GsonTypeAdapter(gson);
  }
}