package toyproject.stylecast.dto.outfit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OotdDto {
    private Long id;

    private Long photoId;

    private String nickname;
    private String name;

    private int likes;
}
