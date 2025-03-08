package model;

import java.time.LocalDateTime;

public class OrderStatus {

    private int statusId;
    private int orderId;
    private String status;
    private LocalDateTime updatedAt;

    private Orders order;

    public OrderStatus(int statusId, int orderId, String status, LocalDateTime updatedAt) {
        this.statusId = statusId;
        this.orderId = orderId;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public OrderStatus(int statusId, int orderId, String status, LocalDateTime updatedAt, Orders order) {
        this.statusId = statusId;
        this.orderId = orderId;
        this.status = status;
        this.updatedAt = updatedAt;
        this.order = order;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
