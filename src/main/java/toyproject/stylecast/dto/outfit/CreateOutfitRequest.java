package toyproject.stylecast.dto.outfit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Style;

@Data
@AllArgsConstructor
public class CreateOutfitRequest {
    private Long memberId;
    private String name;
    private String description; //코디 설명

    private Style style;

    //악세서리는 추후 추가 예정
    private Long top_id;
    private Long bottom_id;
    private Long outerwear_id;
    private Long shoes_id;

    private MultipartFile file;
}
