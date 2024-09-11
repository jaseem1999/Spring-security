package com.security.jwt.without_jpa;

import java.util.ArrayList;
import java.util.List;

import com.security.jwt.without_jpa.model.UserReq;

public class ListOfUsers {
	
	
	public static List<UserReq> getAllUsers() {
		List<UserReq> listOfUsers = new ArrayList<UserReq>();

		listOfUsers.add(new UserReq("jaseem", "1234#"));
		listOfUsers.add(new UserReq("ADIL","1wE4#"));
		listOfUsers.add(new UserReq("Deve", "132P"));
		
		
		return listOfUsers;
	}
}
