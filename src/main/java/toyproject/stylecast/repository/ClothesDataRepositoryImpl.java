package toyproject.stylecast.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.stylecast.domain.clothes.*;

import javax.persistence.EntityManager;
import java.util.List;

import static toyproject.stylecast.domain.QClothes.clothes;


public class ClothesDataRepositoryImpl implements ClothesDataRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ClothesDataRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> SelectByTop(Top top) {
        return queryFactory
                .select(clothes.id)
                .from(clothes)
                .where(clothes.top.eq(top))
                .fetch();
    }

    @Override
    public List<Long> SelectByPants(Pants pants) {
        return queryFactory
                .select(clothes.id)
                .from(clothes)
                .where(clothes.pants.eq(pants))
                .fetch();
    }

    @Override
    public List<Long> SelectBySkirt(Skirt skirt) {
        return queryFactory
                .select(clothes.id)
                .from(clothes)
                .where(clothes.skirt.eq(skirt))
                .fetch();
    }

    @Override
    public List<Long> SelectByOnepiece(Onepiece onepiece) {
        return queryFactory
                .select(clothes.id)
                .from(clothes)
                .where(clothes.onepiece.eq(onepiece))
                .fetch();
    }

    @Override
    public List<Long> SelectByShoes(Shoes shoes) {
        return queryFactory
                .select(clothes.id)
                .from(clothes)
                .where(clothes.shoes.eq(shoes))
                .fetch();
    }

    @Override
    public List<Long> SelectByOuter(Outer outer) {
        return queryFactory
                .select(clothes.id)
                .from(clothes)
                .where(clothes.outer.eq(outer))
                .fetch();
    }
}
