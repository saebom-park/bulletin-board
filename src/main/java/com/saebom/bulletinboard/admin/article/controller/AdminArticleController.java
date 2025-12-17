package com.saebom.bulletinboard.admin.article.controller;

import com.saebom.bulletinboard.admin.article.dto.AdminArticleDetailView;
import com.saebom.bulletinboard.admin.article.dto.AdminArticleListView;
import com.saebom.bulletinboard.admin.article.service.AdminArticleService;
import com.saebom.bulletinboard.global.web.LoginSessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/articles")
public class AdminArticleController {

    private final AdminArticleService adminArticleService;

    // constructor
    public AdminArticleController(AdminArticleService adminArticleService) {
        this.adminArticleService = adminArticleService;
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

        return "admin/articles/detail";
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