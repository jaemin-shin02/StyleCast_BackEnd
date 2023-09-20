package toyproject.stylecast.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * toyproject.stylecast.dto.QOutfitDto is a Querydsl Projection type for OutfitDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOutfitDto extends ConstructorExpression<OutfitDto> {

    private static final long serialVersionUID = -1970551428L;

    public QOutfitDto(com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<toyproject.stylecast.domain.Style> style, com.querydsl.core.types.Expression<Long> top_id, com.querydsl.core.types.Expression<Long> bottom_id, com.querydsl.core.types.Expression<Long> outerwear_id) {
        super(OutfitDto.class, new Class<?>[]{String.class, String.class, toyproject.stylecast.domain.Style.class, long.class, long.class, long.class}, name, description, style, top_id, bottom_id, outerwear_id);
    }

}

