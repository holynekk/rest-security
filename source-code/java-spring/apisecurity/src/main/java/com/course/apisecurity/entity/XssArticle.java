package com.course.apisecurity.entity;

import org.springframework.data.annotation.Id;

public class XssArticle {

	@Id
	private long articleId;

	private String article;

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

}
