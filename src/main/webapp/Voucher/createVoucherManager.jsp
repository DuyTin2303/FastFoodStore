<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create Voucher</title>
    </head>
    <body>
        <h1>Create Voucher</h1>

        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty errorMessage}">
            <p style="color:red;">${errorMessage}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/Voucher/createVoucherManager" method="POST">
            <input type="text" name="name" placeholder="Voucher Name" required
                   minlength="3" maxlength="100"><br>

            <input type="number" step="0.01" name="discountPercentage" placeholder="Discount Percentage" required
                   min="0" max="100"><br>

            <input type="date" name="validFrom" required><br>
            <input type="date" name="validUntil" required><br>

            <input type="number" name="soLuong" placeholder="Quantity" required
                   min="1" max="1000"><br>

            <select name="status" required>
                <option value="Active">Active</option>
                <option value="Out of stock">Out of stock</option>
            </select><br>

            <button type="submit">Create Voucher</button>
        </form>

        <a href="${pageContext.request.contextPath}/Voucher/viewVoucherManager">Quay lại danh sách Voucher</a>
    </body>
</html>
