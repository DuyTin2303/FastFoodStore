package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
public class Vouchers {
    private int voucherId;
    private String code;
    private double discountPercentage;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private boolean isUsed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Vouchers(int voucherId, String code, double discountPercentage, LocalDate validFrom, LocalDate validUntil, boolean isUsed, LocalDateTime createAt, LocalDateTime updateAt) {
        this.voucherId = voucherId;
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.isUsed = isUsed;
        this.createdAt = createAt;
        this.updatedAt = updateAt;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatdeAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
