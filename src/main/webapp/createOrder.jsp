<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tạo Đơn Hàng</title>
</head>
<body>

<h2>Tạo Đơn Hàng</h2>

<% if (request.getAttribute("message") != null) { %>
    <p style="color: green;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
    <p style="color: red;"><%= request.getAttribute("error") %></p>
<% } %>

<form action="createOrder" method="post">
    <label for="user_id">Mã khách hàng:</label>
    <input type="number" id="user_id" name="user_id" required><br>

    <label for="total_amount">Tổng tiền:</label>
    <input type="number" id="total_amount" name="total_amount" step="0.01" required><br>

    <label for="payment_method">Hình thức thanh toán:</label>
    <select id="payment_method" name="payment_method" required>
        <option value="Cash">Tiền mặt</option>
        <option value="Online">Trực tuyến</option>
    </select><br>

    <label for="delivery_address">Địa chỉ giao hàng:</label>
    <input type="text" id="delivery_address" name="delivery_address"><br>

    <label for="estimated_delivery_date">Ngày giao dự kiến:</label>
    <input type="date" id="estimated_delivery_date" name="estimated_delivery_date"><br>

    <button type="submit">Tạo Đơn Hàng</button>
</form>

</body>
</html>
