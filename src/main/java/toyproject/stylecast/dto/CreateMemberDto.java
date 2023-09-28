package toyproject.stylecast.dto;

import lombok.Data;

@Data
public class CreateMemberDto {
    private String name;
    private String nickname;
    private String birth_date;
    private String password1;
    private String password2;
    private String email;
}
