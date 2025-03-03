package model;

public class CartItems {

    private int cartItemId;
    private int cartId;
    private int dishId;
    private int quantity;
    
    private Cart cart;
    private Dishes dish;

    public CartItems(int cartItemId, int cartId, int dishId, int quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public CartItems(int cartItemId, int cartId, int dishId, int quantity, Cart cart, Dishes dish) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.cart = cart;
        this.dish = dish;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public int getDishId() {
        return dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public Dishes getDish() {
        return dish;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setDish(Dishes dish) {
        this.dish = dish;
    }
}
