<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Đăng Ký Ca Làm Việc</title>
        <link rel="stylesheet" href="styles.css">
        <style>
            .hidden-column {
                display: none;
            }
        </style>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            function deleteShiftRegistration(registrationId) {
                if (confirm("Bạn có chắc chắn muốn xóa đăng ký ca làm này không?")) {
                    $.ajax({
                        url: "/ShiftRegistration/deleteWorkShiftRegistrationsForStaff",
                        type: "POST",
                        data: {registrationId: registrationId},
                        success: function (response) {
                            alert(response); // Hiển thị thông báo từ server
                            location.reload(); // Tải lại trang để cập nhật danh sách
                        },
                        error: function (xhr) {
                            alert("❌ Lỗi: " + xhr.responseText); // Hiển thị lỗi nếu có
                        }
                    });
                }
            }
        </script>

    </head>
    <body>
        <header>
            <h1>Danh Sách Đăng Ký Ca Làm Việc</h1>
        </header>

        <section>
            <c:if test="${not empty shiftRegistrations}">
                <table border="1" cellspacing="0" cellpadding="5">
                    <thead>
                        <tr>
                            <th class="hidden-column">STT</th>
                            <th>Tên Nhân Viên</th>
                            <th>Ngày Bắt Đầu</th>
                            <th>Ngày Kết Thúc</th>
                            <th>Khung Giờ</th>
                            <th>Các Ngày Trong Tuần</th>
                            <th>Trạng Thái</th>
                            <th>Ghi Chú</th>
                            <th>Quản Lý</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="shift" items="${shiftRegistrations}">
                            <tr>
                                <td class="hidden-column">${shift.registrationId}</td>
                                <td>${shift.employeeName}</td>
                                <td>${shift.startDate}</td>
                                <td>${shift.endDate}</td>
                                <td>${shift.shiftTime}</td>
                                <td>${shift.weekdays}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${shift.requestStatus == 'Chờ duyệt'}">Chờ duyệt</c:when>
                                        <c:when test="${shift.requestStatus == 'Đã duyệt'}">Đã duyệt</c:when>
                                        <c:when test="${shift.requestStatus == 'Bị từ chối'}">Bị từ chối</c:when>
                                        <c:otherwise>Không Xác Định</c:otherwise>
                                    </c:choose>


                                </td>
                                <td>${shift.notes}</td>
                                <td>${shift.managerName}</td>
                                <td>
                                    <a href="/ShiftRegistration/updateWorkShiftRegistrationsForStaff?id=${shift.registrationId}" class="update-btn">Cập Nhật</a>
                                    <!-- Gọi hàm deleteShiftRegistration khi nhấn nút Xoá -->
                                    <a href="javascript:void(0);" class="delete-btn" onclick="deleteShiftRegistration(${shift.registrationId})">Xoá</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty shiftRegistrations}">
                <p>Không có đăng ký ca làm việc nào.</p>
            </c:if>
        </section>

        <footer>
            <a href="staffView.jsp" class="back-btn">Quay Lại</a>
        </footer>
    </body>
</html>
