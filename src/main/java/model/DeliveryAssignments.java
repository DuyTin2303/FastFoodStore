package model;

import java.time.LocalDateTime;

public class DeliveryAssignments {

    private int assignmentId;
    private int orderId;
    private int deliveryStaffId;
    private LocalDateTime assignedAt;
    private String status;

    public DeliveryAssignments(int assignmentId, int orderId, int deliveryStaffId, LocalDateTime assignedAt, String status) {
        this.assignmentId = assignmentId;
        this.orderId = orderId;
        this.deliveryStaffId = deliveryStaffId;
        this.assignedAt = assignedAt;
        this.status = status;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getDeliveryStaffId() {
        return deliveryStaffId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public String getStatus() {
        return status;
    }

}
