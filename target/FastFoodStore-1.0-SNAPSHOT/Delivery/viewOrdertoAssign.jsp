<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn hàng chưa phân công</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        button.assign-button {
            padding: 5px 10px;
            color: white;
            background-color: #2196F3;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button.assign-button:hover {
            background-color: #0b7dda;
        }
        a.back-button {
            margin-top: 20px;
            display: inline-block;
            color: #2196F3;
            text-decoration: none;
        }
        a.back-button:hover {
            text-decoration: underline;
        }
        .message {
            margin-top: 20px;
            padding: 10px;
            border-radius: 5px;
        }
        .success {
            color: #155724;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
        }
        .error {
            color: #721c24;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
        }
    </style>
    <script>
        // Gọi AJAX để phân công đơn hàng mà không tải lại trang
        function assignOrder(orderId) {
            if (confirm("Bạn có chắc chắn muốn gán nhân viên giao hàng cho đơn hàng #" + orderId + " không?")) {
                fetch('/assignDeliveryManager', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'orderId=' + encodeURIComponent(orderId)
                })
                .then(response => response.text()) // Chuyển đổi phản hồi từ servlet thành text
                .then(data => {
                    // Hiển thị thông báo dựa trên phản hồi từ servlet
                    const messageContainer = document.getElementById('message');
                    if (data.trim() === 'success') {
                        messageContainer.innerHTML = '<div class="message success">Phân công đơn hàng #' + orderId + ' thành công!</div>';
                        // Ẩn dòng đơn hàng vừa phân công đi
                        document.getElementById('row-' + orderId).remove();
                    } else {
                        messageContainer.innerHTML = '<div class="message error">Không thể phân công đơn hàng #' + orderId + ': ' + data + '</div>';
                    }
                })
                .catch(error => {
                    console.error('Lỗi khi gọi servlet:', error);
                    document.getElementById('message').innerHTML = '<div class="message error">Lỗi khi phân công đơn hàng. Vui lòng thử lại!</div>';
                });
            }
        }
    </script>
</head>
<body>
    <h1>Danh sách đơn hàng chưa phân công giao hàng</h1>

    <div id="message"></div> <!-- Khu vực hiển thị thông báo -->

    <table>
        <tr>
            <th>Mã đơn hàng</th>
            <th>Ngày tạo</th>
            <th>Tên khách hàng</th>
            <th>Địa chỉ giao hàng</th>
            <th>Ngày giao dự kiến</th>
            <th>Phương thức thanh toán</th>
            <th>Trạng thái giao hàng</th>
            <th>Phí vận chuyển</th>
            <th>Tổng giá trị đơn hàng</th>
            <th>Phân công</th>
        </tr>
        <c:forEach var="order" items="${unassignedDeliveries}">
            <tr id="row-${order.orderId}">
                <td>${order.orderId}</td>
                <td>${order.assignedAt}</td>
                <td>${order.customerName}</td>
                <td>${order.deliveryAddress}</td>
                <td>${order.estimatedDeliveryDate}</td>
                <td>${order.paymentMethod}</td>
                <td>${order.status}</td>
                <td>${order.shippingFee}</td>
                <td>${order.totalAmount}</td>
                <td>
                    <button type="button" class="assign-button" onclick="assignOrder(${order.orderId})">
                        Phân công
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>

    <a href="dashboard.jsp" class="back-button">Quay lại Dashboard</a>
</body>
</html>
