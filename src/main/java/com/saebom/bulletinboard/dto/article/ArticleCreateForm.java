package com.saebom.bulletinboard.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ArticleCreateForm {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    // getter
    public String getTitle() { return title; }
    public String getContent() { return content; }

    // setter
    public void setContent(String content) { this.content = content; }
    public void setTitle(String title) { this.title = title; }

}