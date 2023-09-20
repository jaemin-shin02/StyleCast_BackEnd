package toyproject.stylecast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.stylecast.domain.Outfit;

import java.util.List;
import java.util.Optional;

public interface OutfitDataRepository extends JpaRepository<Outfit, Long>, OutfitDataRepositoryCustom{

    List<Outfit> findOutfitsByMember_Id(Long memberId);

}
