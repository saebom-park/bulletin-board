package com.saebom.bulletinboard.service;

import com.saebom.bulletinboard.domain.Article;
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
    public Long createArticle(Long memberId, String title, String content) {

        Article article = Article.createArticle(memberId, title, content);

        int inserted = articleMapper.insert(article);
        if (inserted != 1) {
            throw new IllegalStateException("게시글 저장에 실패했습니다.");
        }
        return article.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Article getArticle(Long id) {

        Article article = articleMapper.findById(id);
        if (article == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id);
        }
        return article;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getArticles() {
        return articleMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getArticlesByMember(Long memberId) {
        return articleMapper.findByMemberId(memberId);
    }

    @Override
    public void updateArticle(Long id, Long memberId, String title, String content) {

        Article article = getArticle(id);
        if (!article.getMemberId().equals(memberId)) {
            throw new IllegalStateException("본인 게시글만 수정할 수 있습니다.");
        }

        article.setTitle(title);
        article.setContent(content);

        int updated = articleMapper.update(article);
        if (updated != 1) {
            throw new IllegalStateException("게시글 수정에 실패했습니다. id=" + id);
        }
    }

    @Override
    public void deleteArticle(Long id, Long memberId) {

        Article article = getArticle(id);

        if (!article.getMemberId().equals(memberId)) {
            throw new IllegalStateException("본인 게시글만 삭제할 수 있습니다.");
        }

        int deleted = articleMapper.deleteById(id);
        if (deleted != 1) {
            throw new IllegalStateException("게시글 삭제에 실패했습니다. id=" + id);
        }
    }

}