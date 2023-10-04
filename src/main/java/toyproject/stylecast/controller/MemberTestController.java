package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.stylecast.auth.JwtService;
import toyproject.stylecast.auth.JwtTokenProvider;
import toyproject.stylecast.domain.*;
import toyproject.stylecast.dto.CreateMemberRequest;
import toyproject.stylecast.repository.MemberDataRepository;
import toyproject.stylecast.service.MemberDataService;

import javax.validation.Valid;
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

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
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



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        log.info("user email = {}", user.get("userEmail"));
        Member member = memberDataRepository.findMemberByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        // 비밀번호를 비교하여 로그인을 검증합니다.
        if (passwordEncoder.matches(user.get("password"), member.getPassword())) {
            Token tokenDto = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRoles());
            log.info("getRoles = {}", member.getRoles());
            jwtService.login(tokenDto);
            return ResponseEntity.ok(tokenDto); // 로그인 성공
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다."); // 로그인 실패
        }
    }

    @GetMapping("/check-login-status")
    public ResponseEntity<String> checkLoginStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 현재 사용자가 로그인 상태입니다.
            return ResponseEntity.ok("현재 로그인 중입니다.");
        } else {
            // 현재 사용자가 로그인되어 있지 않습니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되어 있지 않습니다.");
        }
    }

}
