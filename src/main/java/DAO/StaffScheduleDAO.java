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
        String sql = "DELETE FROM StaffSchedule WHERE shift_id = ?";

        try ( Connection conn = getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, shiftId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Không thể xóa ca làm. Shift ID không tồn tại.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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

    public boolean updateSchedule(int scheduleId, String employeeName, String shiftDate, String shiftTime, String status, String notes, String managerName, String replacementEmployeeName) {
    String getUserIdQuery = "SELECT user_id FROM Users WHERE full_name = ?";
    Integer employeeId = null, managerId = null, replacementEmployeeId = null;

    try {
        // 🔍 Tìm employee_id
        try (PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
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
            try (PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
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
            try (PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                ps.setString(1, replacementEmployeeName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    replacementEmployeeId = rs.getInt("user_id");
                } else {
                    System.out.println("⚠ Cảnh báo: Nhân viên thay thế '" + replacementEmployeeName + "' không tồn tại! Sẽ để NULL.");
                }
            }
        }

        // ✅ Cập nhật lịch làm việc trong StaffSchedule
        String updateQuery = "UPDATE StaffSchedule SET employee_id = ?, shift_date = ?, shift_time = ?, status = ?, notes = ?, manager_id = ?, replacement_employee_id = ? WHERE schedule_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
            ps.setInt(1, employeeId);
            ps.setString(2, shiftDate);
            ps.setString(3, shiftTime);
            ps.setString(4, status);
            ps.setString(5, notes);
            ps.setObject(6, (managerId != null) ? managerId : null, java.sql.Types.INTEGER);
            ps.setObject(7, (replacementEmployeeId != null) ? replacementEmployeeId : null, java.sql.Types.INTEGER);
            ps.setInt(8, scheduleId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        }

    } catch (SQLException e) {
        System.out.println("❌ Lỗi khi cập nhật lịch làm việc: " + e.getMessage());
        return false;
    }
}

}
