package DAO;

import db.DBContext;
import model.Dishes;
import model.FoodCategories;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodManagementDAO extends DBContext {

    public List<Dishes> getAllDishes() {
        List<Dishes> dishesList = new ArrayList<>();
        String sql = "SELECT d.*, c.category_name FROM Dishes d "
                + "INNER JOIN FoodCategories c ON d.category_id = c.category_id";

        try ( ResultSet rs = execSelectQuery(sql)) {
            while (rs.next()) {
                FoodCategories category = new FoodCategories(
                        rs.getInt("category_id"),
                        rs.getString("category_name"), null, null
                );

                dishesList.add(new Dishes(
                        rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("category_id"),
                        rs.getBoolean("availability"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("image_url"),
                        category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishesList;
    }

    public boolean addDish(String dishName, String description, double price, int categoryId, LocalDateTime createdAt, LocalDateTime updatedAt, String imageUrl) throws SQLException {
        String sql = "INSERT INTO Dishes (dish_name, description, price, category_id, availability, created_at, updated_at, image_url) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] params = {dishName, description, price, categoryId, true, Timestamp.valueOf(createdAt), Timestamp.valueOf(updatedAt), imageUrl};

        return execQuery(sql, params) > 0;
    }

    public boolean updateDish(int dishId, String dishName, String description, double price, int categoryId, LocalDateTime updatedAt, String imageUrl) throws SQLException {
        String sql = "UPDATE Dishes SET dish_name = ?, description = ?, price = ?, category_id = ?, updated_at = ?, image_url = ? WHERE dish_id = ?";

        Object[] params = {dishName, description, price, categoryId, Timestamp.valueOf(updatedAt), imageUrl, dishId};

        return execQuery(sql, params) > 0;
    }

    public List<FoodCategories> getAllCategories() {
        List<FoodCategories> categories = new ArrayList<>();
        String query = "SELECT * FROM FoodCategories";

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                categories.add(new FoodCategories(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving categories: " + e.getMessage());
        }
        return categories;
    }

    public Dishes getDishById(int dishId) {
        String sql = "SELECT d.*, c.category_name FROM Dishes d "
                + "INNER JOIN FoodCategories c ON d.category_id = c.category_id WHERE d.dish_id = ?";
        Object[] params = {dishId};

        try ( ResultSet rs = execSelectQuery(sql, params)) {
            if (rs.next()) {
                FoodCategories category = new FoodCategories(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        null, null, null
                );
                return new Dishes(
                        rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("category_id"),
                        rs.getBoolean("availability"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("image_url"),
                        category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean softDeleteDish(int dishId) {
        String sql = "UPDATE Dishes SET availability = 0 WHERE dish_id = ?";
        Object[] params = {dishId};

        try {
            return execQuery(sql, params) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDish(int dishId) {
        String sql = "DELETE FROM Dishes WHERE dish_id = ?";
        Object[] params = {dishId};

        try {
            return execQuery(sql, params) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
