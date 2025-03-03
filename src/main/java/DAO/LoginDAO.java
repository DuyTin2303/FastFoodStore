/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import model.Users;
import db.DBContext;
import utils.MD5;
/**
 *
 * @author a
 */
public class LoginDAO{
    private DBContext dbContext;

    public LoginDAO() {
        dbContext = new DBContext();
    }

    public Users verifyCredentials(String username, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dbContext.getConnection();
            String sql = "SELECT * FROM Users WHERE username = ? AND password_hash = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, MD5.getMd5(password)); // Mã hóa mật khẩu với MD5

            rs = ps.executeQuery();
            if (rs.next()) {
                return new Users(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("email"),
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("address"),
                    rs.getString("role"),
                    rs.getString("google_id"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
        return null;
    }

    public boolean registerUser(String username, String password, String email) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbContext.getConnection();
            String sql = "INSERT INTO Users (username, password_hash, email) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, MD5.getMd5(password)); // Băm mật khẩu bằng MD5
            ps.setString(3, email);
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }
}