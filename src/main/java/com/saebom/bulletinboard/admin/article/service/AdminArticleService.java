package com.saebom.bulletinboard.admin.article.service;

import com.saebom.bulletinboard.admin.article.dto.AdminArticleDetailView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleListView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleStatusUpdateForm;

import java.util.List;
import java.time.LocalDateTime;

public interface AdminArticleService {

    // 게시글 리스트 조회
    List<AdminArticleListView> getArticleList();

    // 게시글 상세 조회
    AdminArticleDetailView getArticleDetail(Long articleId);

    // 게시글 상태 수정
    void updateStatus(Long adminId, Long articleId, AdminArticleStatusUpdateForm form);

}