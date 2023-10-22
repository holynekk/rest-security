package com.holynekk.apisecurity.api.response.xss;

import com.holynekk.apisecurity.entity.XssArticle;

import java.util.List;

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
