<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Delivery" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xem lịch sử giao hàng </title>
</head>
<body>
    <h2>Xem lịch sử giao hàng</h2>

    <table border="1">
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Total Amount</th>
                <th>Payment Method</th>
                <th>Delivery Address</th>
                <th>Estimated Delivery Date</th>
                <th>Order Created At</th>
                <th>Status</th>
                <th>Status Updated At</th>
            </tr>
        </thead>
        <tbody>
            <% 
                // Lấy danh sách deliveries từ request
                List<Delivery> deliveries = (List<Delivery>) request.getAttribute("deliveries");
                if (deliveries != null && !deliveries.isEmpty()) {
                    for (Delivery delivery : deliveries) {
            %>
                        <tr>
                            <td><%= delivery.getOrderId() %></td>
                            <td><%= delivery.getTotalAmount() %></td>
                            <td><%= delivery.getPaymentMethod() %></td>
                            <td><%= delivery.getDeliveryAddress() %></td>
                            <td><%= delivery.getEstimatedDeliveryDate() %></td>
                            <td><%= delivery.getOrderCreatedAt() %></td>
                            <td><%= delivery.getStatus() %></td>
                            <td><%= delivery.getStatusUpdatedAt() %></td>
                        </tr>
            <%      }
                } else { %>
                    <tr><td colspan="8">No delivery status found.</td></tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
