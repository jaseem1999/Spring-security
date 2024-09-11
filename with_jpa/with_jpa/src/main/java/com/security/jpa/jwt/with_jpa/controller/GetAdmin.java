package com.security.jpa.jwt.with_jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jpa.jwt.with_jpa.Admin;
import com.security.jpa.jwt.with_jpa.AdminRepository;


@RestController
@RequestMapping("/secure")
public class GetAdmin {
	
	@Autowired
	private AdminRepository adminRepo;
	
	@GetMapping("/get")
	public List<Admin> getAllAdmins(){
		return adminRepo.findAll();
	}
	
	
}
