package DAO;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import db.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Users;
import model.MD5;

public class RoleManagementDAO extends DBContext {

    public boolean createUser(String username, String plainPassword, String email, String fullName,
            String phoneNumber, String address, String role) {
        String hashedPassword = MD5.getMd5(plainPassword);

        String sql = "INSERT INTO Users (username, password_hash, email, full_name, phone_number, address, role, google_id, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] params = {username, hashedPassword, email, fullName, phoneNumber, address, role, email,
            LocalDateTime.now(), LocalDateTime.now()};

        try {
            return execQuery(sql, params) > 0;
        } catch (SQLServerException e) {
            if (e.getMessage().contains("UQ__Users__F3DBC572")) { // Replace with actual unique constraint name if different
                throw new RuntimeException("Username already exists. Please choose a different one.");
            } else if (e.getMessage().contains("UQ__Users__AB6E6164C4B9B1F4")) { // If there is a unique constraint on Email
                throw new RuntimeException("Email is already registered. Please use a different email.");
            } else {
                throw new RuntimeException("Database error: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    public boolean assignRole(int userId, String role) {
        String sql = "UPDATE Users SET role = ?, updated_at = ? WHERE user_id = ?";
        Object[] params = {role, LocalDateTime.now(), userId};
        try {
            return execQuery(sql, params) > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<String> getPredefinedRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("Customer");
        roles.add("Admin");
        roles.add("Manager");
        roles.add("Staff");
        return roles;
    }

    public List<Users> getUsers() {
        List<Users> users = new ArrayList<>();
        String query = "SELECT user_id, email, username, full_name, phone_number, address , role, created_at, updated_at FROM Users";

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                Users user = new Users(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        null, // Password is not fetched for security reasons
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("role"),
                        null, // Google ID is not included in this query
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Err:::" + ex);
        }
        return users;
    }

    public Users getUserById(int userId) {
        String query = "SELECT user_id, email, username, full_name, phone_number, address, role, created_at, updated_at FROM Users WHERE user_id = ?";
        Object[] params = {userId};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            if (rs.next()) {
                return new Users(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        null, // Password not included
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("role"),
                        null, // Google ID not included
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching user: " + ex.getMessage());
        }
        return null;
    }

}
