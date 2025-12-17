package com.saebom.bulletinboard.admin.article.dto;

import java.time.LocalDateTime;

public class AdminArticleDetailView {

    private final Long id;
    private final Long memberId;
    private final String title;
    private final String content;
    private final Integer viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final String memberUsername;
    private final String memberName;

    // TODO(Admin): later - deleted/status, reportCount, moderatedAt, moderatedBy

    // constructor
    public AdminArticleDetailView(
            Long id,
            Long memberId,
            String title,
            String content,
            Integer viewCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
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
    public String getMemberUsername() { return memberUsername; }
    public String getMemberName() { return memberName; }

    // helper method
    public String getWriterText() {
        return memberName + "(" + memberUsername + ")";
    }
}