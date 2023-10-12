package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import toyproject.stylecast.auth.JwtService;
import toyproject.stylecast.auth.JwtTokenProvider;
import toyproject.stylecast.auth.mail.MailService;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Token;
import toyproject.stylecast.dto.CreateMemberRequest;
import toyproject.stylecast.repository.MemberDataRepository;
import toyproject.stylecast.service.MemberDataService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDataService memberDataService;
    private final MemberDataRepository memberDataRepository;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    @GetMapping("/joinPage")
    public String joinPage(Model model, HttpSession session){
        // 세션에서 저장된 회원정보를 읽어 모델에 추가
        String name = (String) session.getAttribute("name");
        String nickname = (String) session.getAttribute("nickname");
        String birthdate = (String) session.getAttribute("birthdate");
        String email = (String) session.getAttribute("email");
        String password1 = (String) session.getAttribute("password1");
        String password2 = (String) session.getAttribute("password2");

        if (name != null) {
            model.addAttribute("name", name);
            model.addAttribute("nickname", nickname);
            model.addAttribute("birthdate", birthdate);
            model.addAttribute("email", email);
            model.addAttribute("password1", password1);
            model.addAttribute("password2", password2);
        }

        return "join";
    }

    @PostMapping("/joinPage/member")
    public String joinUser(@Valid CreateMemberRequest request){
        log.info("일반 회원가입 시도됨");
        System.out.println("request!!!!!! = " + request);

        Member member = Member.creatMember(request.getName(), request.getNickname(), request.getBirthdate(), request.getEmail(), request.getPassword2());

        memberDataService.join(member);

        return "redirect:/joinPage/success";
    }

    @PostMapping("/joinPage/admin")
    public String joinAdmin(@RequestBody @Valid CreateMemberRequest request){
        log.info("관리자 회원가입 시도됨");

        Member member = Member.creatAdmin(request.getName(), request.getNickname(), request.getBirthdate(), request.getEmail(), request.getPassword2());

        memberDataService.join(member);

        return member.toString();
    }

    @PostMapping("/joinPage/mail/send")
    public ResponseEntity<String> MailSend(String email, HttpSession session){
        // 인증 번호 생성 및 저장
        System.out.println("email = " + email);
        int verificationCode = mailService.sendMail(email);

        // 생성된 인증 번호를 문자열로 변환하여 세션에 저장
        session.setAttribute("verificationCode", String.valueOf(verificationCode));

        // 입력한 이메일을 세션에 저장
        session.setAttribute("email", email);

        return ResponseEntity.ok("Email submitted successfully");
    }

    @PostMapping("/joinPage/mail/verify")
    public ResponseEntity<String> verifyCode(@RequestParam("code") String enteredCode, HttpSession session) {
        // 세션에서 인증 번호와 이메일을 가져옵니다.
        String verificationCode = (String) session.getAttribute("verificationCode");
        String email = (String) session.getAttribute("email");

        if (verificationCode != null && enteredCode.equals(verificationCode)) {
            // 인증 성공 시 세션에서 이메일 정보 삭제
            session.removeAttribute("verificationCode");
            session.removeAttribute("email");
            log.info("인증 성공!");
            return ResponseEntity.ok("Email certification successfully");
        } else {
            log.info("인증 실패!");
            return ResponseEntity.ok("Email certification fail");
        }
    }

    @GetMapping("/joinPage/success")
    public String joinSuccess(){
        return "success";
    }
    @GetMapping("/joinPage/failure")
    public String joinFailure(){
        return "failure";
    }

    @GetMapping("/loginPage")
    public String LoginPage(Model model, HttpSession session){


        return "loginForm";
    }

    @PostMapping("/loginPage/verify")
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
}
