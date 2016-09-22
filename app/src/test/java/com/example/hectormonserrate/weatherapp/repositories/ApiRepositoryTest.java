package com.example.hectormonserrate.weatherapp.repositories;

import android.support.v4.util.Pair;
import com.example.hectormonserrate.weatherapp.api.AutocompleteService;
import com.example.hectormonserrate.weatherapp.api.WundergroundService;
import com.example.hectormonserrate.weatherapp.dto.Autocomplete;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import factories.AutocompleteFactory;
import factories.ForecastFactory;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ApiRepositoryTest {
  @Mock AutocompleteService autocompleteService;
  @Mock WundergroundService wundergroundService;

  @InjectMocks ApiRepository apiRepository;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void getPhotos_200OkResponse_InvokesCorrectApiCalls() throws Exception {
    when(autocompleteService.getQuery(anyString())).thenReturn(
        Observable.just(AutocompleteFactory.queryResponse()));
    when(wundergroundService.getForecast10Days(anyString(), anyString())).thenReturn(
        Observable.just(ForecastFactory.forecast10Days()));

    TestSubscriber<Pair<Autocomplete, List<Forecast>>> testSubscriber = new TestSubscriber<>();
    apiRepository.tenDayForecastForQuery("Zaragoza").subscribe(testSubscriber);

    testSubscriber.awaitTerminalEvent();
    testSubscriber.assertNoErrors();

    Pair<Autocomplete, List<Forecast>> result = testSubscriber.getOnNextEvents().get(0);
    List<Forecast> forecasts = result.second;
    Autocomplete autocomplete = result.first;

    assertThat(forecasts.get(0).conditions()).isEqualTo("cloudy");
    assertThat(forecasts.get(0).high().celsius()).isEqualTo("19");
    assertThat(forecasts.get(0).low().celsius()).isEqualTo("10");
    assertThat(autocomplete.name()).isEqualTo("Zaragoza");
  }
}