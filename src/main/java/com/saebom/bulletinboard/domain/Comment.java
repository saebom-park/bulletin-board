package com.saebom.bulletinboard.domain;

import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private Long articleId;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // constructor
    public Comment() {

    }

    private Comment(Long articleId, Long memberId, String content) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.content = content;
    }

    // method
    public static Comment createComment(Long articleId, Long memberId, String content) {
        return new Comment(articleId, memberId, content);
    }

    // getter
    public Long getId() { return id; }
    public Long getArticleId() { return articleId; }
    public Long getMemberId() { return memberId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // setter
    public void setId(Long id) { this.id = id; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}