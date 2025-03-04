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
}
