package toyproject.stylecast.repository.data;

import toyproject.stylecast.dto.outfit.OutfitDto;
import toyproject.stylecast.dto.outfit.OutfitSearchBasic;
import toyproject.stylecast.dto.outfit.OutfitSearchCondition;
import toyproject.stylecast.dto.outfit.OutfitSearchPersonal;

import java.util.List;

public interface OutfitDataRepositoryCustom {
    List<OutfitDto> RecommendOutfitBasic(OutfitSearchBasic condition);

    List<OutfitDto> RecommendOutfitByPersonalized(OutfitSearchPersonal condition);

    List<OutfitDto> RecommendOutfitBySimilarThing(OutfitSearchCondition condition);

}
