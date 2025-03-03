package DAO;

import db.DBContext;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Dishes;
import model.FoodCategories;
import model.Inventory;
import model.OrderDetails;
import model.OrderStatus;

public class OrderDetailDAO extends DBContext {

    public List<OrderDetails> getAllByOrderId(int orderId) {
        List<OrderDetails> list = new ArrayList<>();
        String query = "SELECT OrderDetails.*,\n"
                + "       Dishes.*,\n"
                + "       FoodCategories.category_name\n"
                + "FROM OrderDetails\n"
                + "INNER JOIN Inventory ON OrderDetails.inventory_id = Inventory.inventory_id\n"
                + "INNER JOIN Dishes ON Inventory.dish_id = Dishes.dish_id\n"
                + "INNER JOIN FoodCategories ON Dishes.category_id = FoodCategories.category_id\n"
                + "WHERE (OrderDetails.order_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, orderId);
            while (rs.next()) {
                FoodCategories category = new FoodCategories(0,
                        rs.getString("category_name"),
                        null, null, null);

                Dishes dish = new Dishes(rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("category_id"),
                        rs.getBoolean("availability"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        category, null);

                Inventory inventory = new Inventory(rs.getInt("inventory_id"),
                        rs.getInt("dish_id"),
                        0, 0, 0, 0, null);
                inventory.setDish(dish);

                list.add(new OrderDetails(rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("inventory_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("selling_price"),
                        rs.getDouble("discount"),
                        rs.getDouble("original_pirce"),
                        null, inventory));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<OrderStatus> getAllStatusByOrderId(int orderId) {
        List<OrderStatus> list = new ArrayList<>();
        String query = "SELECT *\n"
                + "FROM OrderStatus\n"
                + "WHERE (order_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, orderId);
            while (rs.next()) {
                list.add(new OrderStatus(rs.getInt("status_id"),
                        rs.getInt("order_id"),
                        rs.getString("status"),
                        rs.getTimestamp("updated_at").toLocalDateTime()));
            }
        } catch (Exception e) {
        }
        return list;
    }
}
