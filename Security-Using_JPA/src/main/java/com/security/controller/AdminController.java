package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.User;
import com.security.repository.UserRepository;

//Admin can only add the user

@RestController
@RequestMapping(path = "/secure/test")
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@PreAuthorize("hasAnyRole('admin')")
	@PostMapping(path = "/admin/add")
	public String addUserByAdmin(@RequestBody User user) {
		String pass = user.getPassword();
		String ecriptPass = passwordEncoder.encode(pass);

		user.setPassword(ecriptPass);
		userRepository.save(user);
		return "user add successfully";
	}
	
	@GetMapping(path = "/get")
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
}
