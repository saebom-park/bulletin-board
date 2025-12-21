package com.saebom.bulletinboard.admin.comment.repository;

import com.saebom.bulletinboard.admin.comment.dto.AdminCommentListView;
import com.saebom.bulletinboard.comment.domain.CommentStatus;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminCommentMapper {

    // 게시글 별 댓글 리스트 조회
    List<AdminCommentListView> selectListByArticleId(@Param("articleId") Long articleId);

    // 댓글 상태 수정
    int updateStatusById(@Param("id") Long id,
                     @Param("status") CommentStatus status,
                     @Param("adminMemo") String adminMemo,
                     @Param("adminId") Long adminId);

}