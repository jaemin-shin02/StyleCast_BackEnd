package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.stylecast.auth.JwtService;
import toyproject.stylecast.auth.JwtTokenProvider;
import toyproject.stylecast.auth.mail.MailService;
import toyproject.stylecast.domain.Member;
import toyproject.stylecast.domain.Profile;
import toyproject.stylecast.domain.Style;
import toyproject.stylecast.domain.Token;
import toyproject.stylecast.dto.member.CreateMemberProfileDto;
import toyproject.stylecast.dto.member.CreateMemberRequest;
import toyproject.stylecast.dto.member.MyPageDto;
import toyproject.stylecast.repository.data.MemberDataRepository;
import toyproject.stylecast.service.MemberDataService;
import toyproject.stylecast.service.ProfileService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDataService memberDataService;
    private final MemberDataRepository memberDataRepository;
    private final ProfileService profileService;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    @GetMapping("/join")
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

        Member member = Member.creatMember(request.getName(), request.getNickname(), request.getBirthdate(), request.getEmail(), request.getPassword2());

        memberDataService.join(member);
        memberDataService.addLocation(member.getId(),"서울시");

        return "redirect:/home";
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

    @GetMapping("/login")
    public String LoginPage(Model model, HttpSession session){

        return "loginForm";
    }

    @PostMapping("/loginPage/verify")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        Member member = memberDataRepository.findMemberByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        // 비밀번호를 비교하여 로그인을 검증합니다.
        if (passwordEncoder.matches(user.get("password"), member.getPassword())) {
            Token tokenDto = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRoles());
            log.info("getRoles = {}", member.getRoles());
            jwtService.login(tokenDto);
            log.info("tokenDto = {}", tokenDto);
            return ResponseEntity.ok(tokenDto); // 로그인 성공
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다."); // 로그인 실패
        }
    }

    @GetMapping("/logoutPage")
    public String LogoutPage(Model model, HttpSession session){
        log.info("로그아웃접근;");
        return "logoutForm";
    }

    @PostMapping("/logout/delete")
    public ResponseEntity<?> performLogout(@RequestBody Map<String, String> request) {
        String accessToken = request.get("accessToken");
        log.info("로그아웃 시도", accessToken);
        if(!accessToken.isEmpty()){
            String memberEmail = jwtTokenProvider.getUserPk(accessToken);
            jwtService.logout(memberEmail);
            return ResponseEntity.ok("로그아웃 성공"); // 로그아웃 성공
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그아웃 실패"); // 로그아웃 실패
        }
    }

    @GetMapping("/member/profile")
    public String showCreateProfilePage(Model model) {
        CreateMemberProfileDto profileForm = new CreateMemberProfileDto();
        model.addAttribute("profileForm", profileForm);

        // 스타일 목록을 모델에 추가
        List<String> styles = Arrays.asList("캐주얼", "시크", "댄디", "포멀", "걸리시", "레트로", "스포츠", "스트릿", "고프코어");
        model.addAttribute("styles", styles);

        return "/profile";
    }
    @PostMapping("/member/profile")
    public String serProfile(@Valid CreateMemberProfileDto request){
        Long memberId = getMemberId();
        Member member = memberDataService.findOne(memberId);
        Profile profile = Profile.creatProfile(member, request.getGender(), request.getWeight(), request.getHeight(), request.getFigure(), request.getWork_out());
        for (Style style : request.getPrefer_style()) {
            profile.addStyle(style);
        }
        Long profileId = profileService.save(profile);
        memberDataService.setProfile(memberId, profileId);

        return "redirect:/mypage";
    }

    @GetMapping( "/mypage")
    public String myPage(Model model){
        Long memberId = getMemberId();
        Member member = memberDataService.findOne(memberId);
        Profile profile = member.getProfile();

        MyPageDto myPageDto = new MyPageDto(member.getNickname(), member.getName(), member.getBirthdate(), member.getEmail(),
                profile.getGender(), profile.getWeight(), profile.getHeight(), profile.getFigure(), profile.getWork_out(), profile.getPrefer_style());

        model.addAttribute("myPageDto", myPageDto);

        return "/myPage";
    }

    @GetMapping("/getMemberId")
    public ResponseEntity<Long> getMemberId(@RequestHeader("Authorization") String authorizationHeader) {
        // "Bearer [AccessToken]" 형식의 Authorization 헤더에서 AccessToken 추출
        String accessToken = authorizationHeader.replace("Bearer ", "");
        log.info("엑토확인", accessToken);
        // AccessToken을 검증하고 로그인된 회원의 ID 가져오기
        String email = jwtTokenProvider.getUserPk(accessToken);
        Member member = memberDataService.findByEmail(email);
        Long memberId = member.getId();
        log.info(email, memberId);

        if (memberId != null) {
            return ResponseEntity.ok(memberId);
        } else {
            // AccessToken이 유효하지 않은 경우 또는 회원 ID를 가져오지 못한 경우
            return ResponseEntity.badRequest().build();
        }
    }

    private Long getMemberId() {
        // Spring Security를 사용하여 현재 사용자의 Authentication 객체를 얻어옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Authentication 객체에서 사용자 이름 또는 기타 정보를 추출
        String memberEmail = authentication.getName();
        Member member = memberDataService.findByEmail(memberEmail);
        Long memberId = member.getId();
        return memberId;
    }

}
