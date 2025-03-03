package model;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class PasswordReset {

    private int tokenId;
    private int userId;
    private String resetToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    private Users user;

    public PasswordReset(int tokenId, int userId, String resetToken, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.resetToken = resetToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public PasswordReset(int tokenId, int userId, String resetToken, LocalDateTime createdAt, LocalDateTime expiresAt, Users user) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.resetToken = resetToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public int getTokenId() {
        return tokenId;
    }

    public int getUserId() {
        return userId;
    }

    public String getResetToken() {
        return resetToken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
