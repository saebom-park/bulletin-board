package com.saebom.bulletinboard.config;

import com.saebom.bulletinboard.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if ("/articles".equals(requestURI) && "GET".equalsIgnoreCase(method)) {
            return true;
        }

        if (requestURI.matches("^/articles/\\d+$") && "GET".equalsIgnoreCase(method)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        Long loginMemberId = (session != null) ? (Long) session.getAttribute(SessionConst.LOGIN_MEMBER) : null;

        if (loginMemberId == null) {
            String queryString = request.getQueryString();

            String redirectURL = requestURI;
            if (queryString != null && !queryString.isBlank()) {
                redirectURL = redirectURL + "?" + queryString;
            }

            String encodedRedirectURL = URLEncoder.encode(redirectURL, StandardCharsets.UTF_8);

            response.sendRedirect("/login?redirectURL=" + encodedRedirectURL);
            return false;
        }

        return true;

    }
}