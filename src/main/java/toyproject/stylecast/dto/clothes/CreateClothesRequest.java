package toyproject.stylecast.dto.clothes;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import toyproject.stylecast.domain.Season;
import toyproject.stylecast.domain.clothes.Category;


@Data
public class CreateClothesRequest {
    private String name;
    private Category category;
    private String color;
    private Season season;
    private MultipartFile file;
}
