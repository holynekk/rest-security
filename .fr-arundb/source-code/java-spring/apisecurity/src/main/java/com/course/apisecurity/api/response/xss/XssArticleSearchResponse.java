package com.course.apisecurity.api.response.xss;

import java.util.List;

import com.course.apisecurity.entity.XssArticle;

public class XssArticleSearchResponse {

	private String queryCount;

	private List<XssArticle> result;

	public String getQueryCount() {
		return queryCount;
	}

	public void setQueryCount(String queryCount) {
		this.queryCount = queryCount;
	}

	public List<XssArticle> getResult() {
		return result;
	}

	public void setResult(List<XssArticle> result) {
		this.result = result;
	}

}
