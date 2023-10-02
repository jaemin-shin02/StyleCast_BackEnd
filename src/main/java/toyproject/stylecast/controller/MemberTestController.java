package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.stylecast.auth.JwtService;
import toyproject.stylecast.auth.JwtTokenProvider;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.repository.MemberDataRepository;
import toyproject.stylecast.service.MemberDataService;

import java.util.Collections;
import java.util.Map;

@Slf4j
//@Controller
@RestController
@RequiredArgsConstructor
public class MemberTestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDataService memberDataService;
    private final MemberDataRepository memberDataRepository;

    private final JwtService jwtService;

    @PostMapping("/join")
    public String join(){
        log.info("로그인 시도됨");

        Member member = Member.creatMember("pye", "귀욤이","20021011", "pye.eun@naver.com", "dkgkf!");
        Profile profile = Profile.creatProfile(member, Gender.WOMEN, 46, 158, Figure.STANDARD, false);
        profile.addStyle(Style.스트릿);
        profile.addStyle(Style.걸리시);
        member.setRoles(Collections.singletonList("ROLE_USER"));
        memberDataService.join(member, profile);

        return member.toString();

    }

    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> user) {
//        log.info("user email = {}", user.get("email"));
//        Member member = memberDataRepository.findMemberByEmail(user.get("email"))
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//
//        return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
//    }
    @PostMapping("/login")
    public Token login(@RequestBody Map<String, String> user) {
        log.info("user email = {}", user.get("userEmail"));
        Member member = memberDataRepository.findMemberByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        Token tokenDto = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRoles());
        log.info("getRoles = {}", member.getRoles());
        jwtService.login(tokenDto);
        return tokenDto;
    }


//    @GetMapping("/login")
//    public String login() {
//        return "loginForm";
//    }

}
