package com.saebom.bulletinboard.global.exception;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(String message) { super(message); }
}