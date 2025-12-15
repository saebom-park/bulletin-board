package com.saebom.bulletinboard.article.dto;

public class ArticleEditView {

    private final Long id;
    private final Long memberId;
    private final String title;
    private final String content;

    // constructor
    public ArticleEditView(Long id, Long memberId, String title, String content) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    // getter
    public Long getId() { return id; }
    public Long getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }

}