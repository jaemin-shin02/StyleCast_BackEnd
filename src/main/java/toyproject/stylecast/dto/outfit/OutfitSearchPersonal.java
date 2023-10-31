package toyproject.stylecast.dto.outfit;

import lombok.Data;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.domain.Style;

@Data
public class OutfitSearchPersonal {
    private Profile profile;

    private Integer weightGoe;
    private Integer weightLoe;
    private Integer heightGoe;
    private Integer heightLoe;

    private Style style;
}
