package com.saebom.bulletinboard.dto.member;

import com.saebom.bulletinboard.validation.Username;

public class UsernameCheckForm {

    @Username
    private String username;

    // getter
    public String getUsername() { return username; }

    // setter
    public void setUsername(String username) { this.username = username; }

}