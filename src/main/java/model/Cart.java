package model;

import java.time.LocalDate;
import java.util.List;

public class Cart {

    private int cartId;
    private int userId;
    private LocalDate createdAt;

    private Users user;
    private List<CartItems> cartItems;

    public Cart(int cartId, int userId, LocalDate createAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createAt;
    }

    public Cart(int cartId, int userId, LocalDate createdAt, Users user, List<CartItems> cartItems) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.user = user;
        this.cartItems = cartItems;
    }

    public int getCartId() {
        return cartId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getCreatedAt() {
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
