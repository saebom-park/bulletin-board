package com.saebom.bulletinboard.controller;

import com.saebom.bulletinboard.dto.comment.CommentCreateForm;
import com.saebom.bulletinboard.dto.comment.CommentUpdateForm;
import com.saebom.bulletinboard.service.CommentService;
import com.saebom.bulletinboard.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{articleId}/comments")
    public String create(
            @PathVariable Long articleId,
            @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/articles/" + articleId;
        }

        Long loginMemberId = getLoginMemberId(request);
        if (loginMemberId == null) {
            return "redirect:/login";
        }

        Long commentId = commentService.createComment(
                articleId,
                loginMemberId,
                form.getContent()
        );

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/comments/{commentId}/edit")
    public String update(
            @PathVariable Long commentId,
            @RequestParam(value = "articleId") Long articleId,
            @Valid @ModelAttribute("commentUpdateForm") CommentUpdateForm form,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/articles/" + articleId + "?editCommentId=" + commentId;
        }

        Long loginMemberId = getLoginMemberId(request);
        if (loginMemberId == null) {
            return "redirect:/login";
        }

        commentService.updateComment(commentId, loginMemberId, form.getContent());

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/comments/{commentId}/delete")
    public String delete(
            @PathVariable Long commentId,
            @RequestParam(value = "articleId") Long articleId,
            HttpServletRequest request
    ) {
        Long loginMemberId = getLoginMemberId(request);
        if (loginMemberId == null) {
            return "redirect:/login";
        }

        commentService.deleteComment(commentId, loginMemberId);
        return "redirect:/articles/" + articleId;
    }

    // 헬퍼메서드
    private Long getLoginMemberId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
    }

}