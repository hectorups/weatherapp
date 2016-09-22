package com.example.hectormonserrate.weatherapp.presenter;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import com.example.hectormonserrate.weatherapp.dto.Autocomplete;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.example.hectormonserrate.weatherapp.repositories.ApiRepository;
import com.example.hectormonserrate.weatherapp.repositories.NoResultsException;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SelectionPresenter extends BaseRxPresenter<SelectionPresenter.Callback> {

  private ApiRepository apiRepository;

  public interface Callback {
    void showNocityError();

    void showNetworkError();

    void results(Pair<Autocomplete, List<Forecast>> results);
  }

  @Inject public SelectionPresenter(@NonNull ApiRepository apiRepository) {
    this.apiRepository = apiRepository;
  }

  public void resultsForQuery(@NonNull String query) {
    if (getView() == null) {
      throw new AssertionError("Can't invoke resultsForQuery before attach");
    }

    Subscription subscription = apiRepository.tenDayForecastForQuery(query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Pair<Autocomplete, List<Forecast>>>() {
          @Override public void call(Pair<Autocomplete, List<Forecast>> autocompleteListPair) {
            Timber.d(autocompleteListPair.first.name());
            getView().results(autocompleteListPair);
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            Timber.e(throwable.toString());

            if (throwable instanceof NoResultsException) {
              getView().showNocityError();
            } else {
              getView().showNetworkError();
            }
          }
        });

    getSubscriptions().add(subscription);
  }
}
