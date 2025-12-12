package com.saebom.bulletinboard.controller;

import com.saebom.bulletinboard.dto.member.LoginForm;
import com.saebom.bulletinboard.service.MemberService;
import com.saebom.bulletinboard.session.SessionConst;
import com.saebom.bulletinboard.exception.LoginFailedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "redirectURL", required = false, defaultValue = "/articles") String redirectURL, Model model) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("redirectURL", redirectURL);
        return "member/login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("loginForm") LoginForm form,
            BindingResult bindingResult,
            @RequestParam(value = "redirectURL", required = false, defaultValue = "/articles") String redirectURL,
            HttpServletRequest request,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("redirectURL", redirectURL);
            return "member/login";
        }

        try {
            Long loginMemberId = memberService.loginMember(form.getUsername(), form.getPassword());

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberId);
        } catch (LoginFailedException e) {
            model.addAttribute("redirectURL", redirectURL);
            bindingResult.reject("loginFail", e.getMessage());

            return "member/login";
        }

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(
            @RequestParam(value = "redirectURL", required = false, defaultValue = "/articles") String redirectURL,
            HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:" + redirectURL;
    }

}