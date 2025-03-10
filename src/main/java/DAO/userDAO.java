package DAO;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class userDAO {

    // Update all SQL queries to match your actual table structure
    public static boolean registerAccount(String username, String passwordHash, String email, String fullName, String phoneNumber, String address) {
        DBContext db = new DBContext();
        String query = "INSERT INTO Users (username, password_hash, email, full_name, phone_number, address, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, 'Customer', GETDATE(), GETDATE())";

        try {
            int result = db.execQuery(query, new Object[]{username, passwordHash, email, fullName, phoneNumber, address});
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update isUsernameExists to match column names
    public static boolean isUsernameExists(String username) {
        DBContext db = new DBContext();
        String query = "SELECT username FROM Users WHERE username = ?";

        try {
            ResultSet rs = db.execSelectQuery(query, new Object[]{username});
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update loginAccount to match column names
    public static Account loginAccount(String username, String passwordHash) {
        DBContext db = new DBContext();
        String query = "SELECT * FROM Users WHERE username = ? AND password_hash = ?";

        try {
            ResultSet rs = db.execSelectQuery(query, new Object[]{username, passwordHash});
            if (rs.next()) {
                return new Account(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getString("google_id"),
                        null,
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Change this to static to match how it's used in the controller
    public static boolean updateAccountProfile(Account account) {
        DBContext db = new DBContext();
        String query = "UPDATE Users SET full_name = ?, email = ?, phone_number = ?, address = ?, updated_at = GETDATE() WHERE user_id = ?";

        try {
            int result = db.execQuery(query, new Object[]{
                account.getFullName(),
                account.getEmail(),
                account.getPhoneNumber(),
                account.getAddress(),
                account.getUserId()});
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update updateUserPassword to match column names
    public static boolean updateUserPassword(int userId, String passwordHash) {
        DBContext db = new DBContext();
        String query = "UPDATE Users SET password_hash = ?, updated_at = GETDATE() WHERE user_id = ?";

        try {
            int result = db.execQuery(query, new Object[]{passwordHash, userId});
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update getAccountById to match column names
    public static Account getAccountById(int userId) {
        DBContext db = new DBContext();
        String query = "SELECT * FROM Users WHERE user_id = ?";

        try {
            ResultSet rs = db.execSelectQuery(query, new Object[]{userId});
            if (rs.next()) {
                return new Account(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getString("google_id"),
                        null,
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả tài khoản có vai trò là 'Customer'
    public static List<Account> getAllCustomers() {
        DBContext db = new DBContext();
        List<Account> customers = new ArrayList<>();
        String query = "SELECT "
                + "user_id AS userId, "
                + "username, "
                + "email, "
                + "full_name AS fullName, "
                + "phone_number AS phoneNumber, "
                + "role "
                + "FROM Users "
                + "WHERE role = ?";

        Object[] params = {"Customer"};

        try ( ResultSet rs = db.execSelectQuery(query, params)) {
            while (rs.next()) {
                customers.add(new Account(
                        rs.getInt("userId"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log hoặc xử lý ngoại lệ tùy nhu cầu
        }
        return customers;
    }

}
