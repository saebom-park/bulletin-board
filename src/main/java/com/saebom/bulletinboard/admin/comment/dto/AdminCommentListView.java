package com.saebom.bulletinboard.admin.comment.dto;

import com.saebom.bulletinboard.comment.domain.CommentStatus;

import java.time.LocalDateTime;

public class AdminCommentListView {

    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final CommentStatus status;
    private final String adminMemo;
    private final LocalDateTime adminMemoUpdatedAt;

    private final String memberUsername;
    private final String memberName;

    private final String adminMemoUsername;
    private final String adminMemoName;

    // constructor
    public AdminCommentListView(Long id, String content, LocalDateTime createdAt, LocalDateTime updatedAt,
                                CommentStatus status, String adminMemo, LocalDateTime adminMemoUpdatedAt,
                                String memberUsername, String memberName,
                                String adminMemoUsername, String adminMemoName) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.adminMemo = adminMemo;
        this.adminMemoUpdatedAt = adminMemoUpdatedAt;
        this.memberUsername = memberUsername;
        this.memberName = memberName;
        this.adminMemoUsername = adminMemoUsername;
        this.adminMemoName = adminMemoName;
    }

    // getter
    public Long getId() { return id; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public CommentStatus getStatus() { return status; }
    public String getAdminMemo() { return adminMemo; }
    public LocalDateTime getAdminMemoUpdatedAt() { return adminMemoUpdatedAt; }
    public String getMemberUsername() { return memberUsername; }
    public String getMemberName() { return memberName; }
    public String getAdminMemoUsername() { return adminMemoUsername; }
    public String getAdminMemoName() { return adminMemoName; }

    // helper method
    public String getWriterText() {
        return memberName + "(" + memberUsername + ")";
    }

    public boolean isPublic() { return status == CommentStatus.PUBLIC; }

    public String getStatusText() { return isPublic() ? "공개" : "숨김"; }

}