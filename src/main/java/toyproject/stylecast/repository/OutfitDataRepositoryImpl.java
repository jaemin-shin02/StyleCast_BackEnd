package toyproject.stylecast.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.clothes.*;
import toyproject.stylecast.domain.recommendframe.Temperature;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.OutfitDto;
import toyproject.stylecast.dto.OutfitSearchCondition;
import toyproject.stylecast.dto.QOutfitDto;

import javax.persistence.EntityManager;
import java.util.List;

import static toyproject.stylecast.domain.QMember.member;
import static toyproject.stylecast.domain.QOutfit.outfit;
import static toyproject.stylecast.domain.QProfile.*;

public class OutfitDataRepositoryImpl implements OutfitDataRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OutfitDataRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OutfitDto> RecommendOutfit(OutfitSearchCondition condition) {

        return queryFactory
                .select(new  QOutfitDto(
                        outfit.name,
                        outfit.description,
                        outfit.style,
                        outfit.top_id,
                        outfit.bottom_id,
                        outfit.outerwear_id
                ))
                .from(outfit)
                .leftJoin(outfit.member, member)
                .leftJoin(member.profile, profile)
                .where(
                        weightGoe(condition.getWeightGoe()),
                        weightLoe(condition.getWeightLoe()),
                        heightGoe(condition.getHeightGoe()),
                        heightLoe(condition.getHeightLoe()),
                        figureEq(condition.getProfile().getFigure()),
                        workOutEq(condition.getProfile().getWork_out()),
                        preferStyleIn(condition.getStyle()),
                        weatherEq(condition.getWeather()),
                        temperatureEq(condition.getTemperature())
                )
                .fetch();
    }

    private BooleanExpression weightGoe(Integer weightGoe) {
        return weightGoe != null ? outfit.member.profile.weight.goe(weightGoe) : null;
    }

    private BooleanExpression weightLoe(Integer weightLoe) {
        return weightLoe != null ? outfit.member.profile.weight.loe(weightLoe) : null;
    }

    private BooleanExpression heightGoe(Integer heightGoe) {
        return heightGoe != null ? outfit.member.profile.height.goe(heightGoe) : null;
    }

    private BooleanExpression heightLoe(Integer heightLoe) {
        return heightLoe != null ? outfit.member.profile.height.loe(heightLoe) : null;
    }

    private BooleanExpression figureEq(Figure figure) {
        return figure != null ? outfit.member.profile.figure.eq(figure) : null;
    }

    private BooleanExpression workOutEq(Boolean work_out) {
        return work_out != null ? outfit.member.profile.work_out.eq(work_out) : null;
    }

    private BooleanExpression preferStyleIn(Style style) {
        return style != null ? outfit.member.profile.prefer_style.contains(style) : null;
    }

    private BooleanExpression weatherEq(Weather weather) {
        return null;
    }

    private BooleanExpression temperatureEq(Temperature temperature) {
        return null;
    }


}
