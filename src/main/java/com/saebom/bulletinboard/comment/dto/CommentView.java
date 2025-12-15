package com.saebom.bulletinboard.comment.dto;

import java.time.LocalDateTime;

public class CommentView {

    private final Long id;
    private final Long memberId;
    private final String content;
    private final LocalDateTime createdAt;
    private final String memberUsername;
    private final String memberName;

    // constructor
    public CommentView(Long id, Long memberId, String content, LocalDateTime createdAt,
                       String memberUsername, String memberName) {
        this.id = id;
        this.memberId = memberId;
        this.content = content;
        this.createdAt = createdAt;
        this.memberUsername = memberUsername;
        this.memberName = memberName;
    }

    // getter
    public Long getId() { return id; }
    public Long getMemberId() { return memberId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getMemberUsername() { return memberUsername; }
    public String getMemberName() { return memberName; }

}