package toyproject.stylecast.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.dto.member.CreateMemberProfileDto;
import toyproject.stylecast.dto.member.CreateMemberRequest;
import toyproject.stylecast.dto.member.LoginRequest;
import toyproject.stylecast.service.MemberDataService;
import toyproject.stylecast.service.ProfileService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
//@Controller
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberDataService memberDataService;
    private final ProfileService profileService;

    @PostMapping("/join/test")
    public String joinTest(){
        log.info("로그인 시도됨");

        Member member = Member.creatMember("pye", "귀욤이","20021011", "pye.eun@naver.com", "dkgkf!");

        memberDataService.join(member);

        return member.toString();
    }

    @PostMapping("/join/member")
    public String joinUser(@RequestBody @Valid CreateMemberRequest request){
        log.info("일반 회원가입 시도됨");

        Member member = Member.creatMember(request.getName(), request.getNickname(), request.getBirthdate(), request.getEmail(), request.getPassword2());

        memberDataService.join(member);

        return member.toString();
    }

    @PostMapping("/join/admin")
    public String joinAdmin(@RequestBody @Valid CreateMemberRequest request){
        log.info("관리자 회원가입 시도됨");

        Member member = Member.creatAdmin(request.getName(), request.getNickname(), request.getBirthdate(), request.getEmail(), request.getPassword2());

        memberDataService.join(member);

        return member.toString();
    }

    @PostMapping("/member/profile/{id}")
    public String setProfile(@RequestBody @Valid CreateMemberProfileDto request, @PathVariable("id") Long memberId){
        Member member = memberDataService.findOne(memberId);
        Profile profile = Profile.creatProfile(member, request.getGender(), request.getWeight(), request.getHeight(), request.getFigure(), request.getWork_out());
        profileService.save(profile);
        memberDataService.setProfile(memberId, profile.getId());

        return member.toString();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return memberDataService.login(request);
    }

    @GetMapping("/check-login-status")
    public ResponseEntity<String> checkLoginStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 현재 사용자가 로그인 상태입니다.
            // 현재 사용자의 권한(역할) 목록을 얻기
            List<String> userRoles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // userRoles에는 현재 사용자의 권한 목록이 들어 있습니다.

            return ResponseEntity.ok("현재 로그인 중이며 권한은: " + userRoles);
        } else {
            // 현재 사용자가 로그인되어 있지 않습니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되어 있지 않습니다.");
        }
    }

}
