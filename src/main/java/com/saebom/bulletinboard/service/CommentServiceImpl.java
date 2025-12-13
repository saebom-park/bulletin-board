package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Comment;
import com.saebom.bulletinboard.dto.comment.CommentDto;
import com.saebom.bulletinboard.exception.CommentNotFoundException;
import com.saebom.bulletinboard.exception.NoPermissionException;
import com.saebom.bulletinboard.repository.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public Long createComment(Long articleId, Long loginMemberId, String content) {

        Comment comment = Comment.createComment(articleId, loginMemberId, content);

        int inserted = commentMapper.insert(comment);
        if (inserted != 1) {
            throw new IllegalStateException("댓글 저장에 실패했습니다.");
        }

        return comment.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getComment(Long commentId) {

        CommentDto commentDto = commentMapper.findById(commentId);
        if (commentDto == null) {
            throw new CommentNotFoundException("댓글을 찾을 수 없습니다.");
        }

        return commentDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByArticle(Long articleId) {
        return commentMapper.findByArticleId(articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByMember(Long memberId) {
        return commentMapper.findByMemberId(memberId);
    }

    @Override
    public void updateComment(Long commentId, Long loginMemberId, String content) {

        Comment comment = commentMapper.findDomainById(commentId);
        if (comment == null) {
            throw new CommentNotFoundException("댓글을 찾을 수 없습니다.");
        }

        validateOwner(comment, loginMemberId);

        comment.update(content);

        int updated = commentMapper.update(comment);
        if (updated != 1) {
            throw new IllegalStateException("댓글 수정에 실패했습니다.");
        }
    }

    @Override
    public void deleteComment(Long commentId, Long loginMemberId) {

        Comment comment = commentMapper.findDomainById(commentId);
        if (comment == null) {
            throw new CommentNotFoundException("댓글을 찾을 수 없습니다.");
        }

        validateOwner(comment, loginMemberId);

        int deleted = commentMapper.deleteById(commentId);
        if (deleted != 1) {
            throw new IllegalStateException("댓글 삭제에 실패했습니다.");
        }
    }

    private void validateOwner(Comment comment, Long loginMemberId) {

        if (!comment.getMemberId().equals(loginMemberId)) {
            throw new NoPermissionException("본인 댓글만 접근할 수 있습니다.");
        }
    }
}