package com.saebom.bulletinboard.article.domain;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // constructor
    public Article() {

    }

    private Article(Long memberId, String title, String content, int viewCount) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }

    // method
    // 생성 책임
    public static Article createArticle(Long memberId, String title, String content) {
        return new Article(memberId, title, content, 0);
    }

    // 상태 변경 책임 (수정)
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // getter
    public Long getId() { return id; }
    public Long getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getViewCount() { return viewCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // setter
    public void setId(Long id) { this.id = id; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}