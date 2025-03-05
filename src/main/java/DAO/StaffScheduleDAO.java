/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ShiftRegistration;
import model.StaffSchedule;

public class StaffScheduleDAO extends DBContext {

    public List<StaffSchedule> getAllSchedules() {
        List<StaffSchedule> schedules = new ArrayList<>();
        String query = "SELECT ss.shift_id, ss.employee_id, u1.full_name AS employee_name, ss.shift_date, ss.shift_time, ss.status, ss.notes, ss.manager_id, "
                + "u2.full_name AS replacement_employee_name "
                + // 🔹 Lấy tên nhân viên thay thế
                "FROM StaffSchedule ss "
                + "JOIN Users u1 ON ss.employee_id = u1.user_id "
                + "LEFT JOIN Users u2 ON ss.replacement_employee_id = u2.user_id"; // 🔹 LEFT JOIN để tránh lỗi khi không có người thay thế

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                schedules.add(new StaffSchedule(
                        rs.getInt("shift_id"),
                        rs.getInt("employee_id"),
                        rs.getString("employee_name"),
                        rs.getString("shift_date"),
                        rs.getString("shift_time"),
                        rs.getString("status"),
                        rs.getString("notes"),
                        rs.getObject("manager_id") != null ? rs.getInt("manager_id") : null,
                        rs.getString("replacement_employee_name") // 🔹 Lấy tên thay vì ID
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách lịch làm việc: " + e.getMessage());
        }
        return schedules;
    }

    public void deleteSchedule(int shiftId) throws SQLException {
        String updateDeliveryAssignmentsSql = "UPDATE DeliveryAssignments SET shift_id = NULL WHERE shift_id = ?";
        String deleteScheduleSql = "DELETE FROM StaffSchedule WHERE shift_id = ?";

        try ( Connection conn = getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            try ( PreparedStatement updateStmt = conn.prepareStatement(updateDeliveryAssignmentsSql);  PreparedStatement deleteStmt = conn.prepareStatement(deleteScheduleSql)) {

                // Bước 1: Cập nhật shift_id về NULL trong DeliveryAssignments
                updateStmt.setInt(1, shiftId);
                updateStmt.executeUpdate();

                // Bước 2: Xóa bản ghi trong StaffSchedule
                deleteStmt.setInt(1, shiftId);
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected == 0) {
                    conn.rollback(); // Hoàn tác nếu không xóa được
                    throw new SQLException("Không thể xóa ca làm. Shift ID không tồn tại.");
                }

                conn.commit(); // Xác nhận giao dịch
            } catch (SQLException e) {
                conn.rollback(); // Hoàn tác nếu có lỗi
                e.printStackTrace();
                throw e;
            } finally {
                conn.setAutoCommit(true); // Khôi phục chế độ tự động commit
            }
        }
    }

