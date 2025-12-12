package com.saebom.bulletinboard.repository;

import com.saebom.bulletinboard.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 댓글 저장
    int insert(Comment comment);

    // PK로 댓글 조회
    Comment findById(Long id);

    // 특정 게시글의 댓글 조회
    List<Comment> findByArticleId(Long articleId);

    // 특정 회원의 댓글 조회
    List<Comment> findByMemberId(Long memberId);

    // 댓글 수정
    int update(Comment comment);

    // 댓글 삭제
    int deleteById(Long id);

}