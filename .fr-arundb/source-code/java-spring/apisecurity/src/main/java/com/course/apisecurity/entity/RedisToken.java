package com.course.apisecurity.entity;

public class RedisToken {

	private String username;

	private String dummyAttribute;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDummyAttribute() {
		return dummyAttribute;
	}

	public void setDummyAttribute(String dummyAttribute) {
		this.dummyAttribute = dummyAttribute;
	}

}
