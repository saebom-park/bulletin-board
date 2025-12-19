package com.saebom.bulletinboard.admin.comment.service;

import com.saebom.bulletinboard.admin.comment.dto.AdminCommentListView;
import com.saebom.bulletinboard.admin.comment.dto.AdminCommentStatusUpdateForm;

import java.util.List;

public interface AdminCommentService {

    // 게시글 별 댓글 리스트 조회
    List<AdminCommentListView> getCommentList(Long articleId);

    // 게시글 상태 변경
    void updateStatus(Long adminId, Long commentId, AdminCommentStatusUpdateForm form);

    // 게시글 삭제
    void deleteComment(Long adminId, Long commentId);

}