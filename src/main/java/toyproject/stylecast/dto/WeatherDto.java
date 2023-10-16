package toyproject.stylecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherDto {
    private String main;
    private Float temp;
}
