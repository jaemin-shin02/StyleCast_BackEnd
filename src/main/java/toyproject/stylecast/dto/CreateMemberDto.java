package toyproject.stylecast.dto;

import lombok.Data;

@Data
public class CreateMemberDto {
    private String name;
    private String birth_date;
    private String email;
    private String password;
}
