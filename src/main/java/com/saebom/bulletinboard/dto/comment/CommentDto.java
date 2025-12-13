package com.saebom.bulletinboard.dto.comment;

import java.time.LocalDateTime;

public class CommentDto {

    private Long id;
    private Long articleId;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String memberUsername;
    private String memberName;

    // getter
    public Long getId() { return id; }
    public Long getArticleId() { return articleId; }
    public Long getMemberId() { return memberId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getMemberUsername() { return memberUsername; }
    public String getMemberName() { return memberName; }

}