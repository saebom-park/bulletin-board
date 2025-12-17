package com.saebom.bulletinboard.admin.article.dto;

import java.time.LocalDateTime;

public class AdminArticleListView {

    private final Long id;
    private final String title;
    private final Integer viewCount;
    private final LocalDateTime createdAt;

    private final String memberUsername;
    private final String memberName;

    // TODO(Admin): later - reportCount, deleted/status, moderatedAt

    // constructor
    public AdminArticleListView(Long id, String title, Integer viewCount, LocalDateTime createdAt,
                           String memberUsername, String memberName) {
        this.id = id;
        this.title = title;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.memberUsername = memberUsername;
        this.memberName = memberName;
    }

    // getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Integer getViewCount() { return viewCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getMemberUsername() { return memberUsername; }
    public String getMemberName() { return memberName; }

    // helper method
    public String getWriterText() {
        return memberName + "(" + memberUsername + ")";
    }
}