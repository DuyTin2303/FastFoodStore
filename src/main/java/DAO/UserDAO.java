package DAO;

import db.DBContext;
import model.Users;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AccountDAO - Handle database operations for Account
 */
public class UserDAO {

    // Register a new account
    public boolean registerAccount(String username, String passwordHash, String email, String fullName, String phoneNumber, String address) {
        DBContext db = new DBContext();
        String query = "INSERT INTO Users (username, passwordHash, email, fullName, phoneNumber, address, role, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, 'user', GETDATE(), GETDATE())";
        
        try {
            int result = db.execQuery(query, new Object[]{username, passwordHash, email, fullName, phoneNumber, address});
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if username exists
    public boolean isUsernameExists(String username) {
        DBContext db = new DBContext();
        String query = "SELECT username FROM Users WHERE username = ?";
        
        try {
            ResultSet rs = db.execSelectQuery(query, new Object[]{username});
            return rs.next(); // Trả về true nếu username đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get Account by username and password
    public Users loginAccount(String username, String passwordHash) {
        DBContext db = new DBContext();
        String query = "SELECT * FROM Users WHERE username = ? AND passwordHash = ?";
        
        try {
            ResultSet rs = db.execSelectQuery(query, new Object[]{username, passwordHash});
            if (rs.next()) {
                return new Users(
                    rs.getInt("userId"),
                    rs.getString("username"),
                    rs.getString("passwordHash"),
                    rs.getString("email"),
                    rs.getString("fullName"),
                    rs.getString("phoneNumber"),
                    rs.getString("address"),
                    rs.getString("role"),
                    rs.getString("googleId"),
                    rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
