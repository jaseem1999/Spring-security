package com.security.jwt.without_jpa.model;

public class UserResponse {
	private final String jwt;

	public UserResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
	
	
	
}
