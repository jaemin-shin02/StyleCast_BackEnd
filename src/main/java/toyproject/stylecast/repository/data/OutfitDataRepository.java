package toyproject.stylecast.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.Style;

import java.util.List;

public interface OutfitDataRepository extends JpaRepository<Outfit, Long>, OutfitDataRepositoryCustom{
    List<Outfit> findOutfitsByMember_Id(Long memberId);
    List<Outfit> findOutfitByMemberIdAndStyle(Long memberId, Style style);
}
