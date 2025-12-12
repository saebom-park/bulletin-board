package com.saebom.bulletinboard.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentCreateForm {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 1000, message = "댓글 내용은 1000자를 넘을 수 없습니다.")
    private String content;

    // getter
    public String getContent() { return content; }

    // setter
    public void setContent(String content) {
        this.content = content;
    }

}