package com.security.securityex.service;

import com.security.securityex.dto.RequestUserDTO;
import com.security.securityex.model.UserEntity;
import com.security.securityex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public boolean saveUser(RequestUserDTO userReq){
        if(userReq == null){
            return false;
        }

        UserEntity user = new UserEntity();
        user.setEmail(userReq.getEmail());
        user.setPassword(encoder.encode(userReq.getPassword()));

        try {
            userRepository.save(user);
            return true;
        }catch (Exception e) {
            throw new RuntimeException("Inter Server error");
        }
    }

    public boolean verify(RequestUserDTO user) {
        Authentication authenticate =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        if(authenticate.isAuthenticated()){
            return true;
        }
        return false;
    }
}
