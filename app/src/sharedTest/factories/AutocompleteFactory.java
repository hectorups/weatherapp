package factories;

import com.example.hectormonserrate.weatherapp.dto.Autocomplete;
import com.example.hectormonserrate.weatherapp.dto.AutocompleteRes;
import java.util.Collections;

public class AutocompleteFactory {

  public static AutocompleteRes queryResponse() {
    return AutocompleteRes.builder().setResults(Collections.singletonList(autocomplete())).build();
  }

  public static Autocomplete autocomplete() {
    return Autocomplete.builder().setLat("234243").setLng("98789").setName("Zaragoza").build();
  }
}
