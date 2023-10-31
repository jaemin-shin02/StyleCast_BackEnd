package toyproject.stylecast.dto.member;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
