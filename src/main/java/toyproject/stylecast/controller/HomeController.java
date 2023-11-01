package toyproject.stylecast.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/home")
    public String home(Model model, HttpSession session){
        return "home";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/user/test")
    public String hello(){
        return "success";
    }

}
