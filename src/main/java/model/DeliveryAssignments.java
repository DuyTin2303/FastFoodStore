package model;

import java.time.LocalDateTime;

public class DeliveryAssignments {

    private int assignmentId;
    private int orderId;
    private int deliveryStaffId;
    private LocalDateTime assignedAt;
    private String status;
    
    private Orders order;
    private Users user;

    public DeliveryAssignments(int assignmentId, int orderId, int deliveryStaffId, LocalDateTime assignedAt, String status) {
        this.assignmentId = assignmentId;
        this.orderId = orderId;
        this.deliveryStaffId = deliveryStaffId;
        this.assignedAt = assignedAt;
        this.status = status;
    }

    public DeliveryAssignments(int assignmentId, int orderId, int deliveryStaffId, LocalDateTime assignedAt, String status, Orders order, Users user) {
        this.assignmentId = assignmentId;
        this.orderId = orderId;
        this.deliveryStaffId = deliveryStaffId;
        this.assignedAt = assignedAt;
        this.status = status;
        this.order = order;
        this.user = user;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Orders getOrder() {
        return order;
    }

    public int getDeliveryStaffId() {
        return deliveryStaffId;
    }

    public Users getUser() {
        return user;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
