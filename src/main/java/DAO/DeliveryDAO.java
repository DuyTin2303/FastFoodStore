package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Delivery;
import db.DBContext;
import java.sql.PreparedStatement;
import model.ShiftRegistration;

public class DeliveryDAO extends DBContext {

    public List<Delivery> getAllDeliveryAssignments() {
        List<Delivery> deliveries = new ArrayList<>();
        String query = "SELECT "
                + "    da.assignment_id, "
                + "    da.order_id, "
                + "    da.delivery_staff_id, "
                + "    u_staff.full_name AS delivery_staff_name, "
                + "    da.assigned_at, "
                + "    da.status, "
                + "    da.shift_id, "
                + "    o.delivery_address, "
                + "    u_customer.full_name AS customer_name, "
                + "    u_customer.phone_number AS customer_phone "
                + "FROM DeliveryAssignments da "
                + "LEFT JOIN Orders o ON da.order_id = o.order_id "
                + "LEFT JOIN Users u_staff ON da.delivery_staff_id = u_staff.user_id "
                + "LEFT JOIN Users u_customer ON o.user_id = u_customer.user_id";

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                deliveries.add(new Delivery(
                        rs.getInt("assignment_id"),
                        rs.getInt("order_id"),
                        rs.getObject("delivery_staff_id") != null ? rs.getInt("delivery_staff_id") : null,
                        rs.getString("delivery_staff_name"),
                        rs.getTimestamp("assigned_at"),
                        rs.getString("status"),
                        rs.getObject("shift_id") != null ? rs.getInt("shift_id") : null,
                        rs.getString("delivery_address"),
                        rs.getString("customer_name"),
                        rs.getString("customer_phone")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách phân công giao hàng: " + e.getMessage());
        }
        return deliveries;
    }

    public void createTriggerAfterInsertOrders() {
        String query = "CREATE TRIGGER trg_AfterInsertOrders "
                + "ON Orders "
                + "AFTER INSERT "
                + "AS "
                + "BEGIN "
                + "    SET NOCOUNT ON; "
                + "    INSERT INTO DeliveryAssignments (order_id) "
                + "    SELECT order_id FROM inserted; "
                + "END;";

        try ( PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.executeUpdate();
            System.out.println("Trigger trg_AfterInsertOrders đã được tạo thành công.");
        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo trigger trg_AfterInsertOrders: " + e.getMessage());
        }
    }

    public void assignDeliveries() {
        String query = "UPDATE da "
                + "SET da.delivery_staff_id = ss.employee_id, "
                + "    da.shift_id = ss.shift_id, "
                + "    da.status = 'Assigned' "
                + "FROM DeliveryAssignments da "
                + "JOIN StaffSchedule ss ON da.shift_id IS NULL "
                + "WHERE da.status = 'Unassigned' "
                + "AND ss.shift_date = CAST(GETDATE() AS DATE) "
                + "AND ( "
                + "    (ss.shift_time = N'8h-13h' AND CAST(GETDATE() AS TIME) BETWEEN '08:00:00' AND '12:59:59') "
                + "    OR (ss.shift_time = N'13h-18h' AND CAST(GETDATE() AS TIME) BETWEEN '13:00:00' AND '17:59:59') "
                + "    OR (ss.shift_time = N'18h-23h' AND CAST(GETDATE() AS TIME) BETWEEN '18:00:00' AND '22:59:59') "
                + ")";

        try {
            int rowsUpdated = execQuery(query, null);
            System.out.println("Số đơn giao hàng được cập nhật: " + rowsUpdated);
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật phân công giao hàng: " + e.getMessage());
        }
    }

    public boolean assignSingleDelivery(int assignmentId) {
        String query = "UPDATE da "
                + "SET da.delivery_staff_id = ss.employee_id, "
                + "    da.shift_id = ss.shift_id, "
                + "    da.status = 'Assigned', "
                + "    da.assigned_at = GETDATE() " // Cập nhật thời gian gán nhân viên
                + "FROM DeliveryAssignments da "
                + "JOIN StaffSchedule ss ON da.shift_id IS NULL "
                + "WHERE da.assignment_id = ? " // Chỉ cập nhật đơn hàng có ID này
                + "AND da.status = 'Unassigned' "
                + "AND ss.shift_date = CAST(GETDATE() AS DATE) "
                + "AND ( "
                + "    (ss.shift_time = N'8h-13h' AND CAST(GETDATE() AS TIME) BETWEEN '08:00:00' AND '12:59:59') "
                + "    OR (ss.shift_time = N'13h-18h' AND CAST(GETDATE() AS TIME) BETWEEN '13:00:00' AND '17:59:59') "
                + "    OR (ss.shift_time = N'18h-23h' AND CAST(GETDATE() AS TIME) BETWEEN '18:00:00' AND '22:59:59') "
                + ")";

        try {
            int rowsUpdated = execQuery(query, new Object[]{assignmentId});
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi gán nhân viên giao hàng: " + e.getMessage());
            return false;
        }
    }

    public List<Delivery> getDeliveriesByStaffName(String staffName) {
        List<Delivery> deliveries = new ArrayList<>();
        String query = "SELECT "
                + "    da.assignment_id, "
                + "    da.status, "
                + "    da.assigned_at, "
                + "    da.order_id, "
                + "    u_staff.full_name AS delivery_staff_name, "
                + "    u_customer.full_name AS customer_name, "
                + "    o.delivery_address, "
                + "    u_customer.phone_number, "
                + "    o.estimated_delivery_date, "
                + "    o.payment_method, "
                + "    o.total_amount, "
                + "    o.shipping_fee, "
                + "    d.dish_name, "
                + "    od.quantity "
                + "FROM DeliveryAssignments da "
                + "JOIN Orders o ON da.order_id = o.order_id "
                + "JOIN Users u_customer ON o.user_id = u_customer.user_id "
                + "JOIN Users u_staff ON da.delivery_staff_id = u_staff.user_id "
                + "JOIN OrderDetails od ON o.order_id = od.order_id "
                + "JOIN Dishes d ON od.dish_id = d.dish_id "
                + "WHERE u_staff.full_name = N'" + staffName + "'"; // Hỗ trợ tiếng Việt nếu có dấu

        try ( ResultSet rs = execSelectQuery(query)) { // Thực thi truy vấn
            while (rs.next()) {
                deliveries.add(new Delivery(
                        rs.getInt("assignment_id"),
                        rs.getInt("order_id"),
                        null, // Không cần delivery_staff_id
                        rs.getString("delivery_staff_name"),
                        rs.getTimestamp("assigned_at"),
                        rs.getString("status"),
                        null, // Không có shift_id trong truy vấn
                        rs.getString("delivery_address"),
                        rs.getString("customer_name"),
                        rs.getString("phone_number"),
                        rs.getDate("estimated_delivery_date"),
                        rs.getString("payment_method"),
                        rs.getDouble("total_amount"),
                        rs.getDouble("shipping_fee"),
                        rs.getString("dish_name"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách đơn giao của nhân viên: " + e.getMessage());
        }
        return deliveries;
    }
    public List<ShiftRegistration> getAllShiftRegistrations() {
    List<ShiftRegistration> shiftRegistrations = new ArrayList<>();
    String query = "SELECT "
            + "    sr.registration_id, "
            + "    u.full_name AS employee_name, "
            + "    sr.shift_date, "
            + "    sr.shift_time, "
            + "    sr.request_status, "
            + "    sr.notes, "
            + "    m.full_name AS manager_name, "
            + "    sr.approval_date, "
            + "    sr.created_date "
            + "FROM ShiftRegistration sr "
            + "JOIN Users u ON sr.employee_id = u.user_id "
            + "LEFT JOIN Users m ON sr.manager_id = m.user_id";

    try (ResultSet rs = execSelectQuery(query)) { // Execute the query
        while (rs.next()) {
            shiftRegistrations.add(new ShiftRegistration(
                    rs.getInt("registration_id"),
                    rs.getString("employee_name"),
                    rs.getDate("shift_date"),
                    rs.getString("shift_time"),
                    rs.getString("request_status"),
                    rs.getString("notes"),
                    rs.getString("manager_name"),
                    rs.getTimestamp("approval_date"),
                    rs.getTimestamp("created_date")
            ));
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving shift registrations: " + e.getMessage());
    }
    return shiftRegistrations;
}


    public boolean updateDeliveryStatus(int assignmentId, String newStatus) {
        String query = "UPDATE DeliveryAssignments SET status = ? WHERE assignment_id = ?";
        Object[] params = {newStatus, assignmentId};

        try {
            int rowsAffected = execQuery(query, params);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật trạng thái đơn giao: " + e.getMessage());
            return false;
        }
    }

}
