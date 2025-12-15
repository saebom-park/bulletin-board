package com.saebom.bulletinboard.admin.member.dto;

import com.saebom.bulletinboard.global.domain.Status;

import java.time.LocalDateTime;

public class MemberRowDto {

    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final String role;
    private final Status status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // constructor
    public MemberRowDto(Long id, String username, String name, String email,
                        String role, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getter
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public Status getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

}