package com.saebom.bulletinboard.global.web;

import com.saebom.bulletinboard.member.dto.LoginMemberView;
import com.saebom.bulletinboard.member.service.MemberService;
import com.saebom.bulletinboard.global.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LoginMemberInfoAdvice {

    private final MemberService memberService;

    // constructor
    public LoginMemberInfoAdvice(MemberService memberService) { this.memberService = memberService;}

    @ModelAttribute
    public void addLoginMemberInfo(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Long loginMemberId = (session != null) ? (Long) session.getAttribute(SessionConst.LOGIN_MEMBER) : null;
        if (loginMemberId != null) {
            LoginMemberView loginMemberView = memberService.getLoginMember(loginMemberId);
            model.addAttribute("loginMember", loginMemberView);
        }

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        model.addAttribute("currentPath", requestURI);

        String currentURL = (queryString != null && !queryString.isBlank())
                ? requestURI + "?" + queryString
                : requestURI;

        model.addAttribute("currentURL", currentURL);
    }

}