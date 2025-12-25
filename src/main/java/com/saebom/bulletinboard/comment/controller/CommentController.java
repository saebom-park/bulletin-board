package com.saebom.bulletinboard.comment.controller;

import com.saebom.bulletinboard.comment.dto.CommentCreateForm;
import com.saebom.bulletinboard.comment.dto.CommentUpdateForm;
import com.saebom.bulletinboard.comment.service.CommentService;
import com.saebom.bulletinboard.member.service.MemberService;
import com.saebom.bulletinboard.global.security.CurrentUserId;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    public CommentController(CommentService commentService, MemberService memberService) {
        this.commentService = commentService;
        this.memberService = memberService;
    }

    @PostMapping("/articles/{articleId}/comments")
    public String create(
            @PathVariable Long articleId,
            @Valid @ModelAttribute("commentCreateForm") CommentCreateForm form,
            BindingResult bindingResult,
            @RequestParam(required = false) String returnUrl,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/articles/" + articleId;
        }

        Long loginMemberId = CurrentUserId.requireMemberId(memberService);

        commentService.createComment(articleId, loginMemberId, form);

        redirectAttributes.addFlashAttribute("successMessage", "댓글이 등록되었습니다.");

        String target = buildTarget(returnUrl, articleId);

        return "redirect:" + safeReturnUrlOrDefault(target, "/articles/" + articleId);
    }

    @PostMapping("/comments/{commentId}/edit")
    public String update(
            @PathVariable Long commentId,
            @RequestParam Long articleId,
            @RequestParam(required = false) String returnUrl,
            @Valid @ModelAttribute("commentUpdateForm") CommentUpdateForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/articles/" + articleId + "?editCommentId=" + commentId;
        }

        Long loginMemberId = CurrentUserId.requireMemberId(memberService);

        String target = buildTarget(returnUrl, articleId);

        try {
            commentService.validateCommentBelongsToArticle(commentId, articleId);
        } catch (IllegalArgumentException e) {
            return "redirect:" + safeReturnUrlOrDefault(target, "/articles/" + articleId);
        }

        commentService.updateComment(commentId, loginMemberId, form);

        redirectAttributes.addFlashAttribute("successMessage", "댓글이 수정되었습니다.");

        return "redirect:" + safeReturnUrlOrDefault(target, "/articles/" + articleId);
    }

    @PostMapping("/comments/{commentId}/delete")
    public String delete(
            @PathVariable Long commentId,
            @RequestParam Long articleId,
            @RequestParam(required = false) String returnUrl,
            RedirectAttributes redirectAttributes
    ) {
        Long loginMemberId = CurrentUserId.requireMemberId(memberService);

        String target = buildTarget(returnUrl, articleId);

        try {
            commentService.validateCommentBelongsToArticle(commentId, articleId);
        } catch (IllegalArgumentException e) {
            return "redirect:" + safeReturnUrlOrDefault(target, "/articles/" + articleId);
        }

        commentService.deleteComment(commentId, loginMemberId);

        redirectAttributes.addFlashAttribute("successMessage", "댓글이 삭제되었습니다.");

        return "redirect:" + safeReturnUrlOrDefault(target, "/articles/" + articleId);
    }

    private String safeReturnUrlOrDefault(String returnUrl, String defaultUrl) {

        if (returnUrl == null || returnUrl.isBlank()) return defaultUrl;
        if (!returnUrl.startsWith("/")) return defaultUrl;
        if (returnUrl.startsWith("//")) return defaultUrl;

        return returnUrl;
    }

    private String buildTarget(String returnUrl, Long articleId) {
        String base = (returnUrl != null && !returnUrl.isBlank()) ? returnUrl : "/articles";
        if (base.endsWith("/")) base = base.substring(0, base.length() - 1);

        return base + "/" + articleId;
    }

}