package com.saebom.bulletinboard.comment.controller;

import com.saebom.bulletinboard.comment.dto.CommentCreateForm;
import com.saebom.bulletinboard.comment.dto.CommentUpdateForm;
import com.saebom.bulletinboard.comment.service.CommentService;
import com.saebom.bulletinboard.global.web.LoginSessionUtils;
import jakarta.servlet.http.HttpServletRequest;
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
            @RequestParam(required = false) String returnUrl,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/articles/" + articleId;
        }

        Long loginMemberId = LoginSessionUtils.requireLoginMemberId(request);

        commentService.createComment(articleId, loginMemberId, form);

        String target = returnUrl + "/" + articleId;

        return "redirect:" + safeReturnUrlOrDefault(target, "/articles/" + articleId);
    }

    @PostMapping("/comments/{commentId}/edit")
    public String update(
            @PathVariable Long commentId,
            @RequestParam() Long articleId,
            @RequestParam(required = false) String returnUrl,
            @Valid @ModelAttribute("commentUpdateForm") CommentUpdateForm form,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/articles/" + articleId + "?editCommentId=" + commentId;
        }

        Long loginMemberId = LoginSessionUtils.requireLoginMemberId(request);

        commentService.updateComment(commentId, loginMemberId, form);

        String target = returnUrl + "/" + articleId;

        return "redirect:" + safeReturnUrlOrDefault(target, "/articles/" + articleId);
    }

    @PostMapping("/comments/{commentId}/delete")
    public String delete(
            @PathVariable Long commentId,
            @RequestParam() Long articleId,
            @RequestParam(required = false) String returnUrl,
            HttpServletRequest request
    ) {
        Long loginMemberId = LoginSessionUtils.requireLoginMemberId(request);

        commentService.deleteComment(commentId, loginMemberId);

        String target = returnUrl + "/" + articleId;

        return "redirect:" + safeReturnUrlOrDefault(target, "/articles/" + articleId);
    }

    private String safeReturnUrlOrDefault(String returnUrl, String defaultUrl) {

        if (returnUrl == null || returnUrl.isBlank()) return defaultUrl;
        if (!returnUrl.startsWith("/")) return defaultUrl;
        if (returnUrl.startsWith("//")) return defaultUrl;

        return returnUrl;
    }

}