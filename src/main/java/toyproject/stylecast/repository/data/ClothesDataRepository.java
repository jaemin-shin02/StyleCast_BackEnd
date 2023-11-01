package toyproject.stylecast.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.clothes.Category;

import java.util.List;

public interface ClothesDataRepository extends JpaRepository<Clothes, Long>, ClothesDataRepositoryCustom{
    List<Clothes> findClothesByMemberId(Long memberId);

    List<Clothes> findClothesByCategory(Category category);

    List<Clothes> findClothesByMemberIdAndCategory(Long memberId, Category category);

}
