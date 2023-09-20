package toyproject.stylecast.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfile is a Querydsl query type for Profile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfile extends EntityPathBase<Profile> {

    private static final long serialVersionUID = -1779849680L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfile profile = new QProfile("profile");

    public final EnumPath<Figure> figure = createEnum("figure", Figure.class);

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final ListPath<Style, EnumPath<Style>> prefer_style = this.<Style, EnumPath<Style>>createList("prefer_style", Style.class, EnumPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public final BooleanPath work_out = createBoolean("work_out");

    public QProfile(String variable) {
        this(Profile.class, forVariable(variable), INITS);
    }

    public QProfile(Path<? extends Profile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfile(PathMetadata metadata, PathInits inits) {
        this(Profile.class, metadata, inits);
    }

    public QProfile(Class<? extends Profile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

