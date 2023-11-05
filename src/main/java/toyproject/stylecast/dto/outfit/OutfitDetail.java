package toyproject.stylecast.dto.outfit;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.domain.recommendframe.Temperature;
import toyproject.stylecast.domain.recommendframe.Weather;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class OutfitDetail {
    private Long photoId;

    private String nickname;
    private String name;
    private String description; //코디 설명
    private Style style;

    private String top;
    private String bottom;
    private String outerwear;
    private String shoe;

    private Season season;

    private List<Weather> weatherList;

    private int likes;
}
