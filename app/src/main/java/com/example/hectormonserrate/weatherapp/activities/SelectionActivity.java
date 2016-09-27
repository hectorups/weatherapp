package com.example.hectormonserrate.weatherapp.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.hectormonserrate.weatherapp.R;
import com.example.hectormonserrate.weatherapp.di.Injector;
import com.example.hectormonserrate.weatherapp.dto.Autocomplete;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.example.hectormonserrate.weatherapp.presenter.SelectionPresenter;
import java.util.ArrayList;
import java.util.List;

public class SelectionActivity extends AppCompatActivity implements SelectionPresenter.Callback {

  @BindView(R.id.btnSubmit) Button btnSubmit;
  @BindView(R.id.etCity) EditText etCity;
  @BindView(R.id.etDays) EditText etDays;
  @BindView(R.id.pbLoading) ProgressBar pbLoading;
  @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

  private SelectionPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_selection);

    ButterKnife.bind(this);

    presenter = new SelectionPresenter(Injector.obtainNetComponent(this).apiRepository());

    TextView.OnEditorActionListener listener = new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
          onSubmit(btnSubmit);
          return true;
        } else if (i == EditorInfo.IME_ACTION_NEXT) {
          etDays.requestFocus();
          return true;
        }

        return false;
      }
    };

    etDays.setFilters(new InputFilter[] { new DaysInputFilter() });
    etDays.setOnEditorActionListener(listener);
    etCity.setOnEditorActionListener(listener);
  }

  @OnClick(R.id.btnSubmit) public void onSubmit(View view) {
    String name = etCity.getText().toString();
    String days = etDays.getText().toString();

    if (TextUtils.isEmpty(name)) {
      Snackbar.make(coordinatorLayout, R.string.sel_screen_nocityvalidation, Snackbar.LENGTH_SHORT)
          .show();
      return;
    }

    if (TextUtils.isEmpty(days)) {
      Snackbar.make(coordinatorLayout, R.string.sel_screen_nodaysvalidation, Snackbar.LENGTH_SHORT)
          .show();
      return;
    }

    showLoading();
    presenter.resultsForQuery(etCity.getText().toString());
  }

  @Override protected void onResume() {
    super.onResume();
    btnSubmit.setEnabled(true);
    presenter.onAttach(this);
    hideLoading();
  }

  @Override protected void onPause() {
    presenter.onDetach();
    super.onPause();
  }

  @Override public void results(Pair<Autocomplete, List<Forecast>> results) {
    btnSubmit.setEnabled(true);

    int total = Integer.parseInt(etDays.getText().toString());
    List<Forecast> takeList = new ArrayList<>();
    for (int i = 0; i < total; i++) {
      takeList.add(results.second.get(i));
    }

    startActivity(WeatherActivity.buildIntent(this, results.first, takeList));
  }

  @Override public void showNetworkError() {
    Snackbar.make(coordinatorLayout, R.string.sel_screen_networkerror, Snackbar.LENGTH_SHORT)
        .show();
    hideLoading();
  }

  @Override public void showNocityError() {
    Snackbar.make(coordinatorLayout, R.string.sel_screen_nocityerror, Snackbar.LENGTH_SHORT).show();
    hideLoading();
  }

  private void showLoading() {
    btnSubmit.setEnabled(false);
    pbLoading.setVisibility(View.VISIBLE);
  }

  private void hideLoading() {
    btnSubmit.setEnabled(true);
    pbLoading.setVisibility(View.GONE);
  }

  class DaysInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
        int dend) {
      try {
        int input = Integer.parseInt(dest.toString() + source.toString());
        if (input <= 10 && input > 0) {
          return String.valueOf(input);
        }
      } catch (NumberFormatException e) {
      }
      return "";
    }
  }
}
