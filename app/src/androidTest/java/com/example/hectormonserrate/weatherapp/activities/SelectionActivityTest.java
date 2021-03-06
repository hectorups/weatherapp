package com.example.hectormonserrate.weatherapp.activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.TypeTextAction;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.inputmethod.EditorInfo;
import com.example.hectormonserrate.weatherapp.R;
import com.example.hectormonserrate.weatherapp.RestServiceHelper;
import com.example.hectormonserrate.weatherapp.di.Injector;
import com.example.hectormonserrate.weatherapp.di.TestNetComponent;
import com.example.hectormonserrate.weatherapp.rules.DisableAnimationsRule;
import com.example.hectormonserrate.weatherapp.rules.MockWebServerRule;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.squareup.spoon.Spoon;
import javax.inject.Inject;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class) public class SelectionActivityTest {

  @Inject OkHttpClient client;

  @Rule public ActivityTestRule<SelectionActivity> testRule =
      new ActivityTestRule<>(SelectionActivity.class);

  @ClassRule public static DisableAnimationsRule disableAnimationsRule =
      new DisableAnimationsRule();

  @ClassRule public static MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Before public void setup() throws Exception {
    ((TestNetComponent) Injector.obtainNetComponent(testRule.getActivity())).inject(this);

    mockWebServerRule.clearRequests();

    IdlingResource resource = OkHttp3IdlingResource.create("OkHttp", client);
    Espresso.registerIdlingResources(resource);

    Intents.init();
  }

  @Test public void selectionActivity_normalFlow() throws Exception {
    addOkResponsesToServer();

    onView(withId(R.id.etCity)).perform(new TypeTextAction("zaragoza"));
    onView(withId(R.id.etDays)).perform(new TypeTextAction("2")).perform(closeSoftKeyboard());
    onView(withId(R.id.btnSubmit)).perform(click());

    intended(hasComponent(WeatherActivity.class.getName()));
    Spoon.screenshot(testRule.getActivity(), "end");
  }

  @Test public void selectionActivity_keyboardFlow() throws Exception {
    addOkResponsesToServer();

    onView(withId(R.id.etCity)).perform(new TypeTextAction("madrid"));

    onView(withId(R.id.etCity)).check(matches(hasImeAction(EditorInfo.IME_ACTION_NEXT)))
        .perform(pressImeActionButton());

    onView(withId(R.id.etDays)).perform(new TypeTextAction("2"));

    onView(withId(R.id.etDays)).check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)))
        .perform(pressImeActionButton());

    intended(hasComponent(WeatherActivity.class.getName()));
    Spoon.screenshot(testRule.getActivity(), "end");
  }

  @Test public void selectionActivity_noCity() throws Exception {
    addOkResponsesToServer();

    onView(withId(R.id.etCity)).perform(new TypeTextAction(""));
    onView(withId(R.id.etDays)).perform(new TypeTextAction("2")).perform(closeSoftKeyboard());
    onView(withId(R.id.btnSubmit)).perform(click());

    onView(allOf(withId(android.support.design.R.id.snackbar_text),
        withText(R.string.sel_screen_nocityvalidation))).check(matches(isDisplayed()));

    intended(hasComponent(WeatherActivity.class.getName()), times(0));
    Spoon.screenshot(testRule.getActivity(), "end");
  }

  @Test public void selectionActivity_noDays() throws Exception {
    addOkResponsesToServer();

    onView(withId(R.id.etCity)).perform(new TypeTextAction("zara"));
    onView(withId(R.id.etDays)).perform(new TypeTextAction("")).perform(closeSoftKeyboard());
    onView(withId(R.id.btnSubmit)).perform(click());

    onView(allOf(withId(android.support.design.R.id.snackbar_text),
        withText(R.string.sel_screen_nodaysvalidation))).check(matches(isDisplayed()));

    intended(hasComponent(WeatherActivity.class.getName()), times(0));
    Spoon.screenshot(testRule.getActivity(), "end");
  }

  @Test public void selectionActivity_EmptyResponse() throws Exception {
    addEmptyResponsesToServer();

    onView(withId(R.id.etCity)).perform(new TypeTextAction("madrid"));
    onView(withId(R.id.etDays)).perform(new TypeTextAction("2")).perform(closeSoftKeyboard());
    onView(withId(R.id.btnSubmit)).perform(click());

    onView(allOf(withId(android.support.design.R.id.snackbar_text),
        withText(R.string.sel_screen_nocityerror))).check(matches(isDisplayed()));

    intended(hasComponent(WeatherActivity.class.getName()), times(0));

    Spoon.screenshot(testRule.getActivity(), "end");
  }

  @Test public void selectionActivity_NetworkError() throws Exception {
    addNetworkErrorToServer();

    onView(withId(R.id.etCity)).perform(new TypeTextAction("madrid"));
    onView(withId(R.id.etDays)).perform(new TypeTextAction("2")).perform(closeSoftKeyboard());
    onView(withId(R.id.btnSubmit)).perform(click());

    onView(allOf(withId(android.support.design.R.id.snackbar_text),
        withText(R.string.sel_screen_networkerror))).check(matches(isDisplayed()));

    intended(hasComponent(WeatherActivity.class.getName()), times(0));
    Spoon.screenshot(testRule.getActivity(), "end");
  }

  @After public void tearDown() throws Exception {
    Intents.release();
    for (IdlingResource r : Espresso.getIdlingResources()) {
      Espresso.unregisterIdlingResources(r);
    }
  }

  private void addOkResponsesToServer() throws Exception {
    Context ctx = InstrumentationRegistry.getContext();
    String res1 = RestServiceHelper.getStringFromFile(ctx, "autocomplete_response.json");
    String res2 = RestServiceHelper.getStringFromFile(ctx, "forecast_response.json");
    mockWebServerRule.getServer().enqueue(new MockResponse().setResponseCode(200).setBody(res1));
    mockWebServerRule.getServer().enqueue(new MockResponse().setResponseCode(200).setBody(res2));
  }

  private void addEmptyResponsesToServer() throws Exception {
    mockWebServerRule.getServer()
        .enqueue(new MockResponse().setResponseCode(200).setBody("{\"RESULTS\": []}"));
  }

  private void addNetworkErrorToServer() throws Exception {
    mockWebServerRule.getServer().enqueue(new MockResponse().setResponseCode(400).setBody(""));
  }
}