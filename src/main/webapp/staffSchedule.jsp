<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Lịch Làm Việc Nhân Viên</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                padding: 0;
                background-color: #EDF5E1;
            }
            h2 {
                text-align: center;
                color: #05386B;
            }
            .btn-container {
                text-align: right;
                margin-bottom: 10px;
            }
            .btn-container button {
                background-color: #5CDB95;
                color: white;
                border: none;
                padding: 8px 15px;
                cursor: pointer;
                border-radius: 5px;
            }
            .btn-container button:hover {
                background-color: #379683;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background: white;
                margin-top: 10px;
                box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
            }
            th, td {
                border: 1px solid #379683;
                padding: 8px;
                text-align: center;
            }
            th {
                background-color: #379683;
                color: white;
            }
            .status-completed {
                color: #5CDB95;
                font-weight: bold;
            }
            .status-in-progress {
                color: #05386B;
                font-weight: bold;
            }
            .status-absent {
                color: #E74C3C;
                font-weight: bold;
            }
            .update-btn, .delete-btn {
                background-color: #5CDB95;
                color: white;
                border: none;
                padding: 5px 10px;
                cursor: pointer;
                border-radius: 5px;
                margin-right: 5px;
            }
            .update-btn:hover {
                background-color: #379683;
            }
            .delete-btn {
                background-color: #E74C3C;
            }
            .delete-btn:hover {
                background-color: #C0392B;
            }
        </style>
    </head>
    <body>
        <h2>Lịch Làm Việc Nhân Viên</h2>

        <div class="btn-container">
            <button onclick="location.href = 'createStaffSchedule'">Tạo Lịch Mới</button>

        </div>

        <c:if test="${not empty message}">
            <p style="color: green; text-align: center;">${message}</p>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>ID Ca</th>
                    <th>Tên Nhân Viên</th>
                    <th>Ngày Làm Việc</th>
                    <th>Giờ Làm Việc</th>
                    <th>Trạng Thái</th>
                    <th>Ghi Chú</th>
                    <th>ID Quản Lý</th>
                    <th>Nhân Viên Thay Thế</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="schedule" items="${schedules}">
                    <tr>
                        <td>${schedule.shiftId}</td>
                        <td>${schedule.employeeName}</td>
                        <td>${schedule.shiftDate}</td>
                        <td>${schedule.shiftTime}</td>
                        <td>
                            <c:choose>
                                <c:when test="${schedule.status == 'Đã hoàn thành'}">
                                    <span class="status-completed">${schedule.status}</span>
                                </c:when>
                                <c:when test="${schedule.status == 'Đang làm'}">
                                    <span class="status-in-progress">${schedule.status}</span>
                                </c:when>
                                <c:when test="${schedule.status == 'Vắng mặt'}">
                                    <span class="status-absent">${schedule.status}</span>
                                </c:when>
                                <c:otherwise>
                                    ${schedule.status}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${schedule.notes}</td>
                        <td>${schedule.managerId}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty schedule.replacementEmployeeName}">
                                    ${schedule.replacementEmployeeName}
                                </c:when>
                                <c:otherwise>
                                    Không có
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                           <button class="update-btn" onclick="location.href = 'updateStaffSchedule?shiftId=${schedule.shiftId}'">Cập nhật</button>



                            <form action="DeleteSchedule" method="post" onsubmit="return confirmDelete(${schedule.shiftId})" style="display:inline;">
                                <input type="hidden" name="scheduleId" value="${schedule.shiftId}">
                                <button type="submit" class="delete-btn">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <script>
            function confirmDelete(shiftId) {
                return confirm("Bạn có chắc chắn muốn xóa ca làm ID " + shiftId + " không?");
            }
        </script>
    </body>
</html>
