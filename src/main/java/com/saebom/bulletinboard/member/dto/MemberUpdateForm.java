package com.saebom.bulletinboard.member.dto;

import com.saebom.bulletinboard.global.validation.Name;
import jakarta.validation.constraints.Email;

public class MemberUpdateForm {

    @Name
    private String name;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    // getter
    public String getName() { return name; }
    public String getEmail() { return email; }

    // setter
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

}