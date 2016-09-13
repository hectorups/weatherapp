package com.example.hectormonserrate.weatherapp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.util.Log;
import com.example.hectormonserrate.weatherapp.di.Injector;
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

public class SelectionPresenter extends BaseRxPresenter<SelectionPresenter.Callback> {

  @Inject ApiRepository apiRepository;

  public interface Callback {
    void showNocityError();

    void showNetworkError();

    void results(Pair<Autocomplete, List<Forecast>> results);

    Context getContext();
  }

  @Override public void onAttach(@NonNull Callback view) {
    super.onAttach(view);
    Injector.obtainNetComponent(view.getContext()).inject(this);
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
            getView().results(autocompleteListPair);
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            Log.e("asdf", throwable.toString());

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
