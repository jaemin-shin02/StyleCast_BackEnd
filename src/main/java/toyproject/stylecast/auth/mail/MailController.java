package toyproject.stylecast.auth.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

//    @GetMapping("/mail")
//    public String mailPage(Model model, HttpSession session){
//        // 세션에서 저장된 이메일을 읽어 모델에 추가
//        String email = (String) session.getAttribute("email");
//        if (email != null) {
//            model.addAttribute("email", email);
//        }
//        return "Mail";
//    }
//
//    @PostMapping("/mail/send")
//    public String MailSend(String email, HttpSession session){
//        // 인증 번호 생성 및 저장
//        System.out.println("email = " + email);
//        int verificationCode = mailService.sendMail(email);
//
//        // 생성된 인증 번호를 문자열로 변환하여 세션에 저장
//        session.setAttribute("verificationCode", String.valueOf(verificationCode));
//
//        // 입력한 이메일을 세션에 저장
//        session.setAttribute("email", email);
//
//        return "redirect:/mail";
//    }
//
//    @PostMapping("/mail/verify")
//    public String verifyCode(@RequestParam("code") String enteredCode, HttpSession session) {
//        // 세션에서 인증 번호와 이메일을 가져옵니다.
//        String verificationCode = (String) session.getAttribute("verificationCode");
//        String email = (String) session.getAttribute("email");
//
//        if (verificationCode != null && enteredCode.equals(verificationCode)) {
//            // 인증 성공 시 세션에서 이메일 정보 삭제
//            session.removeAttribute("verificationCode");
//            session.removeAttribute("email");
//            return "redirect:/mail"; // 인증 성공 페이지로 리다이렉트
//        } else {
//            return "loginForm"; // 인증 실패 페이지로 리다이렉트
//        }
//    }

    @GetMapping("/mail")
    public String mailPage(Model model, HttpSession session){
        // 세션에서 저장된 이메일을 읽어 모델에 추가
        String email = (String) session.getAttribute("email");
        if (email != null) {
            model.addAttribute("email", email);
        }

        return "Mail";
    }

    @PostMapping("/mail/send")
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

    @PostMapping("/mail/verify")
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

    @GetMapping("/mail/success")
    public String mailSuccess(){
        return "success";
    }
    @GetMapping("/mail/failure")
    public String mailFailure(){
        return "failure";
    }

}
