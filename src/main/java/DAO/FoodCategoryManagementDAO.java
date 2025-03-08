package DAO;

import db.DBContext;
import model.FoodCategories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodCategoryManagementDAO extends DBContext {

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

    public FoodCategories getCategoryById(int categoryId) {
        String query = "SELECT * FROM FoodCategories WHERE category_id = ?";
        try ( ResultSet rs = execSelectQuery(query, new Object[]{categoryId})) {
            if (rs.next()) {
                return new FoodCategories(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving category: " + e.getMessage());
        }
        return null;
    }

    public boolean addCategory(String name) throws SQLException {
        String query = "INSERT INTO FoodCategories (category_name, created_at, updated_at) VALUES (?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try {
            int rowsInserted = execQuery(query, new Object[]{name});
            return rowsInserted > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("Category name must be unique. This category already exists.", e);
        }
    }

    public boolean updateCategory(int categoryId, String name) throws SQLException {
        String query = "UPDATE FoodCategories SET category_name = ?, updated_at = CURRENT_TIMESTAMP WHERE category_id = ?";
        int rowsUpdated = execQuery(query, new Object[]{name, categoryId});
        return rowsUpdated > 0;

    }

    public boolean deleteCategory(int categoryId) {
        String query = "DELETE FROM FoodCategories WHERE category_id = ?";
        try {
            int rowsDeleted = execQuery(query, new Object[]{categoryId});
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting category: " + e.getMessage());
            return false;
        }
    }
}
