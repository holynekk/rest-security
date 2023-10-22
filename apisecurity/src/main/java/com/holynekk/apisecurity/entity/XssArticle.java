package com.holynekk.apisecurity.entity;

import javax.persistence.Id;

public class XssArticle {

    @Id
    private int articleId;

    private String article;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
