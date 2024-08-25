package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.model.User;
import com.security.repository.UserRepository;

@Service
public class CostomeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepo.findByUsername(username);
		CustomeUserDetails cuserDetails = null;
		if(user != null) {
			cuserDetails = new CustomeUserDetails();
			cuserDetails.setUser(user);
		}else {
			throw new UsernameNotFoundException("user not exist :: "+username);
		}
		return cuserDetails;
	}
	
}
