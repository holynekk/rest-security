package com.course.apisecurity.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

public class BasicAuthUser {

	@Id
	private int userId;

	private String username;

	private String passwordHash;

	private String salt;

	private String displayName;

	@MappedCollection(idColumn = "user_id")
	private Set<BasicAclUserUriRef> allowedUris;

	public String getDisplayName() {
		return displayName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getSalt() {
		return salt;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<BasicAclUserUriRef> getAllowedUris() {
		return allowedUris;
	}

	public void setAllowedUris(Set<BasicAclUserUriRef> allowedUris) {
		this.allowedUris = allowedUris;
	}

}
