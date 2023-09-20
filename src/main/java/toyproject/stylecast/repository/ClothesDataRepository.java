package toyproject.stylecast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.clothes.Category;

import java.util.List;
import java.util.Optional;

public interface ClothesDataRepository extends JpaRepository<Clothes, Long> {
    List<Clothes> findClothesByMemberId(Long memberId);

    List<Clothes> findClothesByMemberIdAndCategory(Long memberId, Category category);

}
