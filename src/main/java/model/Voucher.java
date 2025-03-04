/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.Date;

public class Voucher {

    private int voucherId;
    private String name;
    private double discountPercentage;
    private java.sql.Date validFrom;
    private java.sql.Date validUntil;
    private int soLuong;
    private String status;

    // Constructors
    public Voucher() {
    }

    public Voucher(int voucherId, String name, double discountPercentage, java.sql.Date validFrom, java.sql.Date validUntil, int soLuong, String status) {
        this.voucherId = voucherId;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.soLuong = soLuong;
        this.status = status;

    }

    // Getters and Setters
    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public java.sql.Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(java.sql.Date validFrom) {
        this.validFrom = validFrom;
    }

    public java.sql.Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(java.sql.Date validUntil) {
        this.validUntil = validUntil;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
