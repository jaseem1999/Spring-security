package com.security.securityex.controller;

import com.security.securityex.dto.RequestUserDTO;
import com.security.securityex.jwt.JwtService;
import com.security.securityex.model.UserEntity;
import com.security.securityex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/save", consumes = {"application/json"})
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody RequestUserDTO user){
        Map<String,Object> response = new HashMap<>();
        Map<String,String> message = new HashMap<>();
        boolean created = userService.saveUser(user);
        if(!created){
            message.put("message","User not created");
            response.put("success",false);
            response.put("error",message);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        message.put("message","User Created");
        response.put("success",true);
        response.put("data",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/login",  consumes = {"application/json"})
    public ResponseEntity<Map<String, Object>> login(@RequestBody RequestUserDTO user) {
        System.out.println(user.getPassword());
        Map<String, Object> response = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        boolean authenticated = userService.verify(user);
        if(!authenticated){
            message.put("message","Login failed try again");
            response.put("success",false);
            response.put("error",message);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token=jwtService.generateToken(user.getEmail());
        response.put("success",true);
        message.put("message","Login successfully");
        message.put("token",token);
        response.put("data",message);
        return ResponseEntity.ok(response);
    }

}
