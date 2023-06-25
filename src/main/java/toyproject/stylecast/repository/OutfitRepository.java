package toyproject.stylecast.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproject.stylecast.domain.Outfit;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutfitRepository {
    private final EntityManager em;

    public void save(Outfit outfit){
        em.persist(outfit);
    }

    public Outfit findOne(Long id){
        return em.find(Outfit.class, id);
    }

    public List<Outfit> findAll(){
        return em.createQuery("select o from Outfit o", Outfit.class).getResultList();
    }
}
