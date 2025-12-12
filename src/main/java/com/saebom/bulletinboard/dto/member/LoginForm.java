package com.saebom.bulletinboard.dto.member;

import jakarta.validation.constraints.NotBlank;

public class LoginForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    // getter
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // setter
    public void setPassword(String password) { this.password = password; }
    public void setUsername(String username) { this.username = username; }
}