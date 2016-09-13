package com.example.hectormonserrate.weatherapp.presenter;

import android.support.annotation.NonNull;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseRxPresenter<T> {
  private T view;
  private final CompositeSubscription subscriptions;

  public BaseRxPresenter() {
    this.subscriptions = new CompositeSubscription();
  }

  final public CompositeSubscription getSubscriptions() {
    return subscriptions;
  }

  final public T getView() {
    return view;
  }

  public void onAttach(@NonNull final T view) {
    this.view = view;
  }

  public void onDetach() {
    subscriptions.unsubscribe();
    this.view = null;
  }
}
