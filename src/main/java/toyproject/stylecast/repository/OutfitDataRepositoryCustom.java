package toyproject.stylecast.repository;

import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.domain.clothes.*;
import toyproject.stylecast.domain.recommendframe.Temperature;
import toyproject.stylecast.domain.recommendframe.Weather;
import toyproject.stylecast.dto.OutfitDto;
import toyproject.stylecast.dto.OutfitSearchCondition;

import java.util.List;

public interface OutfitDataRepositoryCustom {
    List<OutfitDto> RecommendOutfit(OutfitSearchCondition condition);

}
