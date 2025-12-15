package com.saebom.bulletinboard.article.dto;

public class ArticleAuthView {

    private final Long articleId;
    private final Long memberId;

    // constructor
    public ArticleAuthView(Long articleId, Long memberId) {
        this.articleId = articleId;
        this.memberId = memberId;
    }

    // getter
    public Long getArticleId() { return articleId; }
    public Long getMemberId() { return memberId; }

}