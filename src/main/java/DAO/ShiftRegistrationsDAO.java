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
import model.ShiftRegistration;

/**
 *
 * @author Vo Truong Qui - CE181170
 */
public class ShiftRegistrationsDAO extends DBContext {
    //xem don dang ki c?a nahn vien 

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

    /**
     * Cập nhật thông tin đăng ký ca làm dựa trên registration_id. Kiểm tra xem
     * nhân viên và quản lý có tồn tại hay không dựa vào tên, nếu có thì lấy
     * user_id để cập nhật vào bảng ShiftRegistration.
     *
     * @param registrationId ID của đơn đăng ký cần cập nhật
     * @param employeeName Tên nhân viên
     * @param startDate Ngày bắt đầu ca làm
     * @param endDate Ngày kết thúc ca làm
     * @param shiftTime Thời gian ca làm
     * @param weekdays Các ngày trong tuần làm việc
     * @param requestStatus Trạng thái yêu cầu
     * @param notes Ghi chú
     * @param managerName Tên quản lý
     * @param approvalDate Ngày duyệt
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
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
