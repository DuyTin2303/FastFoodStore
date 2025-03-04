<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Duyệt Đăng Ký Ca</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h2>Duyệt Đăng Ký Ca</h2>

    <c:if test="${not empty param.message}">
        <div class="alert alert-warning">${param.message}</div>
    </c:if>

   <form action="${pageContext.request.contextPath}/approveShiftRegistration" method="post">

    <div class="form-group">
        <label for="registrationId">ID Đăng Ký Ca:</label>
        <input type="number" class="form-control" id="registrationId" name="registrationId" value="${param.registrationId}" readonly>
    </div>

    <div class="form-group">
        <label for="managerName">Tên Quản Lý:</label>
        <input type="text" class="form-control" id="managerName" name="managerName" required>
    </div>

    <div class="form-group">
        <label for="requestStatus">Trạng Thái Đăng Ký:</label>
        <select class="form-control" id="requestStatus" name="requestStatus" required>
            <option value="Chờ duyệt">Chờ duyệt</option>
            <option value="Đã duyệt">Đã duyệt</option>
            <option value="Bị từ chối">Bị từ chối</option>
        </select>
    </div>

    <button type="submit" class="btn btn-primary">Duyệt</button>
</form>


    <hr>

    <a href="viewShiftRegistration.jsp" class="btn btn-secondary">Quay lại</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
