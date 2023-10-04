package toyproject.stylecast.dto;

import lombok.Data;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.domain.clothes.*;
import toyproject.stylecast.domain.recommendframe.Temperature;
import toyproject.stylecast.domain.recommendframe.Weather;

@Data
public class OutfitSearchCondition {
    private Profile profile;

    private Integer weightGoe;
    private Integer weightLoe;
    private Integer heightGoe;
    private Integer heightLoe;

    private Style style;

    private Category category;

    private Onepiece onepiece;
    private Outer outer;
    private Pants pants;
    private Shoes shoes;
    private Skirt skirt;
    private Top top;

    private Weather weather;
    private float temperature;
}
