package com.example.hectormonserrate.weatherapp.dto;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue public abstract class Forecast implements Parcelable {
  public abstract WondergroundTemp high();

  public abstract WondergroundTemp low();

  public abstract WondergroundDate date();

  public abstract String conditions();

  @SerializedName("icon_url") public abstract String icon();

  public static Builder builder() {
    return new AutoValue_Forecast.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder setDate(WondergroundDate date);

    public abstract Builder setConditions(String conditions);

    public abstract Builder setHigh(WondergroundTemp high);

    public abstract Builder setLow(WondergroundTemp low);

    public abstract Builder setIcon(String icon);

    public abstract Forecast build();
  }

  public static TypeAdapter<Forecast> typeAdapter(Gson gson) {
    return new AutoValue_Forecast.GsonTypeAdapter(gson);
  }
}
