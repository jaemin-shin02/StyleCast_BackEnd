package toyproject.stylecast.dto.outfit;

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
    private Long id;
    private Long photoId;
    private String name;
    private String description; //코디 설명

    private Style style;

    private Long top_id;
    private Long bottom_id;
    private Long outerwear_id;
    private Long shoes_id;

    @QueryProjection
    public OutfitDto(Long id, Long photoId, String name, String description, Style style, Long top_id, Long bottom_id, Long outerwear_id, Long shoes_id) {
        this.id = id;
        this.photoId = photoId;
        this.name = name;
        this.description = description;
        this.style = style;
        this.top_id = top_id;
        this.bottom_id = bottom_id;
        this.outerwear_id = outerwear_id;
        this.shoes_id = shoes_id;
    }
}
