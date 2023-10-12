package toyproject.stylecast.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

@Data
public class CreateMemberRequest {
    private String name;
    private String nickname;
    private String birthdate;
    private String email;
    private String password1;
    private String password2;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        System.out.println("name = " + name);
        System.out.println("nickname = " + nickname);
        System.out.println("birthdate = " + birthdate);
        System.out.println("email = " + email);
        System.out.println("password1 = " + password1);
        System.out.println("password2 = " + password2);
        // 비밀번호1과 비밀번호2가 동일한지 검사
        return password1 != null && password1.equals(password2);
    }
}
