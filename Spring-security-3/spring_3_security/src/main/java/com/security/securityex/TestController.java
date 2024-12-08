package com.security.securityex;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello...........";
    }

    @GetMapping("/admin/hello")
    public String adminHello(){
        return "Hello admin...........";
    }

    @GetMapping("/hi")
    public String commonHello(){
        return "Hi any authenticated...!";
    }


}
