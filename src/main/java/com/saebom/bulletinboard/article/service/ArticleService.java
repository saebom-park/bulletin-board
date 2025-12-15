package com.saebom.bulletinboard.article.service;

import com.saebom.bulletinboard.article.dto.ArticleCreateForm;
import com.saebom.bulletinboard.article.dto.ArticleDetailView;
import com.saebom.bulletinboard.article.dto.ArticleEditView;
import com.saebom.bulletinboard.article.dto.ArticleListView;
import com.saebom.bulletinboard.article.dto.ArticleUpdateForm;

import java.util.List;

public interface ArticleService {

    Long createArticle(Long loginMemberId, ArticleCreateForm form);

    List<ArticleListView> getArticleList();
    ArticleDetailView getArticleDetail(Long articleId);
    ArticleEditView getArticleEditView(Long articleId);

    void updateArticle(Long articleId, Long loginMemberId, ArticleUpdateForm form);
    void deleteArticle(Long articleId, Long loginMemberId);

    void increaseViewCount(Long articleId);

}