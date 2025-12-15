package com.saebom.bulletinboard.member.dto;

import com.saebom.bulletinboard.global.domain.Role;

public class LoginMemberView {

    private final Long id;
    private final String username;
    private final String name;
    private final Role role;

    // constructor
    public LoginMemberView(Long id, String username, String name, Role role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    // getter
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public Role getRole() { return role; }

    // helper method
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }


}