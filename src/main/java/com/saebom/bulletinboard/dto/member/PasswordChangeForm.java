package com.saebom.bulletinboard.dto.member;

import com.saebom.bulletinboard.validation.Password;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class PasswordChangeForm {

    @Password
    private String newPassword;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String confirmPassword;

    private LocalDateTime passwordChangedAt;

    // getter
    public String getNewPassword() { return newPassword; }
    public String getConfirmPassword() { return confirmPassword; }
    public LocalDateTime getPasswordChangedAt() { return passwordChangedAt; }

    // setter
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public void setPasswordChangedAt(LocalDateTime passwordChangedAt) { this.passwordChangedAt = passwordChangedAt; }

}