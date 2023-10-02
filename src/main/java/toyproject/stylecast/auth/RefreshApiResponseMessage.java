package toyproject.stylecast.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshApiResponseMessage {
    private String errorType;
    private String status;
    private String message;
    private String accessToken;

    // 맵 데이터를 이용한 생성자
    public RefreshApiResponseMessage(Map<String, String> map) {
        this.errorType = map.get("errorType");
        this.status = map.get("status");
        this.message = map.get("message");
        this.accessToken = map.get("accessToken");
    }

}
