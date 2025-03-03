package model;

public class OrderDetails {

    private int orderDetailId;
    private int orderId;
    private int inventoryId;
    private int quantity;
    private double sellingPrice;
    private double discount;
    private double originalPrice;

    private Orders order;
    private Inventory inventory;

    public OrderDetails(int orderDetailId, int orderId, int inventoryId, int quantity, double sellingPrice, double discount, double originalPrice) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.originalPrice = originalPrice;
    }

    public OrderDetails(int orderDetailId, int orderId, int inventoryId, int quantity, double sellingPrice, double discount, double originalPrice, Orders order, Inventory inventory) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.originalPrice = originalPrice;
        this.order = order;
        this.inventory = inventory;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getInventoryId() {
        return inventoryId;
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
