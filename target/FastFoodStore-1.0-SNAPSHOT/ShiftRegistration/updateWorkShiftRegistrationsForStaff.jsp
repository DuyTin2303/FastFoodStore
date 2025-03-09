<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cập Nhật Đơn Đăng Ký Ca Làm</title>
</head>
<body>

    <h2>Cập Nhật Đơn Đăng Ký Ca Làm</h2>

    <!-- Hiển thị thông báo lỗi hoặc thành công -->
    <c:if test="${param.message == 'update_failed'}">
        <p style="color: red;">❌ Cập nhật thất bại. Vui lòng thử lại.</p>
    </c:if>
    <c:if test="${param.message == 'update_success'}">
        <p style="color: green;">✅ Cập nhật thành công!</p>
    </c:if>

    <c:choose>
        <c:when test="${shift != null}">
            <form action="/ShiftRegistration/updateWorkShiftRegistrationsForStaff" method="post">
                <!-- Trường ẩn để truyền ID -->
                <input type="hidden" name="id" value="${shift.registrationId}">

                <label for="employeeName">Tên nhân viên:</label>
                <input type="text" id="employeeName" name="employeeName" value="${shift.employeeName}" required><br><br>

                <label for="startDate">Ngày bắt đầu:</label>
                <input type="date" id="startDate" name="startDate" value="${shift.startDate}" required><br><br>

                <label for="endDate">Ngày kết thúc:</label>
                <input type="date" id="endDate" name="endDate" value="${shift.endDate}" required><br><br>

                <label for="shiftTime">Ca làm:</label>
                <select id="shiftTime" name="shiftTime" required>
                    <option value="8h-13h" ${shift.shiftTime == '8h-13h' ? 'selected' : ''}>8h-13h</option>
                    <option value="13h-18h" ${shift.shiftTime == '13h-18h' ? 'selected' : ''}>13h-18h</option>
                    <option value="18h-23h" ${shift.shiftTime == '18h-23h' ? 'selected' : ''}>18h-23h</option>
                </select><br><br>

                <label for="weekdays">Các ngày làm việc:</label>
                <input type="text" id="weekdays" name="weekdays" value="${shift.weekdays}" required><br><br>

                <label for="requestStatus">Trạng thái yêu cầu:</label>
                <p><strong>${shift.requestStatus}</strong></p>
                <input type="hidden" name="requestStatus" value="${shift.requestStatus}">

                <label for="notes">Ghi chú:</label>
                <textarea id="notes" name="notes">${shift.notes}</textarea><br><br>

                <button type="submit">Cập Nhật</button>
            </form>
        </c:when>
        <c:otherwise>
            <p style="color: red;">❌ Không tìm thấy đơn đăng ký ca làm.</p>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="/ShiftRegistration/viewWorkShiftRegistrationsForStaff">⬅ Quay lại danh sách</a>

</body>
</html>
