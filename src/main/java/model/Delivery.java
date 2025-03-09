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
    private Date estimatedDeliveryDate;
    private String paymentMethod;
    private double totalAmount;
    private String dishName;
    private int quantity;
    private double sellingPrice;
    private double discount;
    private double originalPrice;
    private double itemTotal;
    private double shippingFee;
    private Date orderCreatedAt;
    private Date statusUpdatedAt;
    private int dishId;
    private Date createdAt;
    String deliveryStatus;

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public Delivery(int orderId, Date orderCreatedAt, String customerName, String deliveryAddress,
            Date estimatedDeliveryDate, String paymentMethod, String deliveryStatus,
            double shippingFee, double totalAmount, int dishId, String dishName,
            int quantity, double sellingPrice, double discount, double originalPrice) {
        this.orderId = orderId;
        this.orderCreatedAt = orderCreatedAt;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.paymentMethod = paymentMethod;
        this.status = deliveryStatus;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
        this.dishId = dishId;
        this.dishName = dishName;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.originalPrice = originalPrice;
    }

    public Delivery(int orderId, double totalAmount, String paymentMethod, String deliveryAddress,
            String status, Date statusUpdatedAt, Date orderCreatedAt) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.statusUpdatedAt = statusUpdatedAt;
        this.orderCreatedAt = orderCreatedAt;
    }

    public Delivery(int orderId, double totalAmount, String paymentMethod, String deliveryAddress,
            Date estimatedDeliveryDate, Date orderCreatedAt, String status, Date statusUpdatedAt) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.orderCreatedAt = orderCreatedAt;
        this.status = status;
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public Delivery(int orderId, Date createdAt, String customerName, String deliveryAddress,
            Date estimatedDeliveryDate, String paymentMethod, String status,
            double shippingFee, double totalAmount) {
        this.orderId = orderId;
        this.assignedAt = createdAt; // Sử dụng assignedAt để lưu createdAt
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
    }

    public Delivery(int assignmentId, int orderId, Integer deliveryStaffId, String deliveryStaffName,
            Date assignedAt, String status, Integer shiftId, String deliveryAddress,
            String customerName, String customerPhone, Date estimatedDeliveryDate,
            String paymentMethod, double totalAmount, String dishName, int quantity,
            double sellingPrice, double discount, double originalPrice, double itemTotal) {
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
        this.dishName = dishName;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.originalPrice = originalPrice;
        this.itemTotal = itemTotal;
    }

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
    

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public Date getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(Date orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }

    public Date getStatusUpdatedAt() {
        return statusUpdatedAt;
    }

    public void setStatusUpdatedAt(Date statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(double itemTotal) {
        this.itemTotal = itemTotal;
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
