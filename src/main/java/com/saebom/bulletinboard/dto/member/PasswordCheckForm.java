package com.saebom.bulletinboard.dto.member;

import jakarta.validation.constraints.NotBlank;

public class PasswordCheckForm {

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    // getter
    public String getPassword() { return password; }

    // setter
    public void setPassword(String password) { this.password = password; }

}