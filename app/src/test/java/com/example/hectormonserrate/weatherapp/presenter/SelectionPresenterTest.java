package com.example.hectormonserrate.weatherapp.presenter;

import android.support.v4.util.Pair;
import com.example.hectormonserrate.weatherapp.RxJavaResetRule;
import com.example.hectormonserrate.weatherapp.dto.Autocomplete;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.example.hectormonserrate.weatherapp.repositories.ApiRepository;
import com.example.hectormonserrate.weatherapp.repositories.NoResultsException;
import factories.AutocompleteFactory;
import factories.ForecastFactory;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SelectionPresenterTest {
  @Rule public RxJavaResetRule rxJavaResetRule = new RxJavaResetRule();

  @Mock ApiRepository apiRepository;

  @Mock SelectionPresenter.Callback callback;

  SelectionPresenter selectionPresenter;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    selectionPresenter = new SelectionPresenter(apiRepository);
    selectionPresenter.onAttach(callback);
  }

  @Test public void resultsForQuery_200OkResponse() {
    when(apiRepository.tenDayForecastForQuery(anyString())).thenReturn(Observable.just(response()));

    selectionPresenter.resultsForQuery("zaragoza");

    verify(callback).results(response());
  }

  @Test public void testRequestPhotos_NetworkFail() throws Exception {
    when(apiRepository.tenDayForecastForQuery(anyString())).thenReturn(
        Observable.<Pair<Autocomplete, List<Forecast>>>error(new Exception()));

    selectionPresenter.resultsForQuery("zaragoza");

    verify(callback, never()).results(response());
    verify(callback).showNetworkError();
  }

  @Test public void testRequestPhotos_NoCityError() throws Exception {
    when(apiRepository.tenDayForecastForQuery(anyString())).thenReturn(
        Observable.<Pair<Autocomplete, List<Forecast>>>error(new NoResultsException()));

    selectionPresenter.resultsForQuery("zaragoza");

    verify(callback, never()).results(response());
    verify(callback).showNocityError();
  }

  private Pair<Autocomplete, List<Forecast>> response() {
    return new Pair<>(AutocompleteFactory.autocomplete(),
        Collections.singletonList(ForecastFactory.forecast()));
  }
}