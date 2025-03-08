package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Orders {

    private int orderId;
    private int userId;
    private double totalAmount;
    private int voucherId;
    private String paymentMethod;
    private String deliveryAddress;
    private LocalDate estimatedDeliveryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double shippingFee;

    private Users user;
    private Vouchers voucher;
    private List<OrderDetails> orderDetails;
    private List<OrderStatus> orderStatuses;

    public Orders(int orderId, int userId, double totalAmount, int voucherId, String paymentMethod, String deliveryAddress, LocalDate estimatedDeliveryDate, LocalDateTime createdAt, LocalDateTime updatedAt, double shippingFee) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.voucherId = voucherId;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.shippingFee = shippingFee;
        this.orderDetails = new ArrayList<>();
        this.orderStatuses = new ArrayList<>();
    }

    public Orders(int orderId, int userId, double totalAmount, int voucherId, String paymentMethod, String deliveryAddress, LocalDate estimatedDeliveryDate, LocalDateTime createdAt, LocalDateTime updatedAt, double shippingFee, Users user, Vouchers voucher, List<OrderDetails> orderDetails, List<OrderStatus> orderStatuses) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.voucherId = voucherId;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.shippingFee = shippingFee;
        this.user = user;
        this.voucher = voucher;
        this.orderDetails = orderDetails;
        this.orderStatuses = orderStatuses;
    }

    public int getTotalQuantity() {
        int quantity = 0;
        for (OrderDetails orderDetail : orderDetails) {
            quantity += orderDetail.getQuantity();
        }
        return quantity;
    }

    public double getOriginalPrice() {
        double price = 0;
        for (OrderDetails orderDetail : orderDetails) {
            price += orderDetail.getOriginalPrice();
        }
        return price;
    }

    public double getDiscountPrice() {
        double price = 0;
        for (OrderDetails orderDetail : orderDetails) {
            price += orderDetail.getDiscount();
        }
        return price;
    }

    public OrderStatus getLastStatus() {
        return !orderStatuses.isEmpty() ? orderStatuses.get(orderStatuses.size() - 1) : null;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Users getUser() {
        return user;
    }

    public Vouchers getVoucher() {
        return voucher;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public List<OrderStatus> getOrderStatuses() {
        return orderStatuses;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setVoucher(Vouchers voucher) {
        this.voucher = voucher;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setOrderStatuses(List<OrderStatus> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }
}
