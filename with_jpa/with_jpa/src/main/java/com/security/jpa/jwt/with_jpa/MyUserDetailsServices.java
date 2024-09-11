package com.security.jpa.jwt.with_jpa;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsServices implements UserDetailsService {
	
	@Autowired
	private AdminRepository adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Admin admin= adminRepo.findByUsername(username);
		
		if (admin != null) {
            return new User(admin.getUsername(), admin.getPassword(), new ArrayList<>()); // Add authorities if needed
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
		
	}

}
