package com.example.hectormonserrate.weatherapp.di;

import android.content.Context;
import android.support.annotation.Nullable;

public final class Injector {
  private static final String INJECTOR_SERVICE = "com.example.hectormonserrate.weatherapp.module";

  @SuppressWarnings({"ResourceType", "WrongConstant"})
  public static NetComponent obtainNetComponent(Context context) {
    return (NetComponent) context.getApplicationContext().getSystemService(INJECTOR_SERVICE);
  }

  public static boolean matchesService(String name) {
    return INJECTOR_SERVICE.equals(name);
  }

  private Injector() {
    throw new AssertionError("No instances.");
  }
}
