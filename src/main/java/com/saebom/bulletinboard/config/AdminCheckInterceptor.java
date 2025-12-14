package com.saebom.bulletinboard.config;

import com.saebom.bulletinboard.domain.Role;
import com.saebom.bulletinboard.domain.Member;
import com.saebom.bulletinboard.session.SessionConst;
import com.saebom.bulletinboard.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AdminCheckInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    public AdminCheckInterceptor(MemberService memberService) { this.memberService = memberService; }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        HttpSession session = request.getSession(false);
        Long loginMemberId = (session != null) ? (Long) session.getAttribute(SessionConst.LOGIN_MEMBER) : null;

        // 1) 로그인 체크
        if (loginMemberId == null) {
            String redirectURL = requestURI;
            if (queryString != null && !queryString.isBlank()) redirectURL += "?" + queryString;

            String encodedRedirectURL = URLEncoder.encode(redirectURL, StandardCharsets.UTF_8);
            response.sendRedirect("/login?redirectURL=" + encodedRedirectURL);

            return false;
        }

        // 2) 로그인 권한 체크 (DB에서 role 조회)
        Member member = memberService.getMember(loginMemberId);

        if (member == null || !Role.ADMIN.value().equals(member.getRole())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            return false;
        }

        return true;

    }
}