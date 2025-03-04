<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="model.Delivery" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách Phân Công Giao Hàng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 20px;
            background-color: #EDF5E1;
        }
        .container {
            width: 80%;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #379683;
            color: white;
        }
        .message {
            color: red;
            font-weight: bold;
        }
        .assign-btn {
            background-color: #ff9800;
            color: white;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 4px;
        }
        .assign-btn:hover {
            background-color: #e68900;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Danh sách Phân Công Giao Hàng</h2>
        
        <% String message = (String) request.getAttribute("message");
           if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <table>
            <tr>
                <th>ID Phân Công</th>
                <th>ID Đơn Hàng</th>
                <th>ID Nhân Viên Giao Hàng</th>
                <th>Thời Gian Giao</th>
                <th>Trạng Thái</th>
                <th>ID Ca Làm</th>
                <th>Tên Khách Hàng</th>
                <th>Số Điện Thoại Giao Hàng</th>
                <th>Hành động</th>
            </tr>
            <%
                List<Delivery> assignments = (List<Delivery>) request.getAttribute("assignments");
                if (assignments != null) {
                    for (Delivery d : assignments) {
            %>
            <tr>
                <td><%= d.getAssignmentId() %></td>
                <td><%= d.getOrderId() %></td>
                <td><%= d.getDeliveryStaffId() != null ? d.getDeliveryStaffId() : "Chưa giao" %></td>
                <td><%= d.getAssignedAt() %></td>
                <td><%= d.getStatus() %></td>
                <td><%= d.getShiftId() != null ? d.getShiftId() : "Chưa có ca làm" %></td>
                <td><%= d.getCustomerName() %></td>
                <td><%= d.getCustomerPhone() %></td>
                <td>
                    <% if ("Unassigned".equals(d.getStatus())) { %>
                        <button class="assign-btn" onclick="assignDelivery(<%= d.getAssignmentId() %>)">Gán</button>
                    <% } else { %>
                        Đã gán
                    <% } %>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="9">Không có dữ liệu</td>
            </tr>
            <%
                }
            %>
        </table>
    </div>

    <script>
        function assignDelivery(assignmentId) {
            if (confirm("Bạn có chắc chắn muốn gán nhân viên cho đơn hàng này?")) {
                fetch("/assignDeliveryManager?assignmentId=" + assignmentId, {
                    method: "POST"
                })
                .then(response => response.text())
                .then(data => {
                    alert(data); // Hiển thị thông báo
                    location.reload(); // Làm mới trang để cập nhật dữ liệu mới
                })
                .catch(error => console.error("Lỗi:", error));
            }
        }
    </script>
</body>
</html>
