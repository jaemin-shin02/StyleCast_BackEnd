package toyproject.stylecast.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
    private String name;
    private WeatherDetails main;
}
