package com.saebom.bulletinboard.member.dto;

import com.saebom.bulletinboard.global.domain.Status;

public class MemberAuthView {

    private final Long id;
    private final String password;
    private final Status status;

    // constructor
    public MemberAuthView(Long id, String password, Status status) {
        this.id = id;
        this.password = password;
        this.status = status;
    }

    // getter
    public Long getId() { return id; }
    public String getPassword() { return password; }
    public Status getStatus() { return status; }

}