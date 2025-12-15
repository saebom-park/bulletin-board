package com.saebom.bulletinboard.member.dto;

import com.saebom.bulletinboard.global.validation.Username;

public class UsernameCheckForm {

    @Username
    private String username;

    // getter
    public String getUsername() { return username; }

    // setter
    public void setUsername(String username) { this.username = username; }

}