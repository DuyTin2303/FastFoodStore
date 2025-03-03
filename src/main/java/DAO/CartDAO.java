package DAO;

import db.DBContext;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import model.Cart;
import model.CartItems;

public class CartDAO extends DBContext {

    public Cart getByUserId(int userId) {
        String query = "SELECT *\n"
                + "FROM Cart\n"
                + "WHERE (user_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, userId);
            if (rs.next()) {
                Cart cart = new Cart(rs.getInt("cart_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("created_at").toLocalDateTime());
                List<CartItems> cartItems = new CartItemDAO().getAllByCartId(cart.getCartId());
                cart.setCartItems(cartItems);
                return cart;
            } else {
                return add(userId);
            }
        } catch (Exception e) {
        }
        return null;
    }

    // This function return new cart after insert
    private Cart add(int userId) {
        String query = "INSERT INTO Cart (user_id, created_at)\n"
                + "OUTPUT inserted.cart_id, inserted.created_at\n"
                + "VALUES (?, ?)";
        try {

            ResultSet rs = execSelectQuery(query, userId, LocalDateTime.now());
            if (rs.next()) {
                return new Cart(rs.getInt("cart_id"),
                        userId,
                        rs.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (Exception e) {
        }
        return null;
    }
}
