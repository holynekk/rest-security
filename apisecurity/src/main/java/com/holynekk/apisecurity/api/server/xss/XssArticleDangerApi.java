package com.holynekk.apisecurity.api.server.xss;


import com.holynekk.apisecurity.api.response.xss.XssArticleSearchResponse;
import com.holynekk.apisecurity.entity.XssArticle;
import com.holynekk.apisecurity.repository.XssArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xss/danger/v1/article")
@CrossOrigin(origins = "http://localhost:3000")
public class XssArticleDangerApi {

    @Autowired
    private XssArticleRepository repository;

    @PostMapping(value = "")
    public String create(@RequestBody(required = true) XssArticle article) {
        XssArticle savedArticle = repository.save(article);

        return "Saved as " + savedArticle;
    }

    @GetMapping(value = "")
    public XssArticleSearchResponse search(@RequestParam(required = true) String query) {
        List<XssArticle> articles = repository.findByArticleContainsIgnoreCase(query);
        XssArticleSearchResponse response = new XssArticleSearchResponse();
        response.setResult(articles);

        if (articles.size() < 100) {
            response.setQueryCount("Search for " + query + " return <strong>" + articles.size() + "</strong> results.");
        } else {
            response.setQueryCount("Search for " + query + " return too many results. Use more specific keywords.");
        }

        return response;
    }
}
