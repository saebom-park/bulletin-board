package com.saebom.bulletinboard.admin.comment.dto;

import com.saebom.bulletinboard.comment.domain.CommentStatus;

public class AdminCommentStatusUpdateForm {

    private CommentStatus status;
    private String adminMemo;

    // getter
    public CommentStatus getStatus() { return status; }
    public String getAdminMemo() { return adminMemo; }

    // setter
    public void setStatus(CommentStatus status) { this.status = status; }
    public void setAdminMemo(String adminMemo) { this.adminMemo = adminMemo; }

}