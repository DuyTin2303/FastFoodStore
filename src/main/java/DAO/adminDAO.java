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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author a
 */
public class adminDAO {

    private DBContext dbContext;

    public adminDAO() {
        DBContext dbContext = new DBContext();
    }

    // Get all accounts with optional filter
    public List<Account> getAllAccounts(String searchKeyword, String roleFilter) {
        List<Account> accounts = new ArrayList<>();

        try {
            String query = "SELECT * FROM Users";
            List<Object> params = new ArrayList<>();

            // Add search filter if provided
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                query += " AND (username LIKE ? OR email LIKE ? OR full_name LIKE ?)";
                String searchParam = "%" + searchKeyword + "%";
                params.add(searchParam);
                params.add(searchParam);
                params.add(searchParam);
            }

            // Add role filter if provided
            if (roleFilter != null && !roleFilter.trim().isEmpty() && !roleFilter.equals("All")) {
                query += " AND role = ?";
                params.add(roleFilter);
            }

            query += " ORDER BY user_id";

            ResultSet rs = dbContext.execSelectQuery(query, params.toArray());

            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getString("google_id"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                accounts.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error getting accounts", ex);
        }

        return accounts;
    }

    // Get account by ID
    public Account getAccountById(int userId) {
        try {
            String query = "SELECT * FROM Users WHERE user_id = ?";
            Object[] params = {userId};

            ResultSet rs = dbContext.execSelectQuery(query, params);

            if (rs.next()) {
                return new Account(
                        rs.getInt("user_id"), rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getString("google_id"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error getting account by ID", ex);
        }

        return null;
    }

    // Add new account
    public boolean addAccount(Account account) {
        try {
            String query = "INSERT INTO Users (username, password_hash, email, full_name, phone_number, address, role, google_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            Object[] params = {
                account.getUsername(),
                account.getPasswordHash(),
                account.getEmail(),
                account.getFullName(),
                account.getPhoneNumber(),
                account.getAddress(),
                account.getRole(),
                account.getGoogleId()
            };

            int result = dbContext.execQuery(query, params);
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error adding account", ex);
            return false;
        }
    }

    // Update account role
    public boolean updateUserRole(int userId, String newRole) {
        try {
            String query = "UPDATE Users SET role = ?, updated_at = GETDATE() WHERE user_id = ?";
            Object[] params = {newRole, userId};

            int result = dbContext.execQuery(query, params);
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error updating user role", ex);
            return false;
        }
    }

    // Update account
    public boolean updateAccount(Account account) {
        try {
            String query = "UPDATE Users SET username = ?, email = ?, full_name = ?, "
                    + "phone_number = ?, address = ?, role = ?, updated_at = GETDATE() "
                    + "WHERE user_id = ?";

            Object[] params = {
                account.getUsername(),
                account.getEmail(),
                account.getFullName(),
                account.getPhoneNumber(),
                account.getAddress(),
                account.getRole(),
                account.getUserId()
            };

            int result = dbContext.execQuery(query, params);
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error updating account", ex);
            return false;
        }
    }

    // Delete account
    public boolean deleteAccount(int userId) {
        try {
            String query = "DELETE FROM Users WHERE user_id = ?";
            Object[] params = {userId};

            int result = dbContext.execQuery(query, params);
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error deleting account", ex);
            return false;
        }
    }

    // Check if username exists
    public boolean isUsernameExists(String username) {
        try {
            String query = "SELECT COUNT(*) AS count FROM Users WHERE username = ?";
            Object[] params = {username};

            ResultSet rs = dbContext.execSelectQuery(query, params);

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error checking username existence", ex);
        }

        return false;
    }

    // Check if email exists
    public boolean isEmailExists(String email) {
        try {
            String query = "SELECT COUNT(*) AS count FROM Users WHERE email = ?";
            Object[] params = {email};

            ResultSet rs = dbContext.execSelectQuery(query, params);

            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error checking email existence", ex);
        }

        return false;
    }

    // Count total accounts
    public int countTotalAccounts() {
        try {
            String query = "SELECT COUNT(*) AS total FROM Users";
            ResultSet rs = dbContext.execSelectQuery(query);

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error counting accounts", ex);
        }

        return 0;
    }

    // Count accounts by role
    public int countAccountsByRole(String role) {
        try {
            String query = "SELECT COUNT(*) AS total FROM Users WHERE role = ?";
            Object[] params = {role};

            ResultSet rs = dbContext.execSelectQuery(query, params);

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminDAO.class.getName()).log(Level.SEVERE, "Error counting accounts by role", ex);
        }

        return 0;
    }
}
