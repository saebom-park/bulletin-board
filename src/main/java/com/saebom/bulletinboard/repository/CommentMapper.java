package com.saebom.bulletinboard.repository;

import com.saebom.bulletinboard.domain.Comment;
import com.saebom.bulletinboard.dto.comment.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 댓글 저장
    int insert(Comment comment);

    // 순수 댓글 조회
    Comment findDomainById(@Param("id") Long id);

    // PK로 댓글 조회
    CommentDto findById(@Param("id") Long id);

    // 특정 게시글의 댓글 조회
    List<CommentDto> findByArticleId(@Param("articleId") Long articleId);

    // 특정 회원의 댓글 조회
    List<CommentDto> findByMemberId(@Param("memberId") Long memberId);

    // 댓글 수정
    int update(Comment comment);

    // 댓글 삭제
    int deleteById(@Param("id") Long id);

}