package com.course.apisecurity.entity;

import org.springframework.data.annotation.Id;

public class BasicAclUri {

	@Id
	private int uriId;

	private String uri;

	private String method;

	public int getUriId() {
		return uriId;
	}

	public void setUriId(int uriId) {
		this.uriId = uriId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
