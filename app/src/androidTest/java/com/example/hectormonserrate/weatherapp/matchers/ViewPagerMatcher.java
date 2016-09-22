package com.example.hectormonserrate.weatherapp.matchers;

import android.support.v4.view.ViewPager;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ViewPagerMatcher {
  public static Matcher<View> withListSize(final int size) {
    return new TypeSafeMatcher<View>() {
      @Override public boolean matchesSafely(final View view) {
        return ((ViewPager) view).getAdapter().getCount() == size;
      }

      @Override public void describeTo(final Description description) {
        description.appendText("ViewPager should have " + size + " items");
      }
    };
  }
}
