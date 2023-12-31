package toyproject.stylecast.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.domain.Style;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutfitRepository {
    private final EntityManager em;

    public void save(Outfit outfit){
        em.persist(outfit);
    }

    public void remove(Outfit outfit){
        em.remove(outfit);
    }

    public Outfit findOne(Long id){
        return em.find(Outfit.class, id);
    }

    public List<Outfit> findAll(){
        return em.createQuery("select o from Outfit o", Outfit.class).getResultList();
    }

    public List<Outfit> findAllByMemberId(Long memberId){
        return em.createQuery("select o from Outfit o " +
                "where o.member.id =: memberId")
                .setParameter("memberId", memberId)
                .getResultList();
    }

    //회원의 Profile과 원하는 Style을 받은 뒤 체형이 비슷한 원하는 스타일의 outfit을 추천하기 위해 검색
    public List<Outfit> RecommendOutfitV1(Profile profile, Style style){

        int targetWeight = profile.getWeight();
        int targetHeight = profile.getHeight();
        int errorRange = 2; // +- 2로 설정
        int minWeight = targetWeight - errorRange;
        int maxWeight = targetWeight + errorRange;
        int minHeight = targetHeight - errorRange;
        int maxHeight = targetHeight + errorRange;

        return em.createQuery("select o from Outfit o " +
                "where o.member.profile.weight BETWEEN :minWeight AND :maxWeight " +
                "AND o.member.profile.height BETWEEN :minHeight AND :maxHeight " +
                "AND o.member.profile.figure = :figure " +
                "AND o.member.profile.work_out = :work_out " +
                "AND :targetStyle MEMBER OF o.member.profile.prefer_style", Outfit.class)
                .setParameter("minWeight", minWeight)
                .setParameter("maxWeight", maxWeight)
                .setParameter("minHeight", minHeight)
                .setParameter("maxHeight", maxHeight)
                .setParameter("figure", profile.getFigure())
                .setParameter("work_out", profile.getWork_out())
                .setParameter("targetStyle", style)
                .getResultList();
    }

}
