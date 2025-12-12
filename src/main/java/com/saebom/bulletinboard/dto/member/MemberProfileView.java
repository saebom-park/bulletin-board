package com.saebom.bulletinboard.dto.member;

import com.saebom.bulletinboard.domain.Member;

import java.time.LocalDateTime;

public class MemberProfileView {

    private Long id;
    private String username;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemberProfileView from(Member member) {
        MemberProfileView dto = new MemberProfileView();
        dto.id = member.getId();
        dto.username = member.getUsername();
        dto.name = member.getName();
        dto.email = member.getEmail();
        dto.createdAt = member.getCreatedAt();
        dto.updatedAt = member.getUpdatedAt();

        return dto;
    }

    // getter
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

}