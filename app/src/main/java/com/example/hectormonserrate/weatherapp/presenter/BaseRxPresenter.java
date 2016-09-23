package com.example.hectormonserrate.weatherapp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseRxPresenter<T> {
  @Nullable private T view;
  private final CompositeSubscription subscriptions;

  public BaseRxPresenter() {
    this.subscriptions = new CompositeSubscription();
  }

  final public CompositeSubscription getSubscriptions() {
    return subscriptions;
  }

  final public @Nullable T getView() {
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
