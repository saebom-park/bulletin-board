package com.saebom.bulletinboard.admin.article.dto;

import com.saebom.bulletinboard.article.domain.ArticleStatus;

import java.time.LocalDateTime;

public class AdminArticleDetailView {

    private final Long id;
    private final Long memberId;
    private final String title;
    private final String content;
    private final Integer viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final ArticleStatus status;
    private final String adminMemo;
    private final LocalDateTime adminMemoUpdatedAt;
    private final Long adminMemoAdminId;

    private final String memberUsername;
    private final String memberName;

    // constructor
    public AdminArticleDetailView(
            Long id,
            Long memberId,
            String title,
            String content,
            Integer viewCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            ArticleStatus status,
            String adminMemo,
            LocalDateTime adminMemoUpdatedAt,
            Long adminMemoAdminId,
            String memberUsername,
            String memberName
    ) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.adminMemo = adminMemo;
        this.adminMemoUpdatedAt = adminMemoUpdatedAt;
        this.adminMemoAdminId = adminMemoAdminId;
        this.memberUsername = memberUsername;
        this.memberName = memberName;
    }

    // getter
    public Long getId() { return id; }
    public Long getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Integer getViewCount() { return viewCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public ArticleStatus getStatus() { return status; }
    public String getAdminMemo() { return adminMemo; }
    public LocalDateTime getAdminMemoUpdatedAt() { return adminMemoUpdatedAt; }
    public Long getAdminMemoAdminId() { return adminMemoAdminId; }
    public String getMemberUsername() { return memberUsername; }
    public String getMemberName() { return memberName; }

    // helper method
    public String getWriterText() {
        return memberName + "(" + memberUsername + ")";
    }
}