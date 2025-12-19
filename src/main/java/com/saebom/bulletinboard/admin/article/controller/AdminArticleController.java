package com.saebom.bulletinboard.admin.article.controller;

import com.saebom.bulletinboard.admin.article.dto.AdminArticleDetailView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleListView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleStatusUpdateForm;
import com.saebom.bulletinboard.admin.article.service.AdminArticleService;
import com.saebom.bulletinboard.admin.comment.dto.AdminCommentListView;
import com.saebom.bulletinboard.admin.comment.service.AdminCommentService;
import com.saebom.bulletinboard.global.web.LoginSessionUtils;
import jakarta.servlet.http.HttpServletRequest;
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

    // constructor
    public AdminArticleController(AdminArticleService adminArticleService, AdminCommentService adminCommentService) {
        this.adminArticleService = adminArticleService;
        this.adminCommentService = adminCommentService;
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
        model.addAttribute("statusForm", articleStatusForm);

        List<AdminCommentListView> comments = adminCommentService.getCommentList(articleId);
        model.addAttribute("comments", comments);

        return "admin/articles/detail";
    }

    @PostMapping("/{articleId}/status")
    public String editStatus(
            @Valid @ModelAttribute("statusForm") AdminArticleStatusUpdateForm form,
            BindingResult bindingResult,
            @PathVariable("articleId") Long articleId,
            HttpServletRequest request,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            AdminArticleDetailView article = adminArticleService.getArticleDetail(articleId);
            model.addAttribute("article", article);
            model.addAttribute("statusForm", form);

            return "admin/articles/detail";
        }

        Long adminId = LoginSessionUtils.requireLoginMemberId(request);
        adminArticleService.updateStatus(adminId, articleId, form);

        return "redirect:/admin/articles/" + articleId;

    }

    @PostMapping("/{articleId}/delete")
    public String delete(
            @PathVariable("articleId") Long articleId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {

        try {
            Long adminId = LoginSessionUtils.requireLoginMemberId(request);
            adminArticleService.deleteArticle(adminId, articleId);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "게시글이 정상적으로 삭제되었습니다."
            );
        } catch (IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "게시글 삭제에 실패했습니다"
            );
        }

        return "redirect:/admin/articles";

    }
}