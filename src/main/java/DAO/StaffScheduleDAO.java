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
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.ShiftRegistration;

import model.StaffSchedule;

public class StaffScheduleDAO extends DBContext {

    public List<StaffSchedule> getAllSchedules() {
        List<StaffSchedule> schedules = new ArrayList<>();
        String query = "SELECT ss.shift_id, ss.employee_id, u1.full_name AS employee_name, ss.shift_date, ss.shift_time, "
                + "ss.status, ss.notes, ss.manager_id, u2.full_name AS replacement_employee_name "
                + "FROM StaffSchedule ss "
                + "JOIN Users u1 ON ss.employee_id = u1.user_id "
                + "LEFT JOIN Users u2 ON ss.replacement_employee_id = u2.user_id "
                + "ORDER BY ss.shift_date ASC, "
                + "CASE "
                + "WHEN ss.shift_time = '8h-13h' THEN 1 "
                + "WHEN ss.shift_time = '13h-18h' THEN 2 "
                + "WHEN ss.shift_time = '18h-23h' THEN 3 "
                + "ELSE 4 END ASC";

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
                        rs.getString("replacement_employee_name")
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
            // 🔍 Lấy employee_id
            try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                ps.setString(1, employeeName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    employeeId = rs.getInt("user_id");
                } else {
                    return false; // Nhân viên không tồn tại
                }
            }

