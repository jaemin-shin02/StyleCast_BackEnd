package toyproject.stylecast.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClothes is a Querydsl query type for Clothes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClothes extends EntityPathBase<Clothes> {

    private static final long serialVersionUID = -603854641L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClothes clothes = new QClothes("clothes");

    public final BooleanPath bookmark = createBoolean("bookmark");

    public final EnumPath<toyproject.stylecast.domain.clothes.Category> category = createEnum("category", toyproject.stylecast.domain.clothes.Category.class);

    public final StringPath color = createString("color");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath name = createString("name");

    public final EnumPath<toyproject.stylecast.domain.clothes.Onepiece> onepiece = createEnum("onepiece", toyproject.stylecast.domain.clothes.Onepiece.class);

    public final EnumPath<toyproject.stylecast.domain.clothes.Outer> outer = createEnum("outer", toyproject.stylecast.domain.clothes.Outer.class);

    public final EnumPath<toyproject.stylecast.domain.clothes.Pants> pants = createEnum("pants", toyproject.stylecast.domain.clothes.Pants.class);

    public final EnumPath<Season> season = createEnum("season", Season.class);

    public final EnumPath<toyproject.stylecast.domain.clothes.Shoes> shoes = createEnum("shoes", toyproject.stylecast.domain.clothes.Shoes.class);

    public final EnumPath<toyproject.stylecast.domain.clothes.Skirt> skirt = createEnum("skirt", toyproject.stylecast.domain.clothes.Skirt.class);

    public final EnumPath<toyproject.stylecast.domain.clothes.Top> top = createEnum("top", toyproject.stylecast.domain.clothes.Top.class);

    public QClothes(String variable) {
        this(Clothes.class, forVariable(variable), INITS);
    }

    public QClothes(Path<? extends Clothes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClothes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClothes(PathMetadata metadata, PathInits inits) {
        this(Clothes.class, metadata, inits);
    }

    public QClothes(Class<? extends Clothes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

