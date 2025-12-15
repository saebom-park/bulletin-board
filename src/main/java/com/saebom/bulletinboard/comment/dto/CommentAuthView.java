package com.saebom.bulletinboard.comment.dto;

public class CommentAuthView {

    private final Long commentId;
    private final Long memberId;

    // constructor
    public CommentAuthView(Long commentId, Long memberId) {
        this.commentId = commentId;
        this.memberId = memberId;
    }

    // getter
    public Long getCommentId() { return commentId; }
    public Long getMemberId() { return memberId; }

}