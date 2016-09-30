package com.example.hectormonserrate.weatherapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.hectormonserrate.weatherapp.R;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.example.hectormonserrate.weatherapp.matchers.ViewPagerMatcher;
import factories.AutocompleteFactory;
import factories.ForecastFactory;
import java.util.Collections;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.hectormonserrate.weatherapp.OrientationChangeAction.orientationLandscape;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class) public class WeatherActivityTest {

  @Rule public ActivityTestRule<WeatherActivity> testRule =
      new ActivityTestRule<>(WeatherActivity.class, true, false);

  @Test public void weatherActivity() {
    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    final Forecast forecast = ForecastFactory.forecast();
    Intent intent = WeatherActivity.buildIntent(targetContext, AutocompleteFactory.autocomplete(),
        Collections.singletonList(forecast));

    testRule.launchActivity(intent);

    onView(withId(R.id.vpPager)).check(matches(ViewPagerMatcher.withListSize(1)));

    onView(allOf(withId(R.id.tvDescription), withText(forecast.conditions()))).check(
        matches(isDisplayed()));

    onView(allOf(withId(R.id.tvHigh), withText(forecast.high().celsius() + "°"))).check(
        matches(isDisplayed()));

    onView(allOf(withId(R.id.tvLow), withText(forecast.low().celsius() + "°"))).check(
        matches(isDisplayed()));
  }

  //onView(isRoot()).perform(orientationLandscape());

  @Test public void weatherActivity_OrientationChange() {
    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    final Forecast forecast = ForecastFactory.forecast();
    Intent intent = WeatherActivity.buildIntent(targetContext, AutocompleteFactory.autocomplete(),
        Collections.singletonList(forecast));

    testRule.launchActivity(intent);

    onView(isRoot()).perform(orientationLandscape());

    onView(withId(R.id.vpPager)).check(matches(ViewPagerMatcher.withListSize(1)));

    onView(allOf(withId(R.id.tvDescription), withText(forecast.conditions()))).check(
        matches(isDisplayed()));
  }
}