package com.security.jpa.jwt.with_jpa;

public class AdminResponse {
	private final String jwt;
	
	

	public AdminResponse(String jwt) {
		super();
		this.jwt = jwt;
	}



	public String getJwt() {
		return jwt;
	}
	
	
}
