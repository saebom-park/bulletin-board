package com.saebom.bulletinboard.member.controller;

import com.saebom.bulletinboard.global.security.CurrentUser;
import com.saebom.bulletinboard.member.dto.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.saebom.bulletinboard.global.web.RedirectUtils.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(
            @RequestParam(value = "redirectURL", required = false, defaultValue = "/articles") String redirectURL,
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {

        if (CurrentUser.username().isPresent()) {
            return redirectTo(safeReturnUrlOrDefault(redirectURL, "/articles"));
        }

        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("redirectURL", redirectURL);

        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 패스워드가 일치하지 않습니다.");
        }

        return "member/login";
    }

}