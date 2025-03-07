package model;

public class OrderDetails {

    private int orderDetailId;
    private int orderId;
    private int dishId;
    private int quantity;
    private double sellingPrice;
    private double discount;
    private double originalPrice;

    private Orders order;
    private Dishes dish;

    public OrderDetails(int orderDetailId, int orderId, int dishId, int quantity, double sellingPrice, double discount, double originalPrice) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.originalPrice = originalPrice;
    }

    public OrderDetails(int orderDetailId, int orderId, int dishId, int quantity, double sellingPrice, double discount, double originalPrice, Orders order, Dishes dish) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.originalPrice = originalPrice;
        this.order = order;
        this.dish = dish;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getDishesId() {
        return dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public Orders getOrder() {
        return order;
    }

    public Dishes getDish() {
        return dish;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setDish(Dishes dish) {
        this.dish = dish;
    }
}
