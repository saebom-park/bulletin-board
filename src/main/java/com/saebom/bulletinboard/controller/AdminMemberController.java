package com.saebom.bulletinboard.controller;

import com.saebom.bulletinboard.domain.Status;
import com.saebom.bulletinboard.dto.admin.AdminMemberDto;
import com.saebom.bulletinboard.service.AdminMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}