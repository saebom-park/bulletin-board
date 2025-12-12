package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Comment;

import java.util.List;

public interface CommentService {

    Long createComment(Long articleId, Long memberId, String content);

    Comment getComment(Long id);
    List<Comment> getCommentsByArticle(Long articleId);
    List<Comment> getCommentsByMember(Long memberId);

    void updateComment(Long id, Long memberId, String content);
    void deleteComment(Long id, Long memberId);
}