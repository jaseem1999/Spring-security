package com.security.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class PasswordController {

    private final BCryptPasswordEncoder passEn;

    @Autowired
    public PasswordController(BCryptPasswordEncoder passEn) {
        this.passEn = passEn;
    }
    
    @PostMapping(path = "/check")
    public String loginVerify(@RequestParam("pass") String pass, @RequestParam("verify") String verify) {
        String encodedPass = passEn.encode(pass);
        if (passEn.matches(verify, encodedPass)) {
            return "Yes";
        }
        
        return "No";
    }
}
