package model;

import java.time.LocalDateTime;

public class PasswordHistory {

    private int historyId;
    private int userId;
    private String oldPasswordHash;
    private LocalDateTime changedAt;

    private Users user;

    public PasswordHistory(int historyId, int userId, String oldPasswordHash, LocalDateTime changedAt) {
        this.historyId = historyId;
        this.userId = userId;
        this.oldPasswordHash = oldPasswordHash;
        this.changedAt = changedAt;
    }

    public PasswordHistory(int historyId, int userId, String oldPasswordHash, LocalDateTime changedAt, Users user) {
        this.historyId = historyId;
        this.userId = userId;
        this.oldPasswordHash = oldPasswordHash;
        this.changedAt = changedAt;
        this.user = user;
    }

    public int getHistoryId() {
        return historyId;
    }

    public int getUserId() {
        return userId;
    }

    public String getOldPasswordHash() {
        return oldPasswordHash;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
