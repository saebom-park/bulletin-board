package com.saebom.bulletinboard.admin.comment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import com.saebom.bulletinboard.comment.domain.CommentStatus;

public class AdminCommentStatusUpdateForm {

    @NotNull
    private CommentStatus status;

    @NotBlank
    private String adminMemo;

    // getter
    public CommentStatus getStatus() { return status; }
    public String getAdminMemo() { return adminMemo; }

    // setter
    public void setStatus(CommentStatus status) { this.status = status; }
    public void setAdminMemo(String adminMemo) { this.adminMemo = adminMemo; }

}