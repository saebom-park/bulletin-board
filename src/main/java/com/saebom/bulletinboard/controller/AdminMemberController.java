package com.saebom.bulletinboard.controller;

import com.saebom.bulletinboard.domain.Status;
import com.saebom.bulletinboard.dto.admin.AdminMemberDto;
import com.saebom.bulletinboard.session.SessionConst;
import com.saebom.bulletinboard.service.AdminMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/members")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    public AdminMemberController(AdminMemberService adminMemberService) { this.adminMemberService = adminMemberService; }

    @GetMapping
    public String list(Model model) {

        List<AdminMemberDto> members = adminMemberService.getMembers();
        model.addAttribute("members", members);
        model.addAttribute("statuses", Status.values());
        return "admin/members";
    }

    @PostMapping("/{memberId}/status")
    public String changeStatus(
            @PathVariable Long memberId,
            @RequestParam("status") Status status,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        HttpSession session = request.getSession(false);
        Long adminId = (session != null)
                ? (Long) session.getAttribute(SessionConst.LOGIN_MEMBER)
                : null;

        if (adminId == null) {
            throw new IllegalStateException("관리자 세션이 존재하지 않습니다.");
        }

        try {
            adminMemberService.updateStatus(adminId, memberId, status);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "회원 상태가 성공적으로 변경되었습니다."
            );
        } catch (IllegalArgumentException e) {
            // 정책 위반(본인 변경, WITHDRAW 요청 등)
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );
        } catch (IllegalStateException e) {
            // rowcount 불일치 등 처리 실패
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "상태 변경에 실패했습니다."
            );
        }

        return "redirect:/admin/members";
    }
}