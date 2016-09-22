package com.example.hectormonserrate.weatherapp.dto;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@AutoValue public abstract class AutocompleteRes {
  @SerializedName("RESULTS") public abstract List<Autocomplete> results();

  public static Builder builder() {
    return new AutoValue_AutocompleteRes.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder setResults(List<Autocomplete> results);

    public abstract AutocompleteRes build();
  }

  public static TypeAdapter<AutocompleteRes> typeAdapter(Gson gson) {
    return new AutoValue_AutocompleteRes.GsonTypeAdapter(gson);
  }
}
