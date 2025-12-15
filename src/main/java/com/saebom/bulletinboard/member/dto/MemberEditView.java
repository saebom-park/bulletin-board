package com.saebom.bulletinboard.member.dto;

import java.time.LocalDateTime;

public class MemberEditView {

    private final String username;
    private final String name;
    private final String email;
    private final LocalDateTime updatedAt;

    // constructor
    public MemberEditView(String username, String name,
                          String email, LocalDateTime updatedAt) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.updatedAt = updatedAt;
    }

    // getter
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

}