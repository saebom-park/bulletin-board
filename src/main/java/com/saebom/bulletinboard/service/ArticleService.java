package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Article;

import java.util.List;

public interface ArticleService {

    Long createArticle(Long memberId, String title, String content);

    Article getArticle(Long id);
    List<Article> getArticles();
    List<Article> getArticlesByMember(Long memberId);

    void updateArticle(Long id, Long memberId, String title, String content);
    void deleteArticle(Long id, Long memberId);
}