<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách khách hàng</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Danh sách khách hàng</h1>

    <!-- Bảng hiển thị danh sách khách hàng -->
    <table border="1">
        <tr>
            <th>Chọn</th>
            <th>ID</th>
            <th>Tên đăng nhập</th>
            <th>Email</th>
            <th>Họ và tên</th>
            <th>Số điện thoại</th>
            <th>Vai trò</th>
            <th>Hành động</th>
        </tr>

        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>
                    <input type="checkbox" name="selectedCustomers" value="${customer.userId}">
                </td>
                <td>${customer.userId}</td>
                <td>${customer.username}</td>
                <td>${customer.email}</td>
                <td>${customer.fullName}</td>
                <td>${customer.phoneNumber}</td>
                <td>${customer.role}</td>
                <td>
                    <form action="/sendVoucherCustomer" method="POST">
                        <!-- Truyền cả voucherId và userId vào form -->
                        <input type="hidden" name="voucherId" value="${voucherId}">
                        <input type="hidden" name="userId" value="${customer.userId}">
                        <button type="submit">Gửi Voucher</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <a href="index.jsp">Trở về trang chủ</a>
</body>
</html>
