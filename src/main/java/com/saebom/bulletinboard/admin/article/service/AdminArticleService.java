package com.saebom.bulletinboard.admin.article.service;

import com.saebom.bulletinboard.admin.article.dto.AdminArticleDetailView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleListView;

import java.util.List;

public interface AdminArticleService {

    // 게시글 리스트 조회
    List<AdminArticleListView> getArticleList();

    // 게시글 상세 조회
    AdminArticleDetailView getArticleDetail(Long articleId);

    // 게시글 삭제
    void deleteArticle(Long adminId, Long articleId);

}