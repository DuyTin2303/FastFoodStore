/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Voucher;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
public class VoucherDAO extends DBContext {

    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        String query = "SELECT voucher_id, name, discount_percentage, valid_from, valid_until, so_luong, status FROM Vouchers";

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                vouchers.add(new Voucher(
                        rs.getInt("voucher_id"),
                        rs.getString("name"),
                        rs.getDouble("discount_percentage"),
                        rs.getDate("valid_from"),
                        rs.getDate("valid_until"),
                        rs.getInt("so_luong"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            // Không in ra màn hình, chỉ ghi log hoặc xử lý ngoại lệ tùy nhu cầu
            e.printStackTrace(); // Có thể thay bằng ghi log hoặc xử lý khác
        }
        return vouchers;
    }

    public boolean createVoucher(String name, double discountPercentage, String validFrom, String validUntil, int soLuong, String status) {
        String query = "INSERT INTO Vouchers (name, discount_percentage, valid_from, valid_until, so_luong, status) VALUES (?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setDouble(2, discountPercentage);
            ps.setString(3, validFrom);
            ps.setString(4, validUntil);
            ps.setInt(5, soLuong);
            ps.setString(6, status);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateVoucher(int voucherId, String name, double discountPercentage,
            String validFrom, String validUntil, int soLuong, String status) {
        String query = "UPDATE Vouchers SET name = ?, discount_percentage = ?, valid_from = ?, "
                + "valid_until = ?, so_luong = ?, status = ? WHERE voucher_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setDouble(2, discountPercentage);
            ps.setString(3, validFrom);
            ps.setString(4, validUntil);
            ps.setInt(5, soLuong);
            ps.setString(6, status);
            ps.setInt(7, voucherId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Voucher getVoucherById(int voucherId) {
        String query = "SELECT voucher_id, name, discount_percentage, valid_from, valid_until, so_luong, status FROM Vouchers WHERE voucher_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, voucherId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Voucher(
                        rs.getInt("voucher_id"),
                        rs.getString("name"),
                        rs.getDouble("discount_percentage"),
                        rs.getDate("valid_from"),
                        rs.getDate("valid_until"),
                        rs.getInt("so_luong"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy voucher
    }

    public boolean hideVoucher(int voucherId) {
        String query = "UPDATE Vouchers SET status = 'Deleted', updated_at = GETDATE() WHERE voucher_id = ?";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, voucherId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Voucher> getActiveUserVouchers(int userId) {
        List<Voucher> vouchers = new ArrayList<>();
        String query = "SELECT "
                + "V.name AS VoucherName, "
                + "V.discount_percentage AS DiscountPercentage, "
                + "V.valid_from AS ValidFrom, "
                + "V.valid_until AS ValidUntil, "
                + "UV.received_at AS ReceivedAt, "
                + "UV.status AS UserVoucherStatus "
                + "FROM UserVouchers UV "
                + "JOIN Vouchers V ON UV.voucher_id = V.voucher_id "
                + "WHERE V.status = 'Active' AND UV.user_id = ?";

        try ( PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setInt(1, userId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Voucher voucher = new Voucher(
                            rs.getString("VoucherName"),
                            rs.getDouble("DiscountPercentage"),
                            rs.getDate("ValidFrom"),
                            rs.getDate("ValidUntil"),
                            rs.getString("UserVoucherStatus"),
                            rs.getTimestamp("ReceivedAt")
                    );
                    vouchers.add(voucher);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    public boolean updateVoucherStatusToUsed(int userId, int voucherId) {
        String query = "UPDATE UserVouchers SET status = 'Used' WHERE user_id = ? AND voucher_id = ?";
        try ( PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, voucherId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateExpiredVouchers() {
        String query = "UPDATE UserVouchers SET status = 'Expired' WHERE status = 'Active' AND valid_until < CURRENT_DATE";
        try ( PreparedStatement ps = getConnection().prepareStatement(query)) {
            int rowsAffected = ps.executeUpdate();
            System.out.println("Số lượng voucher đã cập nhật thành 'Expired': " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean sendVoucherToUser(int userId, int voucherId) {
        String insertQuery = "INSERT INTO UserVouchers (user_id, voucher_id, status, received_at) "
                + "VALUES (?, ?, 'Active', GETDATE())";

        String updateQuery = "UPDATE Vouchers SET so_luong = so_luong - 1 "
                + "WHERE voucher_id = ? AND so_luong > 0";

        try ( PreparedStatement insertPs = conn.prepareStatement(insertQuery);  PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {

            conn.setAutoCommit(false); // Bắt đầu giao dịch

            // Thêm vào UserVouchers
            insertPs.setInt(1, userId);
            insertPs.setInt(2, voucherId);
            int rowsInserted = insertPs.executeUpdate();

            // Cập nhật số lượng voucher
            updatePs.setInt(1, voucherId);
            int rowsUpdated = updatePs.executeUpdate();

            if (rowsInserted > 0 && rowsUpdated > 0) {
                conn.commit(); // Xác nhận giao dịch nếu mọi thứ đều ổn
                return true;
            } else {
                conn.rollback(); // Hủy giao dịch nếu có vấn đề
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // Hủy giao dịch khi xảy ra lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true); // Trả về chế độ tự động commit mặc định
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
