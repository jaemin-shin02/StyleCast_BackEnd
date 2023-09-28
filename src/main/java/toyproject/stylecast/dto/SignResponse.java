package toyproject.stylecast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.stylecast.domain.Grade;
import toyproject.stylecast.domain.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse {

    private Long id;

    private String email;

    private String birthdate;

    private String name;

    private Grade grade;

    public SignResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.birthdate = member.getBirthdate();
        this.name = member.getName();
        this.grade = member.getGrade();
    }
}