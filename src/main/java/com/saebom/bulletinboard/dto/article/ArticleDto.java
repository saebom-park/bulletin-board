package com.saebom.bulletinboard.dto.article;

import java.time.LocalDateTime;

public class ArticleDto {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String memberUsername;
    private String memberName;

    // getter
    public Long getId() { return id; }
    public Long getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getViewCount() { return viewCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getMemberUsername() { return memberUsername; }
    public String getMemberName() { return memberName; }

    // 조회 전용 DTO
    // MyBatis resultMap 매핑을 위해 setter 제공
    public void setId(Long id) { this.id = id; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setMemberUsername(String memberUsername) { this.memberUsername = memberUsername; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

}