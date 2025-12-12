package com.saebom.bulletinboard.controller;

import com.saebom.bulletinboard.domain.Article;
import com.saebom.bulletinboard.domain.Comment;
import com.saebom.bulletinboard.dto.article.ArticleCreateForm;
import com.saebom.bulletinboard.dto.article.ArticleUpdateForm;
import com.saebom.bulletinboard.dto.comment.CommentCreateForm;
import com.saebom.bulletinboard.dto.comment.CommentUpdateForm;
import com.saebom.bulletinboard.service.ArticleService;
import com.saebom.bulletinboard.service.CommentService;
import com.saebom.bulletinboard.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping
    public String list(Model model) {
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articles", articles);
        return "articles/list";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            @RequestParam(value = "editCommentId", required = false) Long editCommentId,
            HttpServletRequest request,
            Model model
    ) {
        Article article = articleService.getArticle(id);
        List<Comment> comments = commentService.getCommentsByArticle(id);

        Long loginMemberId = getLoginMemberId(request);

        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
        model.addAttribute("commentCreateForm", new CommentCreateForm());

        if (editCommentId != null) {
            Comment comment = commentService.getComment(editCommentId);

            if (loginMemberId == null || !comment.getMemberId().equals(loginMemberId)) {
                return "redirect:/articles/" + id;
            }

            CommentUpdateForm commentUpdateForm = new CommentUpdateForm();
            commentUpdateForm.setContent(comment.getContent());
            model.addAttribute("commentUpdateForm", commentUpdateForm);
            model.addAttribute("editCommentId", editCommentId);
        }

        return "articles/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(HttpServletRequest request, Model model) {
        Long loginMemberId = getLoginMemberId(request);
        if (loginMemberId == null) {
            return "redirect:/login";
        }

        model.addAttribute("articleCreateForm", new ArticleCreateForm());
        return "articles/new";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("articleCreateForm") ArticleCreateForm form,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "articles/new";
        }

        Long loginMemberId = getLoginMemberId(request);
        if (loginMemberId == null) {
            return "redirect:/login";
        }

        Long articleId = articleService.createArticle(
                loginMemberId,
                form.getTitle(),
                form.getContent()
        );

        return "redirect:/articles/" + articleId;
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, HttpServletRequest request, Model model) {
        Long loginMemberId = getLoginMemberId(request);
        if (loginMemberId == null) {
            return "redirect:/login";
        }

        Article article = articleService.getArticle(id);

        if (!article.getMemberId().equals(loginMemberId)) {
            return "redirect:/articles/" + id;
        }

        ArticleUpdateForm form = new ArticleUpdateForm();
        form.setTitle(article.getTitle());
        form.setContent(article.getContent());

        model.addAttribute("articleUpdateForm", form);
        model.addAttribute("articleId", id);

        return "articles/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("articleUpdateForm") ArticleUpdateForm form,
            BindingResult bindingResult,
            HttpServletRequest request,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("articleId", id);
            return "articles/edit";
        }

        Long loginMemberId = getLoginMemberId(request);
        if (loginMemberId == null) {
            return "redirect:/login";
        }

        articleService.updateArticle(id, loginMemberId, form.getTitle(), form.getContent());

        return "redirect:/articles/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpServletRequest request) {
        Long loginMemberId = getLoginMemberId(request);
        if (loginMemberId == null) {
            return "redirect:/login";
        }

        articleService.deleteArticle(id, loginMemberId);
        return "redirect:/articles";
    }

    // 헬퍼 메서드
    private Long getLoginMemberId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
    }

}