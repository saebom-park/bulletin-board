package com.saebom.bulletinboard.dto.member;

import com.saebom.bulletinboard.validation.Username;
import com.saebom.bulletinboard.validation.Password;
import com.saebom.bulletinboard.validation.Name;
import jakarta.validation.constraints.Email;

public class MemberCreateForm {

    @Username
    private String username;

    @Password
    private String password;

    @Name
    private String name;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    // getter
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    // setter
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

}