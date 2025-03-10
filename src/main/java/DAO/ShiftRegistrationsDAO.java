/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import db.DBContext;
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ShiftRegistration;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
public class ShiftRegistrationsDAO extends DBContext {
     
//xem don dang ki ca lam cho tung nhan vien
    public List<ShiftRegistration> getShiftRegistrationsByUserId(int userId) {
        List<ShiftRegistration> registrations = new ArrayList<>();
        String query = "SELECT sr.registration_id, u.full_name AS employee_name, sr.start_date, sr.end_date, sr.shift_time, "
                + "sr.weekdays, sr.request_status, sr.notes, m.full_name AS manager_name, "
                + "sr.approval_date, sr.created_date "
                + "FROM ShiftRegistration sr "
                + "LEFT JOIN Users u ON sr.employee_id = u.user_id "
                + "LEFT JOIN Users m ON sr.manager_id = m.user_id "
                + "WHERE u.user_id = ?"; // Điều kiện lọc theo user_id

        try {
            // Gọi phương thức execSelectQuery với tham số truyền vào là userId
            ResultSet rs = execSelectQuery(query, new Object[]{userId});

            // Xử lý kết quả trả về từ ResultSet
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
            System.out.println("❌ Lỗi khi lấy danh sách đơn đăng ký ca làm cho user_id " + userId + ": " + e.getMessage());
        }
        return registrations;
    }
    // method lay don dang ki ca lam cho quan li xem de duet
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
//tao don dang ki ca lam cho nhan
    public boolean insertShiftRegistration(String employeeName, String startDate, String endDate, String shiftTime, String weekdays, String notes) {
        String query = "INSERT INTO ShiftRegistration (employee_id, start_date, end_date, shift_time, weekdays, notes) "
                + "VALUES ((SELECT user_id FROM Users WHERE full_name = ?), ?, ?, ?, ?, ?)";

        Object[] params = {employeeName, startDate, endDate, shiftTime, weekdays, notes};

        try {
            int rowsAffected = execQuery(query, params);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi chèn đơn đăng ký ca: " + e.getMessage());
            return false;
        }
    }
    
    //method duet don dang ki ca lam cho nhan vien
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
//method lay id don dang ki de update va xoá
    public ShiftRegistration getShiftRegistrationById(int registrationId) {
        ShiftRegistration registration = null;
        String query = "SELECT sr.registration_id, u.full_name AS employee_name, sr.start_date, sr.end_date, sr.shift_time, "
                + "sr.weekdays, sr.request_status, sr.notes, m.full_name AS manager_name, "
                + "sr.approval_date, sr.created_date "
                + "FROM ShiftRegistration sr "
                + "LEFT JOIN Users u ON sr.employee_id = u.user_id "
                + "LEFT JOIN Users m ON sr.manager_id = m.user_id "
                + "WHERE sr.registration_id = ?";

        try {
            ResultSet rs = execSelectQuery(query, new Object[]{registrationId});
            if (rs.next()) {
                registration = new ShiftRegistration(
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
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy đơn đăng ký ca làm cho registration_id " + registrationId + ": " + e.getMessage());
        }
        return registration;
    }
    //method xoa don dang ki khi chua duet

    public boolean deleteShiftRegistration(int registrationId) {
        String deleteQuery = "DELETE FROM ShiftRegistration WHERE registration_id = ? AND request_status = N'Chờ duyệt'";

        try {
            // Thực thi câu lệnh xóa trực tiếp với điều kiện trạng thái là "Chờ duyệt"
            int result = execQuery(deleteQuery, new Object[]{registrationId});

            // Kiểm tra xem việc xóa có thành công không
            return result > 0;
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa đăng ký ca làm cho registration_id " + registrationId + ": " + e.getMessage());
            return false;
        }
    }

//method update don dang ki chi trang thai la chua duyet
    public boolean updateShiftRegistration(int registrationId, String employeeName, String startDate,
            String endDate, String shiftTime, String weekdays,
            String requestStatus, String notes, String approvalDate) {
        // Kiểm tra xem đăng ký ca làm có tồn tại không
        String checkExistQuery = "SELECT COUNT(*) FROM ShiftRegistration WHERE registration_id = ?";

        try {
            ResultSet rsCheck = execSelectQuery(checkExistQuery, new Object[]{registrationId});
            if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                System.out.println("❌ Không tìm thấy đăng ký ca làm với ID: " + registrationId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kiểm tra tồn tại của đăng ký ca làm: " + e.getMessage());
            return false;
        }

        // Kiểm tra xem nhân viên có tồn tại không
        String getEmployeeIdQuery = "SELECT user_id FROM Users WHERE full_name = ?";
        Integer employeeId = null;

        try {
            ResultSet rsEmployee = execSelectQuery(getEmployeeIdQuery, new Object[]{employeeName});
            if (rsEmployee.next()) {
                employeeId = rsEmployee.getInt("user_id");
            } else {
                System.out.println("❌ Không tìm thấy nhân viên có tên: " + employeeName);
                return false;
            }

            // Cập nhật thông tin đăng ký ca làm (KHÔNG cập nhật manager_id)
            String updateQuery = "UPDATE ShiftRegistration SET employee_id = ?, start_date = ?, end_date = ?, "
                    + "shift_time = ?, weekdays = ?, request_status = ?, notes = ?, approval_date = ? "
                    + "WHERE registration_id = ?";

            int result = execQuery(updateQuery, new Object[]{employeeId, startDate, endDate, shiftTime, weekdays,
                requestStatus, notes, approvalDate, registrationId});

            if (result > 0) {
                System.out.println("✅ Cập nhật đơn đăng ký ca làm thành công cho registration_id: " + registrationId);
                return true;
            } else {
                System.out.println("⚠️ Không có thay đổi nào được thực hiện với registration_id: " + registrationId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật đơn đăng ký ca làm: " + e.getMessage());
            return false;
        }
    }

}
