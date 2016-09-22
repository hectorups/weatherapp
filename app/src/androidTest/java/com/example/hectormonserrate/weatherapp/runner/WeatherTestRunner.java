package com.example.hectormonserrate.weatherapp.runner;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;
import com.example.hectormonserrate.weatherapp.TestWeatherApp;

public class WeatherTestRunner extends AndroidJUnitRunner {

  @Override public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    String testApplicationClassName = TestWeatherApp.class.getCanonicalName();
    return super.newApplication(cl, testApplicationClassName, context);
  }
}
