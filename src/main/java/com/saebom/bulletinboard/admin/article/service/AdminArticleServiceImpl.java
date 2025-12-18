package com.saebom.bulletinboard.admin.article.service;

import com.saebom.bulletinboard.admin.article.dto.AdminArticleDetailView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleListView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleStatusUpdateForm;
import com.saebom.bulletinboard.admin.article.repository.AdminArticleMapper;
import com.saebom.bulletinboard.article.domain.ArticleStatus;
import com.saebom.bulletinboard.global.exception.ArticleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminArticleServiceImpl implements AdminArticleService {

    private final AdminArticleMapper adminArticleMapper;

    // constructor
    public AdminArticleServiceImpl(AdminArticleMapper adminArticleMapper) {
        this.adminArticleMapper = adminArticleMapper;
    }

    @Override
    public List<AdminArticleListView> getArticleList() {
        return adminArticleMapper.selectList();
    }

    @Override
    public AdminArticleDetailView getArticleDetail(Long articleId) {

        AdminArticleDetailView view = adminArticleMapper.selectDetailById(articleId);
        if (view == null) {
            throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
        }

        return view;
    }

    @Override
    @Transactional
    public void updateStatus(Long adminId, Long articleId, AdminArticleStatusUpdateForm form) {

        int updated = adminArticleMapper.updateStatusById(articleId, form.getStatus(), form.getAdminMemo(), adminId);
        if (updated != 1) {
            throw new IllegalStateException("게시글 상태 변경에 실패했습니다.");
        }
    }

    @Override
    @Transactional
    public void deleteArticle(Long adminId, Long articleId) {

        int deleted = adminArticleMapper.deleteById(articleId);
        if (deleted != 1) {
            throw new IllegalStateException("게시글 삭제에 실패했습니다.");
        }

    }

}