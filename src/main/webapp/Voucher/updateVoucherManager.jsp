<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cập Nhật Voucher</title>
    </head>
    <body>
        <h1>Cập Nhật Voucher</h1>

        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty errorMessage}">
            <p style="color:red;">${errorMessage}</p>
        </c:if>

        <c:if test="${not empty voucher}">
            <form action="${pageContext.request.contextPath}/Voucher/updateVoucherManager" method="POST">
                <input type="hidden" name="voucherId" value="${voucher.voucherId}" />

                <label>Tên Voucher:</label><br>
                <input type="text" name="name" value="${voucher.name}" required><br>

                <label>Phần Trăm Giảm Giá:</label><br>
                <input type="number" step="0.01" name="discountPercentage" 
                       value="${voucher.discountPercentage}" required min="0" max="100"><br>

                <label>Ngày Bắt Đầu:</label><br>
                <input type="date" name="validFrom" value="${voucher.validFrom}" required><br>

                <label>Ngày Hết Hạn:</label><br>
                <input type="date" name="validUntil" value="${voucher.validUntil}" required><br>

                <label>Số Lượng:</label><br>
                <input type="number" name="soLuong" value="${voucher.soLuong}" required min="1"><br>

                <label>Trạng Thái:</label><br>
                <select name="status" required>
                    <option value="Active" ${voucher.status == 'Active' ? 'selected' : ''}>Active</option>
                    <option value="Out of stock" ${voucher.status == 'Out of stock' ? 'selected' : ''}>Out of stock</option>
                </select><br><br>

                <button type="submit">Cập Nhật Voucher</button>
            </form>
        </c:if>

        <br>
        <a href="${pageContext.request.contextPath}/Voucher/viewVoucherManager">Quay lại danh sách Voucher</a>
    </body>
</html>
