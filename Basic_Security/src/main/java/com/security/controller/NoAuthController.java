package com.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest")
public class NoAuthController {
	
	@GetMapping(path ="/get")
	public String sayHi() {
		return "Hi";
	}
}
