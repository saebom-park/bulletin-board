package com.saebom.bulletinboard.admin.comment.service;

import com.saebom.bulletinboard.admin.article.service.AdminArticleServiceImpl;
import com.saebom.bulletinboard.admin.comment.dto.AdminCommentListView;
import com.saebom.bulletinboard.admin.comment.dto.AdminCommentStatusUpdateForm;
import com.saebom.bulletinboard.admin.comment.repository.AdminCommentMapper;
import com.saebom.bulletinboard.global.exception.CommentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminCommentServiceImpl implements AdminCommentService {

    private final AdminCommentMapper adminCommentMapper;

    private static final Logger log = LoggerFactory.getLogger(AdminCommentServiceImpl.class);

    // constructor
    public AdminCommentServiceImpl(AdminCommentMapper adminCommentMapper) {
        this.adminCommentMapper = adminCommentMapper;
    }

    @Override
    public List<AdminCommentListView> getCommentList(Long articleId) {
        return adminCommentMapper.selectListByArticleId(articleId);
    }

    @Override
    @Transactional
    public void updateStatus(Long adminId, Long commentId, AdminCommentStatusUpdateForm form) {

        int updated = adminCommentMapper.updateStatusById(commentId, form.getStatus(), form.getAdminMemo(), adminId);
        if (updated == 0) {
            throw new CommentNotFoundException("댓글을 찾을 수 없습니다.");
        }

        if (updated != 1) {
            throw new IllegalStateException("댓글 상태 변경에 실패했습니다.");
        }

    }

    @Override
    @Transactional
    public void deleteComment(Long adminId, Long commentId) {

        int deleted = adminCommentMapper.deleteById(commentId);
        if (deleted == 0) {
            throw new CommentNotFoundException("댓글을 찾을 수 없습니다.");
        }

        if (deleted != 1) {
            throw new IllegalStateException("댓글 삭제에 실패했습니다.");
        }

        log.info("[ADMIN_ACTION] type=ADMIN_DELETE_COMMENT adminId={} commentId={}", adminId, commentId);

    }
}