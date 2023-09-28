package toyproject.stylecast.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.dto.*;
import toyproject.stylecast.repository.MemberDataRepository;
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
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collectM = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getBirthdate(), m.getEmail(), m.getGrade(), m.getClothesList(), m.getOutfitList(), m.getLocationList()))
                .collect(Collectors.toList());
        List<ProfileDto> collectP = findMembers.stream()
                .map(m -> new ProfileDto(m.getName(), m.getProfile().getGender(), m.getProfile().getWeight(), m.getProfile().getHeight(), m.getProfile().getFigure(), m.getProfile().getWork_out(), m.getProfile().getPrefer_style()))
                .collect(Collectors.toList());

        return new Result(collectM.size() ,collectM, collectP);
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request){

        Member member = Member.creatMember(request.getNewMember().getName(), request.getNewMember().getNickname(), request.getNewMember().getBirth_date(), request.getNewMember().getEmail(), request.getNewMember().getPassword2());
        Profile profile = Profile.creatProfile(member, request.getNewProfile().getGender(), request.getNewProfile().getWeight(), request.getNewProfile().getHeight(), request.getNewProfile().getFigure(), request.getNewProfile().getWork_out());
        List<Style> prefer_style = request.getNewProfile().getPrefer_style();
        for (Style style : prefer_style) {
            profile.addStyle(style);
        }

        Long id = memberService.join(member, profile);

        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveAdmin(@RequestBody @Valid CreateMemberRequest request){

        Member member = Member.creatAdmin(request.getNewMember().getName(), request.getNewMember().getNickname(), request.getNewMember().getBirth_date(), request.getNewMember().getEmail(), request.getNewMember().getPassword2());
        Profile profile = Profile.creatProfile(member, request.getNewProfile().getGender(), request.getNewProfile().getWeight(), request.getNewProfile().getHeight(), request.getNewProfile().getFigure(), request.getNewProfile().getWork_out());
        List<Style> prefer_style = request.getNewProfile().getPrefer_style();
        for (Style style : prefer_style) {
            profile.addStyle(style);
        }

        Long id = memberService.join(member, profile);

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
