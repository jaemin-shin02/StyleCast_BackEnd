package toyproject.stylecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.Category;

@Data
@AllArgsConstructor
public class ClothesDto {
    private String name;
    private Category category;
    private String color;
    private Season season;
}
