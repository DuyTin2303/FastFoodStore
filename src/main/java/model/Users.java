/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author a
 */
public class Users {

    private int userId;
    private String username;
    private String passwordHash;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String role;
    private String googleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<PasswordHistory> passwordHistories;
    private List<PasswordReset> passwordResets;

    public Users(int userId, String username, String passwordHash, String email, String fullName, String phoneNumber, String address, String role, String googleId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.googleId = googleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Users(int userId, String username, String passwordHash, String email, String fullName, String phoneNumber, String address, String role, String googleId, LocalDateTime createdAt, LocalDateTime updatedAt, List<PasswordHistory> passwordHistories, List<PasswordReset> passwordResets) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.googleId = googleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.passwordHistories = passwordHistories;
        this.passwordResets = passwordResets;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }

    public String getGoogleId() {
        return googleId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<PasswordHistory> getPasswordHistories() {
        return passwordHistories;
    }

    public List<PasswordReset> getPasswordResets() {
        return passwordResets;
    }

    public void setPasswordHistories(List<PasswordHistory> passwordHistories) {
        this.passwordHistories = passwordHistories;
    }

    public void setPasswordResets(List<PasswordReset> passwordResets) {
        this.passwordResets = passwordResets;
    }
}
