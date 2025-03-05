package DAO;

import db.DBContext;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CartItems;
import model.Dishes;
import model.FoodCategories;

public class CartItemDAO extends DBContext {

    public List<CartItems> getAllByCartId(int cartId) {
        List<CartItems> list = new ArrayList<>();

        String query = "SELECT CartItems.*,\n"
                + "       Dishes.*,\n"
                + "       FoodCategories.*\n"
                + "FROM CartItems\n"
                + "INNER JOIN Dishes ON CartItems.dish_id = Dishes.dish_id\n"
                + "INNER JOIN FoodCategories ON Dishes.category_id = FoodCategories.category_id\n"
                + "WHERE (CartItems.cart_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, cartId);
            while (rs.next()) {
                FoodCategories category = new FoodCategories(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        null, null);
                Dishes dish = new Dishes(rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("category_id"),
                        rs.getBoolean("availability"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("image_url"),
                        category);

                list.add(new CartItems(rs.getInt("cart_item_id"),
                        rs.getInt("cart_id"),
                        rs.getInt("dish_id"),
                        rs.getInt("quantity"),
                        null, dish));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public CartItems getByCartItemId(int cartItemId) {
        String query = "SELECT CartItems.*,\n"
                + "       Dishes.*,\n"
                + "       FoodCategories.*\n"
                + "FROM CartItems\n"
                + "INNER JOIN Dishes ON CartItems.dish_id = Dishes.dish_id\n"
                + "INNER JOIN FoodCategories ON Dishes.category_id = FoodCategories.category_id\n"
                + "WHERE (CartItems.cart_item_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, cartItemId);
            if (rs.next()) {
                FoodCategories category = new FoodCategories(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        null, null);
                Dishes dish = new Dishes(rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("category_id"),
                        rs.getBoolean("availability"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("image_url"),
                        category);

                return new CartItems(rs.getInt("cart_item_id"),
                        rs.getInt("cart_id"),
                        rs.getInt("dish_id"),
                        rs.getInt("quantity"),
                        null, dish);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public CartItems getByCartIdAndDishId(int cartId, int dishId) {
        String query = "SELECT CartItems.*,\n"
                + "       Dishes.*,\n"
                + "       FoodCategories.*\n"
                + "FROM CartItems\n"
                + "INNER JOIN Dishes ON CartItems.dish_id = Dishes.dish_id\n"
                + "INNER JOIN FoodCategories ON Dishes.category_id = FoodCategories.category_id\n"
                + "WHERE (CartItems.cart_id = ?) AND (CartItems.dish_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, cartId, dishId);
            if (rs.next()) {
                FoodCategories category = new FoodCategories(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        null,
                        null);
                Dishes dish = new Dishes(rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("category_id"),
                        rs.getBoolean("availability"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("image_url"),
                        category);

                return new CartItems(rs.getInt("cart_item_id"),
                        rs.getInt("cart_id"),
                        rs.getInt("dish_id"),
                        rs.getInt("quantity"),
                        null, dish);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean add(int cartId, int dishId, int quantity) {
        String query = "INSERT INTO CartItems (cart_id, dish_id, quantity) VALUES (?, ?, ?)";
        try {
            return execQuery(query, cartId, dishId, quantity) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateQuantity(CartItems cartItem, int quantity) {
        if (quantity <= 0) {
            return delete(cartItem);
        }

        String query = "UPDATE CartItems\n"
                + "SET quantity = ?\n"
                + "WHERE (cart_item_id = ?)";
        try {
            return execQuery(query, quantity, cartItem.getCartItemId()) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(CartItems cartItem) {
        String query = "DELETE\n"
                + "FROM CartItems\n"
                + "WHERE (cart_item_id = ?)";
        try {
            return execQuery(query, cartItem.getCartItemId()) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
