<%-- 
    Document   : assignDeliveryStaff.jsp
    Created on : Feb 18, 2025, 6:44:41 PM
    Author     : Vo Truong Qui - CE181170
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gán Nhân Viên Giao Hàng</title>
</head>
<body>
    <h2>Gán Nhân Viên Giao Hàng</h2>
    
    <form action="assignDeliveryStaff" method="post">
        <input type="hidden" name="orderId" value="${orderId}">
        
        <label for="deliveryStaffId">Chọn Nhân Viên Giao Hàng:</label>
        <select name="deliveryStaffId">
            <c:forEach var="staff" items="${deliveryStaff}">
                <option value="${staff.user_id}">${staff.name} - ${staff.email}</option>
            </c:forEach>
        </select>
        
        <button type="submit">Gán Nhân Viên</button>
    </form>
</body>
</html>
