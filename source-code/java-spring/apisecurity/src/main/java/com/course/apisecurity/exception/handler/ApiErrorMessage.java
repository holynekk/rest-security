package com.course.apisecurity.exception.handler;

public class ApiErrorMessage {

	private String errorMessage;

	public ApiErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
