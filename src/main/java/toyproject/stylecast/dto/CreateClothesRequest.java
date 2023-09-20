package toyproject.stylecast.dto;

import lombok.Data;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.Category;


@Data
public class CreateClothesRequest {
    private String name;
    private Category category;
    private String color;
    private Season season;
}
