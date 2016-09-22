package factories;

import com.example.hectormonserrate.weatherapp.dto.Forecast;
import com.example.hectormonserrate.weatherapp.dto.ForecastInfo;
import com.example.hectormonserrate.weatherapp.dto.ForecastRes;
import com.example.hectormonserrate.weatherapp.dto.Simpleforecas;
import com.example.hectormonserrate.weatherapp.dto.WondergroundDate;
import com.example.hectormonserrate.weatherapp.dto.WondergroundTemp;
import java.util.Collections;

public class ForecastFactory {
  public static ForecastRes forecast10Days() {
    return ForecastRes.builder()
        .setForecastInfo(ForecastInfo.builder()
            .setSimpleforecast(
                Simpleforecas.builder().setForecasts(Collections.singletonList(forecast())).build())
            .build())
        .build();
  }

  public static Forecast forecast() {
    return Forecast.builder()
        .setConditions("cloudy")
        .setDate(WondergroundDate.builder().setEpoch("1474296879").build())
        .setHigh(WondergroundTemp.builder().setCelsius("19").build())
        .setLow(WondergroundTemp.builder().setCelsius("10").build())
        .setIcon("sunny.jpg")
        .build();
  }
}