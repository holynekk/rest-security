package com.course.apisecurity.entity;

public class AuditLogEntry {

	private String timestamp;

	private String method;

	private String path;

	private String query;

	private String headers;

	private String requestBody;

	private int responseStatusCode;

	public String getHeaders() {
		return headers;
	}

	public String getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public String getQuery() {
		return query;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getResponseStatusCode() {
		return responseStatusCode;
	}

	public void setResponseStatusCode(int responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}

}
