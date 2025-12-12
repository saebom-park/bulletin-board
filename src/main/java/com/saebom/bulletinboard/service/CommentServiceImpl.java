package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Comment;
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
    public Long createComment(Long articleId, Long memberId, String content) {

        Comment comment = Comment.createComment(articleId, memberId, content);

        int inserted = commentMapper.insert(comment);
        if (inserted != 1) {
            throw new IllegalStateException("댓글 저장에 실패했습니다.");
        }
        return comment.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getComment(Long id) {

        Comment comment = commentMapper.findById(id);
        if (comment == null) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다. id=" + id);
        }
        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByArticle(Long articleId) {
        return commentMapper.findByArticleId(articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByMember(Long memberId) {
        return commentMapper.findByMemberId(memberId);
    }

    @Override
    public void updateComment(Long id, Long memberId, String content) {

        Comment comment = getComment(id);

        if (!comment.getMemberId().equals(memberId)) {
            throw new IllegalStateException("본인 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(content);

        int updated = commentMapper.update(comment);
        if (updated != 1) {
            throw new IllegalStateException("댓글 수정에 실패했습니다. id=" + id);
        }
    }

    @Override
    public void deleteComment(Long id, Long memberId) {

        Comment comment = getComment(id);

        if (!comment.getMemberId().equals(memberId)) {
            throw new IllegalStateException("본인 댓글만 삭제할 수 있습니다.");
        }

        int deleted = commentMapper.deleteById(id);
        if (deleted != 1) {
            throw new IllegalStateException("댓글 삭제에 실패했습니다. id=" + id);
        }
    }
}