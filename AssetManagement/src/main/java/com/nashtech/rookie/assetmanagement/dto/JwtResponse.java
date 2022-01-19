package com.nashtech.rookie.assetmanagement.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String jwttoken;
	private UserInfo user;

	public JwtResponse(String jwttoken) {
		setJwttoken(jwttoken);
	}


	public JwtResponse(String jwttoken, UserInfo user) {
		setJwttoken(jwttoken);
		setUser(user);
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}

}
