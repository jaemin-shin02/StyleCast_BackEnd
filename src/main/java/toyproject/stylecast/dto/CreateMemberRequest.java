package toyproject.stylecast.dto;

import lombok.Data;

import javax.validation.Valid;

@Data
public class CreateMemberRequest {
    @Valid
    private CreateMemberDto newMember;
    @Valid
    private CreateMemberProfileDto newProfile;
}
