package com.security.controller;

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

import com.security.model.AuthRequest;
import com.security.model.AuthResponse;
import com.security.services.MyUserDetailsServices;
import com.security.util.JwtUtil;

@RestController
public class BasicController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private MyUserDetailsServices userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/hi")
    public String hello() {
        return "hello";
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        
        // Load the user details from the service
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        
        // Generate JWT token using the user details
        final String jwt = jwtUtil.generateToken(userDetails);
        
        // Return the generated token wrapped in an AuthResponse object
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
