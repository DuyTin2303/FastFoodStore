package DAO;

import db.DBContext;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetails;
import model.OrderStatus;
import model.Orders;
import model.Vouchers;
import model.enums.OrderStatusEnum;

public class OrderDAO extends DBContext {

    private VoucherDAO voucherDAO;
    private OrderDetailDAO orderDetailDAO;

    public OrderDAO() {
        voucherDAO = new VoucherDAO();
        orderDetailDAO = new OrderDetailDAO();
    }

    public List<Orders> getAllByUserId(int userId) {
        List<Orders> list = new ArrayList<>();
        String query = "SELECT Orders.*\n"
                + "FROM Orders\n"
                + "WHERE (user_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, userId);
            while (rs.next()) {
                Vouchers voucher = voucherDAO.getById(rs.getInt("voucher_id"));
                List<OrderDetails> orderDetails = orderDetailDAO.getAllByOrderId(rs.getInt("order_id"));
                List<OrderStatus> orderStatuses = orderDetailDAO.getAllStatusByOrderId(rs.getInt("order_id"));

                list.add(new Orders(rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("total_amount"),
                        rs.getInt("voucher_id"),
                        rs.getString("payment_method"),
                        rs.getString("delivery_address"),
                        rs.getDate("estimated_delivery_date").toLocalDate(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getDouble("shipping_fee"),
                        null, voucher, orderDetails, orderStatuses));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Orders getById(int id) {
        String query = "SELECT Orders.*\n"
                + "FROM Orders\n"
                + "WHERE (order_id = ?)";
        try {
            ResultSet rs = execSelectQuery(query, id);
            if (rs.next()) {
                Vouchers voucher = voucherDAO.getById(rs.getInt("voucher_id"));
                List<OrderDetails> orderDetails = orderDetailDAO.getAllByOrderId(id);
                List<OrderStatus> orderStatuses = orderDetailDAO.getAllStatusByOrderId(id);

                return new Orders(rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("total_amount"),
                        rs.getInt("voucher_id"),
                        rs.getString("payment_method"),
                        rs.getString("delivery_address"),
                        rs.getDate("estimated_delivery_date").toLocalDate(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getDouble("shipping_fee"),
                        null, voucher, orderDetails, orderStatuses);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public int add(int userId, double totalAmount, int voucherId, String paymentMethod, String deliveryAddress, LocalDate estimatedDeliveryDate, double shippingFee) {
        String query = "INSERT INTO Orders (user_id, total_amount, voucher_id, payment_method, delivery_address, estimated_delivery_date, created_at, updated_at, shipping_fee)\n"
                + "OUTPUT inserted.order_id\n"
                + "VALUES (?, ?, " + (voucherId == 0 ? "NULL" : voucherId) + ", ?, ?, ?, ?, ?, ?)";
        try {
            ResultSet rs = execSelectQuery(query, userId,
                    totalAmount,
                    paymentMethod,
                    deliveryAddress,
                    estimatedDeliveryDate,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    shippingFee);
            if (rs.next()) {
                return rs.getInt("order_id");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public boolean updateStatus(int orderId, OrderStatusEnum orderStatus) {
        String query = "INSERT INTO OrderStatus (order_id, status, updated_at)\n"
                + "VALUES (?, ?, ?)";
        try {
            return execQuery(query, orderId, orderStatus.toString(), LocalDateTime.now()) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
