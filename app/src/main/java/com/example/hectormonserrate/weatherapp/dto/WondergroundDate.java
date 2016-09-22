package com.example.hectormonserrate.weatherapp.dto;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue public abstract class WondergroundDate implements Parcelable {
  public abstract String epoch();

  public static Builder builder() {
    return new AutoValue_WondergroundDate.Builder();
  }


@AutoValue.Builder public abstract static class Builder {
  public abstract Builder setEpoch(String epoch);

  public abstract WondergroundDate build();

}

  public static TypeAdapter<WondergroundDate> typeAdapter(Gson gson) {
    return new AutoValue_WondergroundDate.GsonTypeAdapter(gson);
  }
}
