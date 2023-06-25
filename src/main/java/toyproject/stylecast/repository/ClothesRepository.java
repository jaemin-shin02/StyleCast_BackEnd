package toyproject.stylecast.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import toyproject.stylecast.domain.Clothes;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClothesRepository {
    private final EntityManager em;

    public void save(Clothes clothes){
        em.persist(clothes);
    }

    public Clothes findOne(Long id){
        return em.find(Clothes.class, id);
    }

    public List<Clothes> findAll(){
        return em.createQuery("select c from Clothes c", Clothes.class)
                .getResultList();
    }
}
