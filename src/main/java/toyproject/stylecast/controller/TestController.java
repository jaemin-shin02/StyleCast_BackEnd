package toyproject.stylecast.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public String test(){
        return "<h1>test 통과</h1>";
    }

    @PostMapping("/user/test")
    public String userTest(){
        return "<h1>user Test 통과</h1>";
    }

}
