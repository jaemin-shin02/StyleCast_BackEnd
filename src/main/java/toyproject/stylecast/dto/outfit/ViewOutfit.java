package toyproject.stylecast.dto.outfit;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.stylecast.domain.Style;

@Data
@AllArgsConstructor
public class ViewOutfit {
    private String name;
    private String description; //코디 설명

    private Style style;

    private String top;
    private String bottom;
    private String outerwear;
    private String shoe;
}
