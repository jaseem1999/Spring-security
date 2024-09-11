package com.security.jwt.without_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.without_jpa.MYUserDetailsServices;
import com.security.jwt.without_jpa.jwt.JwtCheck;
import com.security.jwt.without_jpa.model.UserReq;
import com.security.jwt.without_jpa.model.UserResponse;

@RestController
@RequestMapping("/api")
public class DemoController {
	
	@Autowired
	private MYUserDetailsServices userDetailsServices;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtCheck jwtToken;

	@GetMapping("/secure")
	public String sampleSecurityTest() {
		return "Security test passed";
	}
	
	@PostMapping("/auth")
	public ResponseEntity<?> userValidate(@RequestBody UserReq user) throws Exception{
//		System.out.println(user.getUsername());
		int flag = 0;
		try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            flag = 1;
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        
        final UserDetails userDetails = userDetailsServices.loadUserByUsername(user.getUsername());
        
        
        final String jwt = jwtToken.generateToken(userDetails);
           
        return ResponseEntity.ok(new UserResponse(jwt));
       
	}
	
	
}