            // 🔍 Lấy manager_id (nếu có)
            if (managerName != null && !managerName.isEmpty()) {
                try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                    ps.setString(1, managerName);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        managerId = rs.getInt("user_id");
                    }
                }
            }

            // 🔍 Lấy replacement_employee_id (nếu có)
            if (replacementEmployeeName != null && !replacementEmployeeName.isEmpty()) {
                try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                    ps.setString(1, replacementEmployeeName);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        replacementEmployeeId = rs.getInt("user_id");
                    }
                }
            }

            // 🛑 Kiểm tra nhân viên đã có lịch trong ca làm đó chưa
            String checkExistingShiftQuery = "SELECT COUNT(*) FROM StaffSchedule "
                    + "WHERE employee_id = ? AND shift_date = ? AND shift_time = ?";
            try ( PreparedStatement ps = conn.prepareStatement(checkExistingShiftQuery)) {
                ps.setInt(1, employeeId);
                ps.setString(2, shiftDate);
                ps.setString(3, shiftTime);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // Nhân viên đã có lịch trong ca làm đó
                }
            }

            // 🛑 Kiểm tra số lượng nhân viên trong ca làm việc đã đủ 4 người chưa
            String checkShiftCapacityQuery = "SELECT COUNT(*) FROM StaffSchedule "
                    + "WHERE shift_date = ? AND shift_time = ?";
            try ( PreparedStatement ps = conn.prepareStatement(checkShiftCapacityQuery)) {
                ps.setString(1, shiftDate);
                ps.setString(2, shiftTime);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) >= 4) {
                    return false; // Ca làm đã đủ 4 người
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
                ps.setObject(6, managerId, java.sql.Types.INTEGER);
                ps.setObject(7, replacementEmployeeId, java.sql.Types.INTEGER);

                int rowsInserted = ps.executeUpdate();
                return rowsInserted > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Giữ lại thông báo lỗi trong log để dễ debug
        }
        return false;
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

    private Integer getUserIdByName(String fullName) throws SQLException {
        if (fullName == null || fullName.isEmpty()) {
            return null;
        }
        String query = "SELECT user_id FROM Users WHERE full_name = ?";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        }
        return null; // Người dùng không tồn tại
    }

    private boolean isEmployeeAlreadyScheduled(int employeeId, String shiftDate, String shiftTime, int shiftId) throws SQLException {
        String query = "SELECT COUNT(*) FROM StaffSchedule "
                + "WHERE employee_id = ? AND shift_date = ? AND shift_time = ? AND shift_id != ?";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, employeeId);
            ps.setString(2, shiftDate);
            ps.setString(3, shiftTime);
            ps.setInt(4, shiftId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private boolean isShiftCapacityFull(String shiftDate, String shiftTime, int shiftId) throws SQLException {
        String query = "SELECT COUNT(*) FROM StaffSchedule "
                + "WHERE shift_date = ? AND shift_time = ? AND shift_id != ?";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, shiftDate);
            ps.setString(2, shiftTime);
            ps.setInt(3, shiftId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) >= 4;
        }
    }

    private boolean updateShiftSchedule(int shiftId, Integer employeeId, Integer managerId, Integer replacementEmployeeId,
            String shiftDate, String shiftTime, String status, String notes) throws SQLException {
        String updateQuery = "UPDATE ss "
                + "SET ss.employee_id = ?, "
                + "    ss.manager_id = ?, "
                + "    ss.replacement_employee_id = ?, "
                + "    ss.shift_date = ?, "
                + "    ss.shift_time = ?, "
                + "    ss.status = ?, "
                + "    ss.notes = ? "
                + "FROM StaffSchedule ss "
                + "WHERE ss.shift_id = ?";

        try ( PreparedStatement ps = conn.prepareStatement(updateQuery)) {
            ps.setInt(1, employeeId);
            ps.setObject(2, managerId, java.sql.Types.INTEGER);
            ps.setObject(3, replacementEmployeeId, java.sql.Types.INTEGER);
            ps.setString(4, shiftDate);
            ps.setString(5, shiftTime);
            ps.setString(6, status);

            // Xử lý notes có thể null
            if (notes == null || notes.isEmpty()) {
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else {
                ps.setString(7, notes);
            }

            ps.setInt(8, shiftId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateSchedule(int shiftId, String employeeName, String managerName,
            String replacementEmployeeName, String shiftDate,
            String shiftTime, String status, String notes) {

        String getUserIdQuery = "SELECT user_id FROM Users WHERE full_name = ?";
        Integer employeeId = null, managerId = null, replacementEmployeeId = null;

        try {
            // 🔍 Lấy employee_id
            try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                ps.setString(1, employeeName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    employeeId = rs.getInt("user_id");
                } else {
                    return false; // Nhân viên không tồn tại
                }
            }

            // 🔍 Lấy manager_id (nếu có)
            if (managerName != null && !managerName.isEmpty()) {
                try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                    ps.setString(1, managerName);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        managerId = rs.getInt("user_id");
                    }
                }
            }

            // 🔍 Lấy replacement_employee_id (nếu có)
            if (replacementEmployeeName != null && !replacementEmployeeName.isEmpty()) {
                try ( PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
                    ps.setString(1, replacementEmployeeName);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        replacementEmployeeId = rs.getInt("user_id");
                    }
                }
            }

            // 🛑 Kiểm tra nhân viên đã có lịch trong ca làm đó chưa (bỏ qua chính ca đang cập nhật)
            String checkExistingShiftQuery = "SELECT COUNT(*) FROM StaffSchedule "
                    + "WHERE employee_id = ? AND shift_date = ? AND shift_time = ? AND shift_id != ?";
            try ( PreparedStatement ps = conn.prepareStatement(checkExistingShiftQuery)) {
                ps.setInt(1, employeeId);
                ps.setString(2, shiftDate);
                ps.setString(3, shiftTime);
                ps.setInt(4, shiftId); // Bỏ qua ca hiện tại đang cập nhật
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // Nhân viên đã có lịch trong ca làm đó
                }
            }

            // 🛑 Kiểm tra số lượng nhân viên trong ca làm việc đã đủ 4 người chưa (bỏ qua chính ca đang cập nhật)
            String checkShiftCapacityQuery = "SELECT COUNT(*) FROM StaffSchedule "
                    + "WHERE shift_date = ? AND shift_time = ? AND shift_id != ?";
            try ( PreparedStatement ps = conn.prepareStatement(checkShiftCapacityQuery)) {
                ps.setString(1, shiftDate);
                ps.setString(2, shiftTime);
                ps.setInt(3, shiftId); // Bỏ qua ca hiện tại đang cập nhật
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) >= 4) {
                    return false; // Ca làm đã đủ 4 người
                }
            }

            // ✅ Thực hiện cập nhật lịch làm việc
            String updateQuery = "UPDATE ss "
                    + "SET ss.employee_id = ?, "
                    + "    ss.manager_id = ?, "
                    + "    ss.replacement_employee_id = ?, "
                    + "    ss.shift_date = ?, "
                    + "    ss.shift_time = ?, "
                    + "    ss.status = ?, "
                    + "    ss.notes = ? "
                    + "FROM StaffSchedule ss "
                    + "WHERE ss.shift_id = ?";

            try ( PreparedStatement ps = conn.prepareStatement(updateQuery)) {
                ps.setInt(1, employeeId);
                ps.setObject(2, managerId, java.sql.Types.INTEGER);
                ps.setObject(3, replacementEmployeeId, java.sql.Types.INTEGER);
                ps.setString(4, shiftDate);
                ps.setString(5, shiftTime);
                ps.setString(6, status);

                // Xử lý notes có thể null
                if (notes == null || notes.isEmpty()) {
                    ps.setNull(7, java.sql.Types.VARCHAR);
                } else {
                    ps.setString(7, notes);
                }

                ps.setInt(8, shiftId);

                int rowsUpdated = ps.executeUpdate();
                return rowsUpdated > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertShiftRegistration(String employeeName, String startDate, String endDate, String shiftTime, String weekdays, String notes) {
        String query = "INSERT INTO ShiftRegistration (employee_id, start_date, end_date, shift_time, weekdays, notes) "
                + "VALUES ((SELECT user_id FROM Users WHERE full_name = ?), ?, ?, ?, ?, ?)";

        try ( PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setString(1, employeeName); // Tên nhân viên
            ps.setString(2, startDate);    // Ngày bắt đầu đăng ký
            ps.setString(3, endDate);      // Ngày kết thúc đăng ký
            ps.setString(4, shiftTime);    // Khung giờ đăng ký
            ps.setString(5, weekdays);     // Các ngày trong tuần
            ps.setString(6, notes);        // Ghi chú thêm

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi chèn đơn đăng ký ca: " + e.getMessage());
            return false;
        }
    }

    public static boolean isUsernameExistsRoleStaff(String username) {
        DBContext db = new DBContext();
        String query = "SELECT username FROM Users WHERE username = ? AND role = 'Staff'";

        try {
            ResultSet rs = db.execSelectQuery(query, new Object[]{username});
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Cập nhật phương thức getAllShiftRegistrations trong StaffScheduleDAO
    public List<ShiftRegistration> getAllShiftRegistrations() {
        List<ShiftRegistration> registrations = new ArrayList<>();
        String query = "SELECT sr.registration_id, u.full_name AS employee_name, sr.start_date, sr.end_date, sr.shift_time, "
                + "sr.weekdays, sr.request_status, sr.notes, m.full_name AS manager_name, "
                + "sr.approval_date, sr.created_date "
                + "FROM ShiftRegistration sr "
                + "LEFT JOIN Users u ON sr.employee_id = u.user_id "
                + "LEFT JOIN Users m ON sr.manager_id = m.user_id";

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                registrations.add(new ShiftRegistration(
                        rs.getInt("registration_id"),
                        rs.getString("employee_name"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("shift_time"),
                        rs.getString("weekdays"),
                        rs.getString("request_status"),
                        rs.getString("notes"),
                        rs.getString("manager_name"),
                        rs.getString("approval_date"),
                        rs.getString("created_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách đơn đăng ký ca làm: " + e.getMessage());
        }
        return registrations;
    }


    public boolean approveShiftRegistration(int registrationId, HttpSession session) {
        String query
                = "BEGIN TRANSACTION; "
                + "UPDATE ShiftRegistration "
                + "SET request_status = N'Đã duyệt', manager_id = ?, approval_date = GETDATE() "
                + "WHERE registration_id = ?; "
                + "INSERT INTO StaffSchedule (employee_id, shift_date, shift_time, status, manager_id, registration_id) "
                + "SELECT sr.employee_id, shift_dates.shift_date, sr.shift_time, N'Chưa bắt đầu', ?, sr.registration_id "
                + "FROM ShiftRegistration sr "
                + "CROSS APPLY ( "
                + "    SELECT DATEADD(DAY, n, sr.start_date) AS shift_date "
                + "    FROM ( "
                + "        SELECT TOP (DATEDIFF(DAY, sr.start_date, sr.end_date) + 1) "
                + "        ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1 AS n "
                + "        FROM master.dbo.spt_values "
                + "    ) AS num "
                + "    WHERE CHARINDEX(CAST(DATEPART(WEEKDAY, DATEADD(DAY, n, sr.start_date)) AS NVARCHAR), sr.weekdays) > 0 "
                + ") AS shift_dates "
                + "WHERE sr.registration_id = ? "
                + "AND NOT EXISTS ( "
                + "    SELECT 1 "
                + "    FROM StaffSchedule ss "
                + "    WHERE ss.shift_date = shift_dates.shift_date "
                + "    AND ss.shift_time = sr.shift_time "
                + "    AND ss.employee_id = sr.employee_id "
                + "); "
                + "IF @@ERROR = 0 "
                + "BEGIN "
                + "    COMMIT TRANSACTION; "
                + "END "
                + "ELSE "
                + "BEGIN "
                + "    ROLLBACK TRANSACTION; "
                + "END;";

        try {
            int managerId = (int) session.getAttribute("userId");
            Object[] params = {managerId, registrationId, managerId, registrationId};

            int rowsAffected = execQuery(query, params);

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void autoUpdateShiftStatus() {
        String updateQuery
                = "UPDATE StaffSchedule SET status = "
                + "CASE "
                + "    WHEN GETDATE() >= CAST(shift_date AS DATETIME) + "
                + "         CASE shift_time "
                + "             WHEN N'8h-13h' THEN '13:00:00' "
                + "             WHEN N'13h-18h' THEN '18:00:00' "
                + "             WHEN N'18h-23h' THEN '23:00:00' "
                + "         END "
                + "         THEN N'Đã hoàn thành' "
                + "    WHEN GETDATE() >= CAST(shift_date AS DATETIME) + "
                + "         CASE shift_time "
                + "             WHEN N'8h-13h' THEN '08:00:00' "
                + "             WHEN N'13h-18h' THEN '13:00:00' "
                + "             WHEN N'18h-23h' THEN '18:00:00' "
                + "         END "
                + "         AND GETDATE() < CAST(shift_date AS DATETIME) + "
                + "         CASE shift_time "
                + "             WHEN N'8h-13h' THEN '13:00:00' "
                + "             WHEN N'13h-18h' THEN '18:00:00' "
                + "             WHEN N'18h-23h' THEN '23:00:00' "
                + "         END "
                + "         THEN N'Đang làm' "
                + "    WHEN GETDATE() < CAST(shift_date AS DATETIME) + '08:00:00' "
                + "         THEN N'Chưa bắt đầu' "
                + "    ELSE status "
                + "END "
                + "WHERE status != N'Vắng mặt';";

        try {
            execQuery(updateQuery, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //lay danh sach ca lam cho tung nhan vien 
    public List<StaffSchedule> getAllSchedules(int userId) {
        List<StaffSchedule> schedules = new ArrayList<>();
        String query = "SELECT ss.shift_id, u1.full_name AS employee_name, ss.shift_date, ss.shift_time, "
                + "ss.status, ss.notes, u2.full_name AS manager_name, u3.full_name AS replacement_employee_name "
                + "FROM StaffSchedule ss "
                + "JOIN Users u1 ON ss.employee_id = u1.user_id "
                + "LEFT JOIN Users u2 ON ss.manager_id = u2.user_id "
                + "LEFT JOIN Users u3 ON ss.replacement_employee_id = u3.user_id "
                + "WHERE ss.employee_id = ? "
                + "ORDER BY ss.shift_date ASC, "
                + "CASE "
                + "WHEN ss.shift_time = '8h-13h' THEN 1 "
                + "WHEN ss.shift_time = '13h-18h' THEN 2 "
                + "WHEN ss.shift_time = '18h-23h' THEN 3 "
                + "ELSE 4 END ASC";

        Object[] params = new Object[]{userId};

        try ( ResultSet rs = execSelectQuery(query, params)) {
            while (rs.next()) {
                schedules.add(new StaffSchedule(
                        rs.getInt("shift_id"),
                        rs.getString("employee_name"), // Employee name instead of ID
                        rs.getString("shift_date"),
                        rs.getString("shift_time"),
                        rs.getString("status"),
                        rs.getString("notes"),
                        rs.getString("manager_name"), // Manager name instead of ID
                        rs.getString("replacement_employee_name") // Replacement employee name instead of ID
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách lịch làm việc: " + e.getMessage());
        }
        return schedules;
    }
}
