package com.saebom.bulletinboard.article.service;

import com.saebom.bulletinboard.article.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    Long createArticle(Long loginMemberId, String title, String content);

    ArticleDto getArticle(Long articleId);
    List<ArticleDto> getArticles();
    List<ArticleDto> getArticlesByMember(Long memberId);

    void updateArticle(Long articleId, Long loginMemberId, String title, String content);
    void deleteArticle(Long articleId, Long loginMemberId);

    void increaseViewCount(Long articleId);

}