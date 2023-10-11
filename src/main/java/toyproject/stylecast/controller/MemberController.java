package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import toyproject.stylecast.dto.CreateMemberRequest;
import toyproject.stylecast.repository.MemberDataRepository;
import toyproject.stylecast.service.MemberDataService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
        System.out.println("request = " + request);

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
    public String MailSend(String email, HttpSession session){
        // 인증 번호 생성 및 저장
        System.out.println("email = " + email);
        int verificationCode = mailService.sendMail(email);

        // 생성된 인증 번호를 문자열로 변환하여 세션에 저장
        session.setAttribute("verificationCode", String.valueOf(verificationCode));

        // 입력한 이메일을 세션에 저장
        session.setAttribute("email", email);

        return "redirect:/mail";
    }

    @PostMapping("/joinPage/mail/verify")
    public String verifyCode(@RequestParam("code") String enteredCode, HttpSession session) {
        // 세션에서 인증 번호와 이메일을 가져옵니다.
        String verificationCode = (String) session.getAttribute("verificationCode");
        String email = (String) session.getAttribute("email");

        if (verificationCode != null && enteredCode.equals(verificationCode)) {
            // 인증 성공 시 세션에서 이메일 정보 삭제
            session.removeAttribute("verificationCode");
            session.removeAttribute("email");
            return "redirect:/mail/success"; // 인증 성공 페이지로 리다이렉트
        } else {
            return "redirect:/mail/failure"; // 인증 실패 시 Mail 페이지로 다시 렌더링
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
}
