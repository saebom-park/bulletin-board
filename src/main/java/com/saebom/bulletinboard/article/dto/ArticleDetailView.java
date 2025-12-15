package com.saebom.bulletinboard.article.dto;

import java.time.LocalDateTime;

public class ArticleDetailView {

    private final Long id;
    private final Long memberId;
    private final String title;
    private final String content;
    private final Integer viewCount;
    private final LocalDateTime createdAt;
    private final String memberUsername;
    private final String memberName;

    // constructor
    public ArticleDetailView(Long id, Long memberId, String title, String content, Integer viewCount,
                             LocalDateTime createdAt, String memberUsername, String memberName) {
        this.id =id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
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
    public String getMemberUsername() { return memberUsername; }
    public String getMemberName() { return memberName; }

}