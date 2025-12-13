package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {

    Long createComment(Long articleId, Long loginMemberId, String content);

    CommentDto getComment(Long commentId);
    List<CommentDto> getCommentsByArticle(Long articleId);
    List<CommentDto> getCommentsByMember(Long memberId);

    void updateComment(Long commentId, Long loginMemberId, String content);
    void deleteComment(Long commentId, Long loginMemberId);
}