package com.saebom.bulletinboard.comment.dto;

public class CommentEditView {

    private final Long memberId;
    private final String content;

    // constructor
    public CommentEditView(Long memberId, String content) {
        this.memberId = memberId;
        this.content = content;
    }

    // getter
    public Long getMemberId() { return memberId; }
    public String getContent() { return content; }

}