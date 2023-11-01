package toyproject.stylecast.repository.data;

import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Style;

import javax.persistence.EntityManager;
import java.util.List;

import static toyproject.stylecast.domain.QMember.member;

public class MemberDataRepositoryImpl implements MemberDataRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MemberDataRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Member> findByPreferStyle(Style style) {
        return queryFactory
                .selectFrom(member)
                .leftJoin(member.profile)
                .where(
                        member.profile.prefer_style.contains(style)
                )
                .fetch();
    }
}
