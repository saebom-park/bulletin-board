package com.saebom.bulletinboard.admin.comment.controller;

import com.saebom.bulletinboard.admin.article.dto.AdminArticleDetailView;
import com.saebom.bulletinboard.admin.article.service.AdminArticleService;
import com.saebom.bulletinboard.admin.comment.dto.AdminCommentListView;
import com.saebom.bulletinboard.admin.comment.dto.AdminCommentStatusUpdateForm;
import com.saebom.bulletinboard.admin.comment.service.AdminCommentService;
import com.saebom.bulletinboard.global.exception.CommentNotFoundException;
import com.saebom.bulletinboard.global.security.CurrentUserId;
import com.saebom.bulletinboard.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final AdminCommentService adminCommentService;
    private final AdminArticleService adminArticleService;
    private final MemberService memberService;

    // constructor
    public AdminCommentController(
            AdminCommentService adminCommentService,
            AdminArticleService adminArticleService,
            MemberService memberService
    ) {
        this.adminCommentService = adminCommentService;
        this.adminArticleService = adminArticleService;
        this.memberService = memberService;
    }

    @PostMapping("/{commentId}/status")
    public String editStatus(
            @Valid @ModelAttribute AdminCommentStatusUpdateForm form,
            BindingResult bindingResult,
            @PathVariable("commentId") Long commentId,
            @RequestParam("articleId") Long articleId,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            AdminArticleDetailView article = adminArticleService.getArticleDetail(articleId);
            List<AdminCommentListView> comments = adminCommentService.getCommentList(articleId);
            model.addAttribute("article", article);
            model.addAttribute("comments", comments);
            model.addAttribute("commentErrorId", commentId);

            return "admin/articles/detail";
        }

        try {
            Long adminId = CurrentUserId.requireMemberId(memberService);

            adminCommentService.updateStatus(adminId, commentId, form);

            redirectAttributes.addFlashAttribute("successMessage", "댓글 상태가 정상적으로 변경되었습니다.");

        } catch (CommentNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "댓글 상태 변경에 실패했습니다.");
        }

        return "redirect:/admin/articles/" + articleId;

    }
}