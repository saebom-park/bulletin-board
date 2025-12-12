package com.saebom.bulletinboard.controller;

import com.saebom.bulletinboard.domain.Member;
import com.saebom.bulletinboard.dto.member.MemberCreateForm;
import com.saebom.bulletinboard.dto.member.UsernameCheckForm;
import com.saebom.bulletinboard.dto.member.MemberProfileView;
import com.saebom.bulletinboard.dto.member.MemberUpdateForm;
import com.saebom.bulletinboard.dto.member.PasswordCheckForm;
import com.saebom.bulletinboard.dto.member.PasswordChangeForm;
import com.saebom.bulletinboard.dto.member.MemberWithdrawForm;
import com.saebom.bulletinboard.service.MemberService;
import com.saebom.bulletinboard.session.SessionConst;
import com.saebom.bulletinboard.exception.WrongPasswordException;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/new")
    public String signUpForm(Model model) {
        model.addAttribute("memberCreateForm", new MemberCreateForm());
        model.addAttribute("usernameChecked", false);
        return "member/signup";
    }

    @PostMapping("/new")
    public String signUp(
            @Valid @ModelAttribute("memberCreateForm") MemberCreateForm form,
            BindingResult bindingResult,
            HttpServletRequest request,
            @RequestParam(defaultValue = "false") boolean usernameChecked,
            Model model
    ) {

        model.addAttribute("usernameChecked", usernameChecked);

        if (!bindingResult.hasFieldErrors("username")) {
            if (memberService.isUsernameDuplicate(form.getUsername())) {
                bindingResult.rejectValue("username", "duplicate", "이미 사용 중인 아이디입니다.");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("usernameCheck", false);
            return "member/signup";
        }

        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setPassword(form.getPassword());
        member.setName(form.getName());
        member.setEmail(form.getEmail());

        Long memberId = memberService.registerMember(member);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, memberId);

        return "redirect:/articles";
    }

    @GetMapping("/check-username")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUsername(
            @Valid UsernameCheckForm form,
            BindingResult bindingResult
    ) {

        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();

            response.put("valid", false);
            response.put("duplicate", false);
            response.put("message", message);

            return ResponseEntity.badRequest().body(response);
        }

        String username = form.getUsername();
        boolean duplicate = memberService.isUsernameDuplicate(username);

        response.put("valid", !duplicate);
        response.put("duplicate", duplicate);
        response.put("message",
                duplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public String myPage(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long loginMemberId,
            Model model
    ) {

        Member member = memberService.getMember(loginMemberId);
        model.addAttribute("member", MemberProfileView.from(member));

        return "member/profile";
    }

    @GetMapping("/me/edit")
    public String editForm(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long loginMemberId,
            Model model
    ) {

        Member member = memberService.getMember(loginMemberId);

        MemberUpdateForm form = new MemberUpdateForm();
        form.setName(member.getName());
        form.setEmail(member.getEmail());

        model.addAttribute("member", member);
        model.addAttribute("memberUpdateForm", form);

        return "member/edit";
    }

    @PostMapping("/me/edit")
    public String edit(
            @Valid @ModelAttribute("memberUpdateForm") MemberUpdateForm form,
            BindingResult bindingResult,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long loginMemberId,
            Model model
    ) {

        Member member = memberService.getMember(loginMemberId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            return "member/edit";
        }

        member.setName(form.getName());
        member.setEmail(form.getEmail());

        memberService.updateMember(member);

        return "redirect:/members/me";
    }

    @GetMapping("/me/password/check")
    public String passwordCheckForm(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long loginMemberId,
            Model model
    ) {

        model.addAttribute("passwordCheckForm", new PasswordCheckForm());

        return "member/password-check";
    }

    @PostMapping("/me/password/check")
    public String checkPassword(
            @Valid @ModelAttribute("passwordCheckForm") PasswordCheckForm form,
            BindingResult bindingResult,
            @SessionAttribute(SessionConst.LOGIN_MEMBER) Long loginMemberId,
            HttpServletRequest request
    ) {

        if (bindingResult.hasErrors()) {
            return "member/password-check";
        }

        try {
            memberService.validatePassword(loginMemberId, form.getPassword());

            HttpSession session = request.getSession(false);

            if (session == null) {
                throw new IllegalArgumentException("세션이 유효하지 않습니다. 다시 로그인 해주세요.");
            }

            session.setAttribute(SessionConst.PASSWORD_CHECKED, true);

            return "redirect:/members/me/password/new";

        } catch(WrongPasswordException e) {
            bindingResult.rejectValue("password", "wrongPassword", e.getMessage());

            return "member/password-check";
        }

    }

    @GetMapping("/me/password/new")
    public String passwordChangeForm(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long loginMemberId,
            HttpServletRequest request,
            Model model
    ) {

        HttpSession session = request.getSession(false);
        Boolean passwordChecked = (session != null)
                ? (Boolean) session.getAttribute(SessionConst.PASSWORD_CHECKED)
                : null;

        if (!Boolean.TRUE.equals(passwordChecked)) {
            return "redirect:/members/me/password/check";
        }

        Member member = memberService.getMember(loginMemberId);
        model.addAttribute("passwordChangedAt", member.getPasswordChangedAt());
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());

        return "member/password-new";
    }

    @PostMapping("/me/password/new")
    public String changePassword(
            @Valid @ModelAttribute("passwordChangeForm") PasswordChangeForm form,
            BindingResult bindingResult,
            @SessionAttribute(SessionConst.LOGIN_MEMBER) Long loginMemberId,
            HttpServletRequest request,
            Model model
    ) {

        HttpSession session = request.getSession(false);
        Boolean passwordChecked = (session != null)
                ? (Boolean) session.getAttribute(SessionConst.PASSWORD_CHECKED)
                : null;

        if (!Boolean.TRUE.equals(passwordChecked)) {
            return "redirect:/members/me/password/check";
        }

        Member member = memberService.getMember(loginMemberId);
        model.addAttribute("passwordChangedAt", member.getPasswordChangedAt());

        if (bindingResult.hasErrors()) {
            return "member/password-new";
        }

        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "mismatch", "변경하려는 비밀번호가 동일해야합니다.");
            return "member/password-new";
        }

        memberService.updatePassword(loginMemberId, form.getNewPassword());

        session.removeAttribute(SessionConst.PASSWORD_CHECKED);

        return "redirect:/members/me/password/success";
    }

    @GetMapping("/me/password/success")
    public String passwordSuccessForm(
            @SessionAttribute(SessionConst.LOGIN_MEMBER) Long loginMemberId
    ) {
        return "member/password-success";
    }

    @GetMapping("/me/withdraw")
    public String memberWithdrawForm(
            @SessionAttribute(SessionConst.LOGIN_MEMBER) Long loginMemberId,
            Model model
    ) {

        model.addAttribute("memberWithdrawForm", new MemberWithdrawForm());

        return "member/withdraw";
    }

    @PostMapping("/me/withdraw")
    public String withdrawMember(
            @Valid @ModelAttribute("memberWithdrawForm") MemberWithdrawForm form,
            BindingResult bindingResult,
            @SessionAttribute(SessionConst.LOGIN_MEMBER) Long loginMemberId,
            HttpServletRequest request
    ) {

        if (bindingResult.hasErrors()) {
            return "member/withdraw";
        }

        try {
            memberService.validatePassword(loginMemberId, form.getPassword());
        } catch(WrongPasswordException e) {
            bindingResult.rejectValue("password", "wrong", e.getMessage());
            return "member/withdraw";
        }

        memberService.withdrawMember(loginMemberId);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/members/withdraw/success";
    }

    @GetMapping("/withdraw/success")
    public String withdrawSuccessForm() {
        return "member/withdraw-success";
    }

}