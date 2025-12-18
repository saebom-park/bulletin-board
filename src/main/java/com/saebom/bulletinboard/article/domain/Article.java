package com.saebom.bulletinboard.article.domain;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ArticleStatus status;
    private String adminMemo;
    private LocalDateTime adminMemoUpdatedAt;
    private Long adminMemoAdminId;

    // constructor
    public Article() {

    }

    private Article(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    // method
    // 생성 책임
    public static Article createArticle(Long memberId, String title, String content) {
        return new Article(memberId, title, content);
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

    // setter
    public void setId(Long id) { this.id = id; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}