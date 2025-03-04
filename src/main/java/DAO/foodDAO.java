/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import db.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.FoodItem;

public class foodDAO {

    private final DBContext dbContext = new DBContext();

    // Lấy tất cả danh mục món ăn từ bảng FoodCategories
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT category_name FROM FoodCategories";

        try ( ResultSet rs = dbContext.execSelectQuery(query)) {
            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Lấy tất cả món ăn từ bảng Dishes
    public List<FoodItem> getAllFood() {
        List<FoodItem> result = getFoodByQuery("SELECT d.dish_id, d.dish_name, c.category_name, d.price, d.review, d.image_url "
                + "FROM Dishes d JOIN FoodCategories c ON d.category_id = c.category_id");

        System.out.println("Debug - getAllFood found: " + (result == null ? "null" : result.size()) + " items");
        if (result != null && !result.isEmpty()) {
            System.out.println("Sample item: " + result.get(0).getName());
        }

        return result;
    }

    private List<FoodItem> getFoodByQuery(String query, Object... params) {
        List<FoodItem> foodList = new ArrayList<>();
        try ( ResultSet rs = dbContext.execSelectQuery(query, params)) {
            int count = 0;
            while (rs.next()) {
                count++;
                FoodItem item = mapResultSetToFoodItem(rs);
                foodList.add(item);
                System.out.println("Debug - Lấy được món: " + item.getName() + ", giá: " + item.getPrice());
            }
            System.out.println("Debug - Tổng số món lấy được: " + count);
        } catch (SQLException e) {
            System.out.println("Debug - Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return foodList;
    }

    // Lọc món ăn theo danh mục
    public List<FoodItem> getFoodByCategory(String category) {
        return getFoodByQuery("SELECT d.dish_id, d.dish_name, c.category_name, d.price, d.review, d.image_url "
                + "FROM Dishes d JOIN FoodCategories c ON d.category_id = c.category_id WHERE c.category_name = ?",
                new Object[]{category});
    }

    // Lọc món ăn theo giá
    public List<FoodItem> getFoodByPrice(double maxPrice) {
        return getFoodByQuery("SELECT d.dish_id, d.dish_name, c.category_name, d.price, d.review, d.image_url "+ "FROM Dishes d JOIN FoodCategories c ON d.category_id = c.category_id WHERE d.price <= ?",
                new Object[]{maxPrice});
    }

    // Tìm kiếm món ăn theo tên
    public List<FoodItem> searchFoodByName(String name) {
        return getFoodByQuery("SELECT d.dish_id, d.dish_name, c.category_name, d.price, d.review, d.image_url "
                + "FROM Dishes d JOIN FoodCategories c ON d.category_id = c.category_id WHERE d.dish_name LIKE ?",
                new Object[]{"%" + name + "%"});
    }

    // Lấy món ăn theo ID
    public FoodItem getFoodById(int id) {
        String query = "SELECT d.dish_id, d.dish_name, c.category_name, d.price, d.review, d.image_url "
                + "FROM Dishes d JOIN FoodCategories c ON d.category_id = c.category_id WHERE d.dish_id = ?";

        try ( ResultSet rs = dbContext.execSelectQuery(query, new Object[]{id})) {
            if (rs.next()) {
                return mapResultSetToFoodItem(rs);
            }
        } catch (SQLException e) {
            System.out.println("Debug - Error getting food by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Thêm các phương thức mới để sắp xếp theo giá
    public List<FoodItem> getFoodByPriceAscending() {
        return getFoodByQuery("SELECT d.dish_id, d.dish_name, c.category_name, d.price, d.review, d.image_url "
                + "FROM Dishes d JOIN FoodCategories c ON d.category_id = c.category_id ORDER BY d.price ASC");
    }

    public List<FoodItem> getFoodByPriceDescending() {
        return getFoodByQuery("SELECT d.dish_id, d.dish_name, c.category_name, d.price, d.review, d.image_url "
                + "FROM Dishes d JOIN FoodCategories c ON d.category_id = c.category_id ORDER BY d.price DESC");
    }

    // Thêm phương thức để lọc theo khoảng giá
    public List<FoodItem> getFoodByPriceRange(double minPrice, double maxPrice) {
        return getFoodByQuery("SELECT d.dish_id, d.dish_name, c.category_name, d.price, d.review, d.image_url "
                + "FROM Dishes d JOIN FoodCategories c ON d.category_id = c.category_id WHERE d.price >= ? AND d.price <= ?",
                new Object[]{minPrice, maxPrice});
    }

    // Chuyển đổi ResultSet thành đối tượng FoodItem
    private FoodItem mapResultSetToFoodItem(ResultSet rs) throws SQLException {
        return new FoodItem(
                rs.getInt("dish_id"),
                rs.getString("dish_name"),
                rs.getString("category_name"),
                rs.getDouble("price"),
                rs.getString("review"),
                rs.getString("image_url")
        );
    }
}