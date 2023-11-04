package toyproject.stylecast.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import toyproject.stylecast.domain.Figure;
import toyproject.stylecast.domain.Gender;
import toyproject.stylecast.domain.Style;

import java.util.List;

@Data
@AllArgsConstructor
public class MyPageDto {
    //member
    private String nickname;
    private String name;
    private String birthdate;
    private String email;

    //profile
    private Gender gender;
    private int weight;
    private int height;
    private Figure figure;
    private Boolean work_out;
    private List<Style> prefer_style;
}
