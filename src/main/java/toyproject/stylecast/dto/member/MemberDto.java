package toyproject.stylecast.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.stylecast.domain.Clothes;
import toyproject.stylecast.domain.Grade;
import toyproject.stylecast.domain.Outfit;
import toyproject.stylecast.domain.Profile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MemberDto {
    private String name;
    private String birth_date;
    private String email;
    private Grade grade;
    private List<Clothes> clothesList = new ArrayList<>();
    private List<Outfit> outfitList = new ArrayList<>();
    private List<String> locationList = new ArrayList<>();
}
