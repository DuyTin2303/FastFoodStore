package model;

import java.time.LocalDateTime;
import java.util.List;

public class Inventory {

    private int inventoryId;
    private int dishId;
    private int supplierId;
    private int quantity;
    private double purchasePrice;
    private double sellingPrice;
    private LocalDateTime createdAt;

    private Dishes dish;
    private Suppliers supplier;
    private List<OrderDetails> orderDetails;

    public Inventory(int inventoryId, int dishId, int supplierId, int quantity, double purchasePrice, double sellingPrice, LocalDateTime createdAt) {
        this.inventoryId = inventoryId;
        this.dishId = dishId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.createdAt = createdAt;
    }

    public Inventory(int inventoryId, int dishId, int supplierId, int quantity, double purchasePrice, double sellingPrice, LocalDateTime createdAt, Dishes dish, Suppliers supplier, List<OrderDetails> orderDetails) {
        this.inventoryId = inventoryId;
        this.dishId = dishId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.createdAt = createdAt;
        this.dish = dish;
        this.supplier = supplier;
        this.orderDetails = orderDetails;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public int getDishId() {
        return dishId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Dishes getDish() {
        return dish;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setDish(Dishes dish) {
        this.dish = dish;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
