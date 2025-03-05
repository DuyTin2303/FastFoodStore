package model;

import java.util.Date;

public class Delivery {

    private int assignmentId;
    private int orderId;
    private Integer deliveryStaffId;
    private String deliveryStaffName;
    private Date assignedAt;
    private String status;
    private Integer shiftId;
    private String deliveryAddress;
    private String customerName;
    private String customerPhone;
    private Date estimatedDeliveryDate; // Ngày giao dự kiến
    private String paymentMethod; // Phương thức thanh toán
    private double totalAmount; // Tổng tiền đơn hàng
    private double shippingFee; // Phí giao hàng
    private String dishName; // Tên món ăn
    private int quantity; // Số lượng món ăn

    // Constructor đầy đủ (có thêm các thuộc tính mới)
    public Delivery(int assignmentId, int orderId, Integer deliveryStaffId, String deliveryStaffName,
            Date assignedAt, String status, Integer shiftId, String deliveryAddress,
            String customerName, String customerPhone) {
        this.assignmentId = assignmentId;
        this.orderId = orderId;
        this.deliveryStaffId = deliveryStaffId;
        this.deliveryStaffName = deliveryStaffName;
        this.assignedAt = assignedAt;
        this.status = status;
        this.shiftId = shiftId;
        this.deliveryAddress = deliveryAddress;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    // Constructor đầy đủ
    public Delivery(int assignmentId, int orderId, Integer deliveryStaffId, String deliveryStaffName,
            Date assignedAt, String status, Integer shiftId, String deliveryAddress,
            String customerName, String customerPhone, Date estimatedDeliveryDate,
            String paymentMethod, double totalAmount, double shippingFee,
            String dishName, int quantity) {
        this.assignmentId = assignmentId;
        this.orderId = orderId;
        this.deliveryStaffId = deliveryStaffId;
        this.deliveryStaffName = deliveryStaffName;
        this.assignedAt = assignedAt;
        this.status = status;
        this.shiftId = shiftId;
        this.deliveryAddress = deliveryAddress;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.shippingFee = shippingFee;
        this.dishName = dishName;
        this.quantity = quantity;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter và Setter
    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getDeliveryStaffId() {
        return deliveryStaffId;
    }

    public void setDeliveryStaffId(Integer deliveryStaffId) {
        this.deliveryStaffId = deliveryStaffId;
    }

    public String getDeliveryStaffName() {
        return deliveryStaffName;
    }

    public void setDeliveryStaffName(String deliveryStaffName) {
        this.deliveryStaffName = deliveryStaffName;
    }

    public Date getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Date assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
