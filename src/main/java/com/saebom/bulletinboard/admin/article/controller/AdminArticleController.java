package com.saebom.bulletinboard.admin.article.controller;

import com.saebom.bulletinboard.admin.article.dto.AdminArticleDetailView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleListView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleStatusUpdateForm;
import com.saebom.bulletinboard.admin.article.service.AdminArticleService;
import com.saebom.bulletinboard.admin.comment.dto.AdminCommentListView;
import com.saebom.bulletinboard.admin.comment.service.AdminCommentService;
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
@RequestMapping("/admin/articles")
public class AdminArticleController {

    private final AdminArticleService adminArticleService;
    private final AdminCommentService adminCommentService;
    private final MemberService memberService;

    // constructor
    public AdminArticleController(
            AdminArticleService adminArticleService,
            AdminCommentService adminCommentService,
            MemberService memberService
    ) {
        this.adminArticleService = adminArticleService;
        this.adminCommentService = adminCommentService;
        this.memberService = memberService;
    }

    @GetMapping
    public String list(Model model) {

        List<AdminArticleListView> articles = adminArticleService.getArticleList();
        model.addAttribute("articles", articles);

        return "admin/articles/list";
    }

    @GetMapping("/{articleId}")
    public String detail(
            @PathVariable("articleId") Long articleId,
            Model model
    ) {

        AdminArticleDetailView article = adminArticleService.getArticleDetail(articleId);
        model.addAttribute("article", article);

        AdminArticleStatusUpdateForm articleStatusForm = new AdminArticleStatusUpdateForm();
        articleStatusForm.setStatus(article.getStatus());
        articleStatusForm.setAdminMemo(article.getAdminMemo());
        model.addAttribute("articleStatusForm", articleStatusForm);

        List<AdminCommentListView> comments = adminCommentService.getCommentList(articleId);
        model.addAttribute("comments", comments);

        return "admin/articles/detail";
    }

    @PostMapping("/{articleId}/status")
    public String editStatus(
            @Valid @ModelAttribute("articleStatusForm") AdminArticleStatusUpdateForm form,
            BindingResult bindingResult,
            @PathVariable("articleId") Long articleId,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            AdminArticleDetailView article = adminArticleService.getArticleDetail(articleId);
            model.addAttribute("article", article);
            model.addAttribute("articleStatusForm", form);

            List<AdminCommentListView> comments = adminCommentService.getCommentList(articleId);
            model.addAttribute("comments", comments);

            return "admin/articles/detail";
        }

        Long adminId = CurrentUserId.requireMemberId(memberService);
        adminArticleService.updateStatus(adminId, articleId, form);

        redirectAttributes.addFlashAttribute("successMessage", "게시글 상태가 변경되었습니다.");

        return "redirect:/admin/articles/" + articleId;

    }

}