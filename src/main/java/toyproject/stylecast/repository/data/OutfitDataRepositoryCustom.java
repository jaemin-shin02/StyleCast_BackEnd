package toyproject.stylecast.repository.data;

import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.dto.outfit.*;

import java.util.List;

public interface OutfitDataRepositoryCustom {

    List<Outfit> ootdList();

    List<Outfit> RecommendOutfitDay(OutfitSearchMy condition);

    List<Outfit> RecommendOutfitBasic(OutfitSearchBasic condition);

    List<Outfit> RecommendOutfitByPersonalized(OutfitSearchPersonal condition);

    List<OutfitDto> RecommendOutfitBySimilarThing(OutfitSearchCondition condition);

}
