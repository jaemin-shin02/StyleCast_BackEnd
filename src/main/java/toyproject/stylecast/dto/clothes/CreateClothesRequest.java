package toyproject.stylecast.dto.clothes;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
public class CreateClothesRequest {
    private Long memberId;
    private String name;
    private Category category;
    private String color;
    private Season season;
    private MultipartFile file;

    private Top top;
    private Pants pants;
    private Skirt skirt;
    private Onepiece onepiece;
    private Shoes shoes;
    private Outer outer;
}
