package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Article;
import com.saebom.bulletinboard.dto.article.ArticleDto;
import com.saebom.bulletinboard.exception.ArticleNotFoundException;
import com.saebom.bulletinboard.exception.NoPermissionException;
import com.saebom.bulletinboard.repository.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public Long createArticle(Long loginMemberId, String title, String content) {

        Article article = Article.createArticle(loginMemberId, title, content);

        int inserted = articleMapper.insert(article);
        if (inserted != 1) {
            throw new IllegalStateException("게시글 저장에 실패했습니다.");
        }

        return article.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {

        ArticleDto articleDto = articleMapper.findById(articleId);
        if (articleDto == null) {
            throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
        }

        return articleDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getArticles() {
        return articleMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getArticlesByMember(Long memberId) {
        return articleMapper.findByMemberId(memberId);
    }

    @Override
    public void updateArticle(Long articleId, Long loginMemberId, String title, String content) {

        Article article = articleMapper.findDomainById(articleId);
        if (article == null) {
            throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
        }

        validateOwner(article, loginMemberId);

        article.update(title, content);

        int updated = articleMapper.update(article);
        if (updated != 1) {
            throw new IllegalStateException("게시글 수정에 실패했습니다.");
        }
    }

    @Override
    public void deleteArticle(Long articleId, Long loginMemberId) {

        Article article = articleMapper.findDomainById(articleId);
        if (article == null) {
            throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
        }

        validateOwner(article, loginMemberId);

        int deleted = articleMapper.deleteById(articleId);
        if (deleted != 1) {
            throw new IllegalStateException("게시글 삭제에 실패했습니다.");
        }
    }

    private void validateOwner(Article article, Long loginMemberId) {
        if (!article.getMemberId().equals(loginMemberId)) {
            throw new NoPermissionException("본인 게시글만 접근할 수 있습니다.");
        }
    }

}