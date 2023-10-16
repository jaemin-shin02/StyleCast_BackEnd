package toyproject.stylecast.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOutfit is a Querydsl query type for Outfit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOutfit extends EntityPathBase<Outfit> {

    private static final long serialVersionUID = 2133633180L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOutfit outfit = new QOutfit("outfit");

    public final BooleanPath bookmark = createBoolean("bookmark");

    public final NumberPath<Long> bottom_id = createNumber("bottom_id", Long.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final QMember member;

    public final StringPath name = createString("name");

    public final NumberPath<Long> outerwear_id = createNumber("outerwear_id", Long.class);

    public final EnumPath<Season> season = createEnum("season", Season.class);

    public final NumberPath<Long> shoe_id = createNumber("shoe_id", Long.class);

    public final EnumPath<Style> style = createEnum("style", Style.class);

    public final EnumPath<toyproject.stylecast.domain.recommendframe.Temperature> temperature = createEnum("temperature", toyproject.stylecast.domain.recommendframe.Temperature.class);

    public final NumberPath<Long> top_id = createNumber("top_id", Long.class);

    public final ListPath<toyproject.stylecast.domain.recommendframe.Weather, EnumPath<toyproject.stylecast.domain.recommendframe.Weather>> weatherList = this.<toyproject.stylecast.domain.recommendframe.Weather, EnumPath<toyproject.stylecast.domain.recommendframe.Weather>>createList("weatherList", toyproject.stylecast.domain.recommendframe.Weather.class, EnumPath.class, PathInits.DIRECT2);

    public QOutfit(String variable) {
        this(Outfit.class, forVariable(variable), INITS);
    }

    public QOutfit(Path<? extends Outfit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOutfit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOutfit(PathMetadata metadata, PathInits inits) {
        this(Outfit.class, metadata, inits);
    }

    public QOutfit(Class<? extends Outfit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

