package com.course.apisecurity.api.request.auth.basic;

public class BasicAuthCreateUserRequest {

	private String username;

	private String password;

	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
