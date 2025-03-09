package DAO;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class accountDAO {

    public boolean registerAccount(String username, String passwordHash, String email, String fullName, String phoneNumber, String address, String role) {
        System.out.println("Đang thử đăng ký user: " + username);
        DBContext db = new DBContext();
        String query = "INSERT INTO Users (username, password_hash, email, full_name, phone_number, address, role, google_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";

        try {
            // Tạo Google ID ngẫu nhiên sử dụng UUID
            String randomGoogleId = UUID.randomUUID().toString();

            int result = db.execQuery(query, new Object[]{username, passwordHash, email, fullName, phoneNumber, address, role, randomGoogleId});
            System.out.println("Kết quả thực thi: " + result);
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUsernameExists(String username) {
        DBContext db = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            db = new DBContext(); // Tạo kết nối mới
            String query = "SELECT * FROM Users WHERE username = ?";

            stmt = db.conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            return rs.next(); // Trả về true nếu có kết quả, false nếu không có
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Đóng các resource theo thứ tự ngược
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (db != null && db.conn != null && !db.conn.isClosed()) {
                    db.conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isEmailExists(String email) {
        DBContext db = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            db = new DBContext(); // Tạo kết nối mới
            String query = "SELECT * FROM Users WHERE email = ?";

            stmt = db.conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            return rs.next(); // Trả về true nếu có kết quả, false nếu không có
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {// Đóng các resource theo thứ tự ngược
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (db != null && db.conn != null && !db.conn.isClosed()) {
                    db.conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPhoneNumberExists(String phoneNumber) {
        DBContext db = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            db = new DBContext(); // Tạo kết nối mới
            String query = "SELECT * FROM Users WHERE phone_number = ?";

            stmt = db.conn.prepareStatement(query);
            stmt.setString(1, phoneNumber);
            rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Đóng các resource theo thứ tự ngược
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (db != null && db.conn != null && !db.conn.isClosed()) {
                    db.conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}