package com.saebom.bulletinboard.domain;

import java.time.LocalDateTime;

public class Member {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime passwordChangedAt;
    private LocalDateTime lastLoginAt;
    private Integer loginFailCount;
    private Boolean accountLocked;
    private LocalDateTime accountLockedAt;
    private Status status;

    // constructor
    public Member() {

    }

    public Member(String username, String password, String name, String email, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // getter
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getPasswordChangedAt() { return passwordChangedAt; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public Integer getLoginFailCount() { return loginFailCount; }
    public Boolean getAccountLocked() { return accountLocked; }
    public LocalDateTime getAccountLockedAt() { return accountLockedAt; }
    public Status getStatus() { return status; }

    // setter
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) {
        if (email != null && email.isBlank()) {
            this.email = null;
        } else {
            this.email = email;
        }
    }
    public void setRole(String role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setPasswordChangedAt(LocalDateTime passwordChangedAt) { this.passwordChangedAt = passwordChangedAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    public void setLoginFailCount(Integer loginFailCount) { this.loginFailCount = loginFailCount; }
    public void setAccountLocked(Boolean accountLocked) { this.accountLocked = accountLocked; }
    public void setAccountLockedAt(LocalDateTime accountLockedAt) { this.accountLockedAt = accountLockedAt; }
    public void setStatus(Status status) { this.status = status; }

}