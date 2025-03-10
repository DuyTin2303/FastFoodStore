<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tạo Voucher</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/components/styles.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                max-width: 400px;
                width: 100%;
            }
            h1 {
                color: #333;
            }
            input, select, button {
                width: 100%;
                padding: 10px;
                margin: 8px 0;
                border-radius: 4px;
                border: 1px solid #ddd;
            }
            button {
                background-color: #4CAF50;
                color: white;
                border: none;
                cursor: pointer;
            }
            button:hover {
                background-color: #45a049;
            }
            .error-message {
                color: red;
                margin-bottom: 10px;
            }
            a {
                display: block;
                text-align: center;
                margin-top: 20px;
                color: #4CAF50;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Tạo Voucher</h1>
            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>
            <form action="${pageContext.request.contextPath}/Voucher/createVoucherManager" method="POST">
                <input type="text" name="name" placeholder="Tên Voucher" required minlength="3" maxlength="100">
                <input type="number" step="0.01" name="discountPercentage" placeholder="Phần trăm giảm giá" required min="0" max="100">
                <input type="date" name="validFrom" placeholder="Ngày bắt đầu" required>
                <input type="date" name="validUntil" placeholder="Ngày kết thúc" required>
                <input type="number" name="soLuong" placeholder="Số lượng" required min="1" max="1000">
                <select name="status" required>
                    <option value="Active">Hoạt động</option>
                    <option value="Out of stock">Hết hàng</option>
                </select>
                <button type="submit">Tạo Voucher</button>
            </form>
            <a href="${pageContext.request.contextPath}/Voucher/viewVoucherManager">Quay lại danh sách Voucher</a>
        </div>
    </body>
</html>