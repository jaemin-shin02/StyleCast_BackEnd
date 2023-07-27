package toyproject.stylecast.dto;

import lombok.Data;
import toyproject.stylecast.domain.Figure;
import toyproject.stylecast.domain.Gender;
import toyproject.stylecast.domain.Style;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateMemberProfileDto {

    private Gender gender;
    private int weight;
    private int height;
    private Figure figure;
    private Boolean work_out;

    @Size(max = 5, min = 1,message = "List size must be in the range of 1 to 5" )
    private List<Style> prefer_style = new ArrayList<>();
}