    public boolean addSchedule(String employeeName, String shiftDate, String shiftTime, String status, String notes, String managerName, String replacementEmployeeName) {
        String getUserIdQuery = "SELECT user_id FROM Users WHERE full_name = ?";
        Integer employeeId = null, managerId = null, replacementEmployeeId = null;

        try {
            // 🔍 Tìm employee_id
            try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                ps.setString(1, employeeName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    employeeId = rs.getInt("user_id");
                } else {
                    System.out.println("❌ Lỗi: Nhân viên '" + employeeName + "' không tồn tại!");
                    return false;
                }
            }

            // 🔍 Tìm manager_id (nếu có)
            if (managerName != null && !managerName.isEmpty()) {
                try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                    ps.setString(1, managerName);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        managerId = rs.getInt("user_id");
                    } else {
                        System.out.println("⚠ Cảnh báo: Quản lý '" + managerName + "' không tồn tại! Sẽ để NULL.");
                    }
                }
            }

            // 🔍 Tìm replacement_employee_id (nếu có)
            if (replacementEmployeeName != null && !replacementEmployeeName.isEmpty()) {
                try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                    ps.setString(1, replacementEmployeeName);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        replacementEmployeeId = rs.getInt("user_id");
                    } else {
                        System.out.println("⚠ Cảnh báo: Nhân viên thay thế '" + replacementEmployeeName + "' không tồn tại! Sẽ để NULL.");
                    }
                }
            }

            // ✅ Thêm lịch làm việc vào StaffSchedule
            String insertQuery = "INSERT INTO StaffSchedule (employee_id, shift_date, shift_time, status, notes, manager_id, replacement_employee_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try ( PreparedStatement ps = conn.prepareStatement(insertQuery)) {
                ps.setInt(1, employeeId);
                ps.setString(2, shiftDate);
                ps.setString(3, shiftTime);
                ps.setString(4, status);
                ps.setString(5, notes);
                if (managerId != null) {
                    ps.setInt(6, managerId);
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }
                if (replacementEmployeeId != null) {
                    ps.setInt(7, replacementEmployeeId);
                } else {
                    ps.setNull(7, java.sql.Types.INTEGER);
                }

                int rowsInserted = ps.executeUpdate();
                return rowsInserted > 0;
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm lịch làm việc: " + e.getMessage());
            return false;
        }
    }

    public boolean isValidManager(String managerName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE TRIM(full_name) = ? AND role = 'Manager'";

        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, managerName);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu tìm thấy Manager, ngược lại false
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    public boolean updateSchedule(int shiftId, String employeeName, String managerName,
            String replacementEmployeeName, String shiftDate,
            String shiftTime, String status, String notes) {
        String updateQuery = ""
                + "IF EXISTS (SELECT 1 FROM Users WHERE full_name = ?) "
                + "AND EXISTS (SELECT 1 FROM Users WHERE full_name = ?) "
                + "AND EXISTS (SELECT 1 FROM Users WHERE full_name = ?) "
                + "BEGIN "
                + "    UPDATE ss "
                + "    SET ss.employee_id = u1.user_id, "
                + "        ss.manager_id = u2.user_id, "
                + "        ss.replacement_employee_id = u3.user_id, "
                + "        ss.shift_date = ?, "
                + "        ss.shift_time = ?, "
                + "        ss.status = ?, "
                + "        ss.notes = ? "
                + "    FROM StaffSchedule ss "
                + "    JOIN Users u1 ON u1.full_name = ? "
                + "    JOIN Users u2 ON u2.full_name = ? "
                + "    LEFT JOIN Users u3 ON u3.full_name = ? "
                + "    WHERE ss.shift_id = ?; "
                + "END "
                + "ELSE "
                + "BEGIN "
                + "    PRINT 'Một trong các tên nhân viên, quản lý hoặc nhân viên thay thế không tồn tại'; "
                + "END";

        try ( PreparedStatement ps = conn.prepareStatement(updateQuery)) {
            // Kiểm tra tồn tại của các tên trong bảng Users
            ps.setString(1, employeeName);
            ps.setString(2, managerName);
            ps.setString(3, replacementEmployeeName);

            // Gán giá trị cho câu lệnh UPDATE
            ps.setString(4, shiftDate);
            ps.setString(5, shiftTime);
            ps.setString(6, status);
            ps.setString(7, notes);

            // Gán giá trị cho các tham số JOIN
            ps.setString(8, employeeName);
            ps.setString(9, managerName);
            ps.setString(10, replacementEmployeeName);

            // Gán giá trị cho shift_id
            ps.setInt(11, shiftId);

            // Thực thi câu lệnh
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ShiftRegistration> getAllShiftRegistrations() {
        List<ShiftRegistration> shiftRegistrations = new ArrayList<>();
        String query = "SELECT "
                + "sr.registration_id, "
                + "u.full_name AS employee_name, "
                + "sr.shift_date, "
                + "sr.shift_time, "
                + "sr.request_status, "
                + "sr.notes, "
                + "m.full_name AS manager_name, "
                + "sr.approval_date, "
                + "sr.created_date "
                + "FROM ShiftRegistration sr "
                + "JOIN Users u ON sr.employee_id = u.user_id "
                + "LEFT JOIN Users m ON sr.manager_id = m.user_id"; // LEFT JOIN để lấy tên manager nếu có

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                shiftRegistrations.add(new ShiftRegistration(
                        rs.getInt("registration_id"),
                        rs.getString("employee_name"),
                        rs.getDate("shift_date"),
                        rs.getString("shift_time"),
                        rs.getString("request_status"),
                        rs.getString("notes"),
                        rs.getString("manager_name"),
                        rs.getDate("approval_date"),
                        rs.getDate("created_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách đăng ký ca làm việc: " + e.getMessage());
        }
        return shiftRegistrations;
    }

    public boolean insertShiftRegistration(String employeeName, String shiftDate, String shiftTime, String notes) {
        String query = "INSERT INTO ShiftRegistration (employee_id, shift_date, shift_time, notes) "
                + "VALUES ((SELECT user_id FROM Users WHERE full_name = ?), ?, ?, ?)";

        try ( PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setString(1, employeeName); // Tên nhân viên
            ps.setString(2, shiftDate);    // Ngày đăng ký ca làm
            ps.setString(3, shiftTime);    // Khung giờ đăng ký
            ps.setString(4, notes);        // Ghi chú thêm

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi chèn đơn đăng ký ca: " + e.getMessage());
            return false;
        }
    }

    public boolean duyetCaLam(int registrationId, String managerName, String requestStatus) {
        String checkManagerQuery = "SELECT user_id FROM Users WHERE full_name = ?";
        String updateQuery = "UPDATE sr "
                + "SET sr.manager_id = u2.user_id, "
                + "sr.approval_date = GETDATE(), "
                + "sr.request_status = ? " // Thêm tham số trạng thái ở đây
                + "FROM ShiftRegistration sr "
                + "JOIN Users u2 ON u2.full_name = ? "
                + "WHERE sr.registration_id = ?";

        try ( Connection conn = getConnection();  PreparedStatement checkStmt = conn.prepareStatement(checkManagerQuery)) {
            checkStmt.setString(1, managerName);  // Truyền tên quản lý vào câu truy vấn
            try ( ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Nếu quản lý tồn tại, thực hiện cập nhật
                    int managerId = rs.getInt("user_id");

                    try ( PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, requestStatus);  // Truyền trạng thái vào câu truy vấn
                        updateStmt.setString(2, managerName);  // Truyền tên quản lý vào câu truy vấn
                        updateStmt.setInt(3, registrationId);  // Truyền ID đăng ký ca làm việc

                        int affectedRows = updateStmt.executeUpdate();
                        return affectedRows > 0;
                    }
                } else {
                    System.out.println("❌ Quản lý không tồn tại: " + managerName);
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi từ chối đơn đăng ký ca làm việc: " + e.getMessage());
            return false;
        }
    }
 public List<ShiftRegistration> getShiftRegistrationsByEmployeeName(String employeeName) {
    List<ShiftRegistration> registrations = new ArrayList<>();
    String query = "SELECT sr.*, u.full_name AS employee_name, "
                 + "m.full_name AS manager_name "
                 + "FROM ShiftRegistration sr "
                 + "JOIN Users u ON sr.employee_id = u.user_id "
                 + "LEFT JOIN Users m ON sr.manager_id = m.user_id "
                 + "WHERE u.full_name LIKE N?";
    try (PreparedStatement ps = getConnection().prepareStatement(query)) {
        ps.setString(1, "%" + employeeName + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            registrations.add(new ShiftRegistration(
                rs.getInt("registration_id"),
                rs.getString("employee_name"),     // Đảm bảo có cột "employee_name"
                rs.getDate("shift_date"),
                rs.getString("shift_time"),
                rs.getString("request_status"),
                rs.getString("notes"),
                rs.getString("manager_name"),      // Đảm bảo có cột "manager_name"
                rs.getDate("approval_date"),
                rs.getDate("created_date")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Nên thêm log để biết lỗi chi tiết
    }
    return registrations;
}


}
