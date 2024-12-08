package com.security.securityex.service;

import com.security.securityex.model.UserEntity;
import com.security.securityex.model.UserPrincipal;
import com.security.securityex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if(user == null){
            System.err.println("User not found...");
            throw new UsernameNotFoundException("User not found....");
        }
        new User(user.getEmail(),user.getPassword(),new ArrayList<>());
        return new UserPrincipal(user);

    }
}
