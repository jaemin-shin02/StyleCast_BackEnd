package toyproject.stylecast.repository.data;

import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.dto.outfit.*;

import java.util.List;

public interface OutfitDataRepositoryCustom {

    List<Outfit> ootdList();

    List<Outfit> RecommendOutfitDay(OutfitSearchMy condition);

    List<OutfitDto> RecommendOutfitBasic(OutfitSearchBasic condition);

    List<OutfitDto> RecommendOutfitByPersonalized(OutfitSearchPersonal condition);

    List<OutfitDto> RecommendOutfitBySimilarThing(OutfitSearchCondition condition);

}
