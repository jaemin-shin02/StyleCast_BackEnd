package toyproject.stylecast.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}
