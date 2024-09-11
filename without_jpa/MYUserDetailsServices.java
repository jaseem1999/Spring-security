package com.security.jwt.without_jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.jwt.without_jpa.model.UserReq;

@Service
public class MYUserDetailsServices implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserReq> users = ListOfUsers.getAllUsers();
        UserReq foundUser = users.stream()
                                 .filter(user -> user.getUsername().equals(username))
                                 .findFirst()
                                 .orElse(null);
        
        if (foundUser != null) {
            return new User(foundUser.getUsername(), foundUser.getPassword(), new ArrayList<>()); // Add authorities if needed
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
