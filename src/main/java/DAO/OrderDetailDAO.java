package DAO;

import db.DBContext;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Dishes;
import model.FoodCategories;
import model.OrderDetails;
import model.OrderStatus;

public class OrderDetailDAO extends DBContext {

    public List<OrderDetails> getAllByOrderId(int orderId) {
        List<OrderDetails> list = new ArrayList<>();
        String query = "SELECT OrderDetails.*,\n"
                + "       Dishes.*,\n"
                + "       FoodCategories.category_name\n"
                + "FROM OrderDetails\n"
                + "INNER JOIN Dishes ON OrderDetails.dish_id = Dishes.dish_id\n"
                + "INNER JOIN FoodCategories ON Dishes.category_id = FoodCategories.category_id\n"
                + "WHERE (OrderDetails.order_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, orderId);
            while (rs.next()) {
                FoodCategories category = new FoodCategories(rs.getInt("category_id"),
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
                        rs.getString("image_url"),
                        category);

                list.add(new OrderDetails(rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("dish_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("selling_price"),
                        rs.getDouble("discount"),
                        rs.getDouble("original_price"),
                        null, dish));
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

    public boolean add(int orderId, int dishId, int quantity, double sellingPrice, double discount, double originalPrice) {
        String query = "INSERT INTO OrderDetails (order_id, dish_id, quantity, selling_price, discount, original_price)\n"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return execQuery(query, orderId, dishId, quantity, sellingPrice, discount, originalPrice) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
