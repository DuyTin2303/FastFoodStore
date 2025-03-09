package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Delivery;
import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

    public boolean assignSingleDelivery(int orderId) {
        // Bước 1: Thêm bản ghi vào DeliveryAssignments nếu chưa có
        String insertQuery = "IF NOT EXISTS (SELECT 1 FROM DeliveryAssignments WHERE order_id = ?) "
                + "INSERT INTO DeliveryAssignments (order_id, status) VALUES (?, 'Unassigned')";

        // Bước 2: Cập nhật để gán nhân viên giao hàng
        String updateQuery = "UPDATE da "
                + "SET da.delivery_staff_id = ss.employee_id, "
                + "    da.shift_id = ss.shift_id, "
                + "    da.status = 'Assigned', "
                + "    da.assigned_at = GETDATE() "
                + "FROM DeliveryAssignments da "
                + "JOIN StaffSchedule ss ON da.shift_id IS NULL "
                + "WHERE da.order_id = ? "
                + "AND da.status = 'Unassigned' "
                + "AND ss.shift_date = CAST(GETDATE() AS DATE) "
                + "AND ( "
                + "    (ss.shift_time = N'8h-13h' AND CAST(GETDATE() AS TIME) BETWEEN '08:00:00' AND '12:59:59') "
                + "    OR (ss.shift_time = N'13h-18h' AND CAST(GETDATE() AS TIME) BETWEEN '13:00:00' AND '17:59:59') "
                + "    OR (ss.shift_time = N'18h-23h' AND CAST(GETDATE() AS TIME) BETWEEN '18:00:00' AND '22:59:59') "
                + ")";

        try {
            // Thêm bản ghi vào DeliveryAssignments nếu chưa có
            execQuery(insertQuery, new Object[]{orderId, orderId});

            // Thực hiện cập nhật và in ra số dòng bị ảnh hưởng
            int rowsUpdated = execQuery(updateQuery, new Object[]{orderId});
            System.out.println("Số dòng được cập nhật: " + rowsUpdated);

            if (rowsUpdated > 0) {
                return true;
            } else {
                System.out.println("Không tìm thấy ca làm việc phù hợp hoặc đơn hàng đã được phân công.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL khi gán nhân viên giao hàng: " + e.getMessage());
            throw new RuntimeException("Lỗi phân công đơn hàng: " + e.getMessage());
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

        }
        return deliveries;
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

    //lay don hang de gan cho nmhan vien giao hang 
    public List<Delivery> getUnassignedOrPendingDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        String query = "SELECT "
                + "    o.order_id, "
                + "    o.created_at, "
                + "    u.full_name AS customer_name, "
                + "    o.delivery_address, "
                + "    o.estimated_delivery_date, "
                + "    o.payment_method, "
                + "    COALESCE(da.status, 'Unassigned') AS delivery_status, "
                + "    o.shipping_fee, "
                + "    o.total_amount "
                + "FROM Orders o "
                + "LEFT JOIN Users u ON o.user_id = u.user_id "
                + "LEFT JOIN DeliveryAssignments da ON o.order_id = da.order_id ";

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                deliveries.add(new Delivery(
                        rs.getInt("order_id"),
                        rs.getTimestamp("created_at"),
                        rs.getString("customer_name"),
                        rs.getString("delivery_address"),
                        rs.getTimestamp("estimated_delivery_date"),
                        rs.getString("payment_method"),
                        rs.getString("delivery_status"),
                        rs.getDouble("shipping_fee"),
                        rs.getDouble("total_amount")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách đơn hàng chưa phân công: " + e.getMessage());
        }
        return deliveries;
    }

    // theo doi trang thai don hang cho quan li 
    public List<Delivery> getAllOrdersWithStatus() {
        List<Delivery> ordersWithStatus = new ArrayList<>();

        // SQL query to fetch orders and their statuses
        String query = "SELECT "
                + "    o.order_id, "
                + "    o.total_amount, "
                + "    o.payment_method, "
                + "    o.delivery_address, "
                + "    o.estimated_delivery_date, "
                + "    o.created_at AS order_created_at, "
                + "    os.status AS current_status, "
                + "    os.updated_at AS status_updated_at "
                + "FROM "
                + "    Orders o "
                + "JOIN "
                + "    OrderStatuses os ON o.order_id = os.order_id "
                + "WHERE "
                + "    os.status IN ('Pending', 'Processing', 'Accepted', 'In Transit', 'Delivered', 'Failed') "
                + "ORDER BY "
                + "    os.updated_at DESC";  // Sorting by the latest status update

        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                // Creating a new Delivery object for each order with its status
                ordersWithStatus.add(new Delivery(
                        rs.getInt("order_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("payment_method"),
                        rs.getString("delivery_address"),
                        rs.getDate("estimated_delivery_date"),
                        rs.getTimestamp("order_created_at"),
                        rs.getString("current_status"),
                        rs.getTimestamp("status_updated_at")
                ));
            }
        } catch (SQLException e) {

        }
        return ordersWithStatus;
    }

    public List<Delivery> getAllOrdersWithStatusForManager() {
        List<Delivery> ordersWithStatus = new ArrayList<>();

        // SQL query to fetch orders and their statuses
        String query = "SELECT "
                + "    o.order_id, "
                + "    o.total_amount, "
                + "    o.payment_method, "
                + "    o.delivery_address, "
                + "    os.status, "
                + "    os.updated_at AS status_updated_at, "
                + "    o.created_at AS order_created_at "
                + "FROM "
                + "    Orders o "
                + "JOIN "
                + "    OrderStatuses os ON o.order_id = os.order_id "
                + "WHERE "
                + "    os.status IN ('Pending', 'Processing', 'Accepted', 'In Transit', 'Delivered', 'Failed') "
                + "ORDER BY "
                + "    os.updated_at DESC";  // Sorting by the latest status update

        try {
            // Use execSelectQuery with no parameters
            ResultSet rs = execSelectQuery(query, null);

            while (rs.next()) {
                // Creating a new Delivery object for each order with its status
                ordersWithStatus.add(new Delivery(
                        rs.getInt("order_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("payment_method"),
                        rs.getString("delivery_address"),
                        rs.getString("status"),
                        rs.getTimestamp("status_updated_at"),
                        rs.getTimestamp("order_created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordersWithStatus;
    }

    // Lấy đơn hàng cho nhân viên giao hàng
    public List<Delivery> getOrderDetailsForStaff(int deliveryStaffId) {
        List<Delivery> deliveries = new ArrayList<>();
        String query = "SELECT "
                + "    o.order_id, "
                + "    o.created_at, "
                + "    u.full_name AS customer_name, "
                + "    o.delivery_address, "
                + "    o.estimated_delivery_date, "
                + "    o.payment_method, "
                + "    os.status AS delivery_status, "
                + "    o.shipping_fee, "
                + "    o.total_amount, "
                + "    od.dish_id, "
                + "    d.dish_name, "
                + "    od.quantity, "
                + "    od.selling_price, "
                + "    od.discount, "
                + "    od.original_price "
                + "FROM Orders o "
                + "LEFT JOIN Users u ON o.user_id = u.user_id "
                + "LEFT JOIN OrderStatuses os ON o.order_id = os.order_id "
                + "LEFT JOIN OrderDetails od ON o.order_id = od.order_id "
                + "LEFT JOIN Dishes d ON od.dish_id = d.dish_id "
                + "LEFT JOIN DeliveryAssignments da ON o.order_id = da.order_id "
                + "WHERE da.delivery_staff_id = ? "
                + "AND os.status IN ('Pending', 'Processing', 'Accepted', 'In Transit', 'Delivered', 'Failed')";

        Object[] params = {deliveryStaffId};

        try ( ResultSet rs = execSelectQuery(query, params)) {
            while (rs.next()) {
                Delivery delivery = new Delivery(
                        rs.getInt("order_id"),
                        rs.getTimestamp("created_at"),
                        rs.getString("customer_name"),
                        rs.getString("delivery_address"),
                        rs.getTimestamp("estimated_delivery_date"),
                        rs.getString("payment_method"),
                        rs.getString("delivery_status"),
                        rs.getDouble("shipping_fee"),
                        rs.getDouble("total_amount"),
                        rs.getInt("dish_id"),
                        rs.getString("dish_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("selling_price"),
                        rs.getDouble("discount"),
                        rs.getDouble("original_price")
                );
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy chi tiết đơn hàng giao cho nhân viên: " + e.getMessage());
        }
        return deliveries;
    }
//update status cho nhan vien giao hàng

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String query = "UPDATE OrderStatuses SET status = ? WHERE order_id = ?";
        Object[] params = {newStatus, orderId};

        try {
            int rowsAffected = execQuery(query, params);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage());
            return false;
        }
    }

}
