<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Đơn Đăng Ký Ca</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4">Danh Sách Đơn Đăng Ký Ca Làm</h2>

            <c:if test="${not empty param.message}">
                <div class="alert alert-success">
                    ${param.message}
                </div>
            </c:if>

            <table class="table table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tên Nhân Viên</th>
                        <th>Ngày Đăng Ký</th>
                        <th>Khung Giờ</th>
                        <th>Trạng Thái</th>
                        <th>Người Duyệt</th>
                        <th>Ngày Duyệt</th>
                        <th>Ngày Tạo</th>
                        <th>Hành động</th> <!-- Thêm cột Hành động -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reg" items="${registrations}">
                        <tr>
                            <td>${reg.registrationId}</td>
                            <td>${reg.employeeName}</td>
                            <td>${reg.shiftDate}</td>
                            <td>${reg.shiftTime}</td>
                            <td>${reg.requestStatus}</td>
                            <td>${reg.managerName != null ? reg.managerName : 'Chưa duyệt'}</td>
                            <td>${reg.approvalDate != null ? reg.approvalDate : 'N/A'}</td>
                            <td>${reg.createdDate}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/approveShiftRegistration?registrationId=${reg.registrationId}" class="btn btn-success" ${reg.requestStatus == 'Đã duyệt' ? 'disabled' : ''}>
                                    Duyệt
                                </a>

                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
