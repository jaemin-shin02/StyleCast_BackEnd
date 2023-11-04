package toyproject.stylecast.dto.outfit;

import lombok.Data;
import toyproject.stylecast.domain.recommendframe.Weather;

@Data
public class OutfitSearchMy {
    private Long memberId;
    private Weather weather;
    private Float temperature;
}
