package toyproject.stylecast.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.clothes.Category;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClothesRepository {
    private final EntityManager em;

    public void save(Clothes clothes){
        em.persist(clothes);
    }

    public void remove(Clothes clothes){
        em.remove(clothes);
    }

    public Clothes findOne(Long id){
        return em.find(Clothes.class, id);
    }

    public List<Clothes> findAll(){
        return em.createQuery("select c from Clothes c", Clothes.class)
                .getResultList();
    }

    public List<Clothes> findClothesByMember(Long memberId){
        return em.createQuery("select c from Clothes c " +
                "where c.member.id =: memberId")
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<Clothes> findClothesByMemberWithCategory(Long memberId, Category category){
        return em.createQuery("select c from Clothes c " +
                        "where c.member.id =: memberId " +
                        "and c.category =: category")
                .setParameter("memberId", memberId)
                .setParameter("category", category)
                .getResultList();
    }

}
