/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import model.Account;
import db.DBContext;
import model.MD5;

/**
 *
 * @author a
 */
public class loginDAO {

    private DBContext dbContext;

    public loginDAO() {
        dbContext = new DBContext();
    }

    public Account verifyCredentials(String username, String password) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dbContext.getConnection();
            String sql = "SELECT user_id, username, password_hash, email, full_name, phone_number, address, role, google_id, created_at, updated_at "
                    + "FROM Users WHERE username = ? AND password_hash = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, MD5.getMd5(password)); // Mã hóa mật khẩu

            System.out.println("Đang kiểm tra tài khoản: " + username);
            System.out.println("SQL Query: " + ps.toString());

            rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                System.out.println("Tài khoản hợp lệ, Role: " + role);

                return new Account(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        role, // Gán role vào Account
                        rs.getString("google_id"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            } else {
                System.out.println("Sai tên đăng nhập hoặc mật khẩu!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    public boolean registerUser(String username, String password, String email, String role) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbContext.getConnection();
            String sql = "INSERT INTO Users (username, password_hash, email, role) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);ps.setString(2, MD5.getMd5(password)); // Băm mật khẩu bằng MD5
            ps.setString(3, email);
            ps.setString(4, role);
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public String getFullname(String username) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dbContext.getConnection();
            String sql = "SELECT full_name FROM Users WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("full_name");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    public boolean getEmail(String email, String user) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dbContext.getConnection();
            String sql = "SELECT * FROM Users WHERE email = ? AND username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, user);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void updatePassword(String password, String username) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dbContext.getConnection();
            String sql = "UPDATE Users SET password_hash = ? WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, MD5.getMd5(password)); // Băm mật khẩu mới bằng MD5
            ps.setString(2, username);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean isAccountExists(String username) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dbContext.getConnection();
            String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    // Method for cookie-based authentication
    public Account getAccountByUsername(String username) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dbContext.getConnection();
            String sql = "SELECT user_id, username, email, full_name, phone_number, address, role, google_id, created_at, updated_at "
                    + "FROM Users WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");

                return new Account(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        null, // Don't retrieve password for security
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        role,
                        rs.getString("google_id"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Database error: " + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }
}