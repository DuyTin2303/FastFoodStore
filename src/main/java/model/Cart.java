package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cart {
    
    private int cartId;
    private int userId;
    private LocalDateTime createdAt;
    
    private Users user;
    private List<CartItems> cartItems;
    
    public Cart(int cartId, int userId, LocalDateTime createAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createAt;
        this.cartItems = new ArrayList<>();
    }
    
    public Cart(int cartId, int userId, LocalDateTime createdAt, Users user, List<CartItems> cartItems) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.user = user;
        this.cartItems = cartItems;
    }
    
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartItems cartItem : cartItems) {
            totalQuantity += cartItem.getQuantity();
        }
        return totalQuantity;
    }
    
    public double getTotalAmount() {
        double totalAmount = 0;
        for (CartItems cartItem : cartItems) {
            totalAmount += cartItem.getTotalPrice();
        }
        return totalAmount;
    }
    
    public int getCartId() {
        return cartId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public Users getUser() {
        return user;
    }
    
    public List<CartItems> getCartItems() {
        return cartItems;
    }
    
    public void setUser(Users user) {
        this.user = user;
    }
    
    public void setCartItems(List<CartItems> cartItems) {
        this.cartItems = cartItems;
    }
}
