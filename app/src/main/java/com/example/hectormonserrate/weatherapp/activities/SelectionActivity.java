package com.example.hectormonserrate.weatherapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.hectormonserrate.weatherapp.R;
import com.example.hectormonserrate.weatherapp.dto.Autocomplete;
import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.example.hectormonserrate.weatherapp.presenter.SelectionPresenter;
import java.util.List;

public class SelectionActivity extends AppCompatActivity implements SelectionPresenter.Callback {

  @BindView(R.id.btnSubmit) Button btnSubmit;
  @BindView(R.id.etCity) EditText etCity;
  @BindView(R.id.etDays) EditText etDays;

  private SelectionPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_selection);

    ButterKnife.bind(this);

    presenter = new SelectionPresenter();
  }

  @OnClick(R.id.btnSubmit) public void onSubmit(View view) {
    String name = etCity.getText().toString();

    if (name.isEmpty()) {
      Toast.makeText(this, R.string.sel_screen_nocityvalidation, Toast.LENGTH_SHORT).show();
      return;
    }

    btnSubmit.setEnabled(false);
    presenter.resultsForQuery(etCity.getText().toString());
  }

  @Override protected void onResume() {
    super.onResume();
    btnSubmit.setEnabled(true);
    presenter.onAttach(this);
  }

  @Override protected void onPause() {
    presenter.onDetach();
    super.onPause();
  }

  @Override public void results(Pair<Autocomplete, List<Forecast>> results) {
    btnSubmit.setEnabled(true);

    WeatherActivity.launch(this, results.first, results.second);
  }

  @Override public void showNetworkError() {
    Toast.makeText(this, R.string.sel_screen_networkerror, Toast.LENGTH_SHORT).show();
    btnSubmit.setEnabled(true);
  }

  @Override public void showNocityError() {
    Toast.makeText(this, R.string.sel_screen_nocityerror, Toast.LENGTH_SHORT).show();
    btnSubmit.setEnabled(true);
  }

  @Override public Context getContext() {
    return this;
  }
}
