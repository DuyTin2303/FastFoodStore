package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vouchers {

    private int voucherId;
    private String name;
    private double discountPercentage;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private int soLuong;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Vouchers(int voucherId, String name, double discountPercentage, LocalDate validFrom, LocalDate validUntil, int soLuong, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.voucherId = voucherId;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.soLuong = soLuong;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public String getName() {
        return name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
