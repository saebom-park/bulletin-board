package com.saebom.bulletinboard.admin.article.dto;

import com.saebom.bulletinboard.article.domain.ArticleStatus;
import jakarta.validation.constraints.NotBlank;

public class AdminArticleStatusUpdateForm {

    private ArticleStatus status;

    @NotBlank(message = "관리자 메모는 필수입니다.")
    private String adminMemo;

    // getter
    public ArticleStatus getStatus() { return status; }
    public String getAdminMemo() { return adminMemo; }

    // setter
    public void setStatus(ArticleStatus status) { this.status = status; }
    public void setAdminMemo(String adminMemo) { this.adminMemo = adminMemo; }

}