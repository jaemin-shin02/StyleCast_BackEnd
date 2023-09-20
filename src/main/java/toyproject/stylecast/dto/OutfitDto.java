package toyproject.stylecast.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Style;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class OutfitDto {
    private String name;
    private String description; //코디 설명

    private Style style;

    private Long top_id;
    private Long bottom_id;
    private Long outerwear_id;

    @QueryProjection
    public OutfitDto(String name, String description, Style style, Long top_id, Long bottom_id, Long outerwear_id) {
        this.name = name;
        this.description = description;
        this.style = style;
        this.top_id = top_id;
        this.bottom_id = bottom_id;
        this.outerwear_id = outerwear_id;
    }
}
