package toyproject.stylecast.dto.outfit;

import lombok.Data;
import toyproject.stylecast.domain.recommendframe.Weather;

@Data
public class OutfitSearchBasic {
    private Weather weather;
    private Float temperature;
}
