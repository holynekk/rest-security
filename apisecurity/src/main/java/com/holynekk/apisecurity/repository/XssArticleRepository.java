package com.holynekk.apisecurity.repository;

import com.holynekk.apisecurity.entity.XssArticle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XssArticleRepository extends CrudRepository<XssArticle, Integer> {
    public List<XssArticle> findByArticleContainsIgnoreCase(String article);
}
