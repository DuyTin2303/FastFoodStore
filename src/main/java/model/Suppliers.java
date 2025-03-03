package model;

import java.time.LocalDateTime;
import java.util.List;

public class Suppliers {

    private int supplierId;
    private String supplierName;
    private String contactInfo;
    private LocalDateTime createdAt;

    private List<Inventory> inventories;

    public Suppliers(int supplierId, String supplierName, String contactInfo, LocalDateTime createdAt) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.createdAt = createdAt;
    }

    public Suppliers(int supplierId, String supplierName, String contactInfo, LocalDateTime createdAt, List<Inventory> inventories) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.createdAt = createdAt;
        this.inventories = inventories;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}
