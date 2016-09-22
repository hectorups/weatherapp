package com.example.hectormonserrate.weatherapp.api;

import android.support.annotation.NonNull;

final public class Endpoint {
  private final String url;

  public Endpoint(@NonNull String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
