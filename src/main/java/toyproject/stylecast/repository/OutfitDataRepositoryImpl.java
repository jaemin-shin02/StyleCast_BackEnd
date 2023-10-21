package toyproject.stylecast.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.domain.clothes.Outer;
import toyproject.stylecast.domain.clothes.Pants;
import toyproject.stylecast.domain.clothes.Skirt;
import toyproject.stylecast.domain.clothes.Top;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.OutfitDto;
import toyproject.stylecast.dto.OutfitSearchCondition;
import toyproject.stylecast.dto.QOutfitDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static toyproject.stylecast.domain.QMember.member;
import static toyproject.stylecast.domain.QOutfit.outfit;
import static toyproject.stylecast.domain.QProfile.*;

public class OutfitDataRepositoryImpl implements OutfitDataRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final ClothesDataRepository clothesDataRepository;

    public OutfitDataRepositoryImpl(EntityManager em, ClothesDataRepository clothesDataRepository) {
        this.queryFactory = new JPAQueryFactory(em);
        this.clothesDataRepository = clothesDataRepository;
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
                        genderEq(condition.getProfile().getGender()),
                        preferStyleIn(condition.getStyle()),
                        styleEq(condition.getStyle()),
                        weatherEq(condition.getWeather()),
                        temperatureCmp(condition.getTemperature()),
                        outfit.likes.goe(10)
                )
                .orderBy(outfit.likes.desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    private BooleanExpression weightGoe(Integer weightGoe) {
        return weightGoe != null ? outfit.member.profile.weight.goe(weightGoe) :  null;
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

    private BooleanExpression genderEq(Gender gender){
        return gender != null ? outfit.member.profile.gender.eq(gender) : null;
    }

    private BooleanExpression preferStyleIn(Style style) {
        return style != null ? outfit.member.profile.prefer_style.contains(style) : null;
    }

    private BooleanExpression styleEq(Style style) {
        return style != null ? outfit.style.eq(style) : null;
    }

    private BooleanExpression weatherEq(Weather weather) {
        return weather!= null ? outfit.weatherList.contains(weather) : null;
    }

    private BooleanExpression temperatureCmp(Float temperature) {
        if(temperature == null){
            return null;
        } else if (temperature > 28) {
            List<Long> topIdList = clothesDataRepository.SelectByTop(Top.반팔);
            List<Long> pantsIdList = clothesDataRepository.SelectByPants(Pants.숏팬츠);
            List<Long> skirtIdList = clothesDataRepository.SelectBySkirt(Skirt.미니스커트);

            BooleanExpression pantsOrSkirt = outfit.bottom_id.in(pantsIdList).or(outfit.bottom_id.in(skirtIdList));

            return outfit.top_id.in(topIdList).and(pantsOrSkirt);
        } else if (temperature > 23) {
            List<Long> topIdList = new ArrayList<>();
            topIdList.addAll(clothesDataRepository.SelectByTop(Top.반팔));
            topIdList.addAll(clothesDataRepository.SelectByTop(Top.셔츠));

            List<Long> pantsIdList = new ArrayList<>();
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.숏팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.코튼팬츠));

            List<Long> skirtIdList = new ArrayList<>();
            skirtIdList.addAll(clothesDataRepository.SelectBySkirt(Skirt.미니스커트));
            skirtIdList.addAll(clothesDataRepository.SelectBySkirt(Skirt.미디스커트));

            BooleanExpression pantsOrSkirt = outfit.bottom_id.in(pantsIdList).or(outfit.bottom_id.in(skirtIdList));

            return outfit.top_id.in(topIdList).and(pantsOrSkirt);
        } else if (temperature > 20) {
            List<Long> topIdList = new ArrayList<>();
            topIdList.addAll(clothesDataRepository.SelectByTop(Top.긴팔));
            topIdList.addAll(clothesDataRepository.SelectByTop(Top.셔츠));
            topIdList.addAll(clothesDataRepository.SelectByTop(Top.블라우스));

            List<Long> pantsIdList = new ArrayList<>();
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.나일론팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.데님팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.코튼팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.슬랙스));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.조거팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.트래이닝팬츠));

            List<Long> skirtIdList = new ArrayList<>();
            skirtIdList.addAll(clothesDataRepository.SelectBySkirt(Skirt.미니스커트));
            skirtIdList.addAll(clothesDataRepository.SelectBySkirt(Skirt.미디스커트));
            skirtIdList.addAll(clothesDataRepository.SelectBySkirt(Skirt.롱스커트));

            BooleanExpression pantsOrSkirt = outfit.bottom_id.in(pantsIdList).or(outfit.bottom_id.in(skirtIdList));

            return outfit.top_id.in(topIdList).and(pantsOrSkirt);
        } else if (temperature > 17) {
            List<Long> topIdList = new ArrayList<>();
            topIdList.addAll(clothesDataRepository.SelectByTop(Top.맨투맨));
            topIdList.addAll(clothesDataRepository.SelectByTop(Top.후드티));
            topIdList.addAll(clothesDataRepository.SelectByTop(Top.니트));

            List<Long> pantsIdList = new ArrayList<>();
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.나일론팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.데님팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.코튼팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.슬랙스));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.조거팬츠));
            pantsIdList.addAll(clothesDataRepository.SelectByPants(Pants.트래이닝팬츠));

            List<Long> skirtIdList = new ArrayList<>();
            skirtIdList.addAll(clothesDataRepository.SelectBySkirt(Skirt.롱스커트));

            List<Long> outerIdList = clothesDataRepository.SelectByOuter(Outer.후드집업);
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.트래이닝재킷));

            BooleanExpression pantsOrSkirt = outfit.bottom_id.in(pantsIdList).or(outfit.bottom_id.in(skirtIdList));
            return outfit.top_id.in(topIdList).or(outfit.outerwear_id.in(outerIdList)).and(pantsOrSkirt);
        } else if (temperature > 12) {
            List<Long> outerIdList = new ArrayList<>();
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.가디건));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.나일론재킷));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.래더재킷));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.베스트));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.블레이저));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.뽀글이));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.스타디움재킷));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.헌팅재킷));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.블루종));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.트러커));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.항공점퍼));
            System.out.println("outerIdList = " + outerIdList);
            return outfit.outerwear_id.in(outerIdList);
        } else if (temperature > 9) {
            List<Long> outerIdList = new ArrayList<>();
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.항공점퍼));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.코트));

            return outfit.outerwear_id.in(outerIdList);
        } else if (temperature > 5) {
            List<Long> outerIdList = new ArrayList<>();
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.항공점퍼));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.코트));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.퍼));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.무스탕));

            return outfit.outerwear_id.in(outerIdList);
        } else {
            List<Long> outerIdList = new ArrayList<>();
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.항공점퍼));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.코트));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.퍼));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.무스탕));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.롱패딩));
            outerIdList.addAll(clothesDataRepository.SelectByOuter(Outer.숏패딩));

            return outfit.outerwear_id.in(outerIdList);
        }
    }

}
