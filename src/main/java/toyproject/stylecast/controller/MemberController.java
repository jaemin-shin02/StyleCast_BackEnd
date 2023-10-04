package toyproject.stylecast.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.dto.*;
import toyproject.stylecast.service.MemberDataService;
import toyproject.stylecast.service.MemberService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberDataService memberDataService;

    @GetMapping("/api/v1/members")
    @ResponseBody
    public Result members() {
        List<Member> findMembers = memberDataService.findMembers();
        List<MemberDto> collectM = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getBirthdate(), m.getEmail(), m.getGrade(), m.getClothesList(), m.getOutfitList(), m.getLocationList()))
                .collect(Collectors.toList());
        List<ProfileDto> collectP = findMembers.stream()
                .map(m -> new ProfileDto(m.getName(), m.getProfile().getGender(), m.getProfile().getWeight(), m.getProfile().getHeight(), m.getProfile().getFigure(), m.getProfile().getWork_out(), m.getProfile().getPrefer_style()))
                .collect(Collectors.toList());

        return new Result(collectM.size() ,collectM, collectP);
    }

    @PostMapping("/api/members/su")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request){

        Member member = Member.creatMember(request.getName(), request.getNickname(), request.getBirthdate(), request.getEmail(), request.getPassword2());

        Long id = memberDataService.join(member);

        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/members/sa")
    public CreateMemberResponse saveAdmin(@RequestBody @Valid CreateMemberRequest request){

        Member member = Member.creatAdmin(request.getName(), request.getNickname(), request.getBirthdate(), request.getEmail(), request.getPassword2());

        Long id = memberDataService.join(member);

        return new CreateMemberResponse(id);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T date1;
        private T date2;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

}
