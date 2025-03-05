<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Voucher Của Khách Hàng</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Danh Sách Voucher Còn Hiệu Lực</h1>

        <form action="${pageContext.request.contextPath}/Voucher/viewVoucherCustomer" method="GET" class="form-inline mb-4">
            <input type="text" name="search" class="form-control mr-2" placeholder="Tìm kiếm voucher...">
            <button type="submit" class="btn btn-primary">Tìm kiếm</button>
        </form>

        <table class="table table-bordered">
            <thead class="thead-light">
                <tr>
                    <th>Tên Voucher</th>
                    <th>% Giảm Giá</th>
                    <th>Ngày Bắt Đầu</th>
                    <th>Ngày Hết Hạn</th>
                    <th>Trạng Thái</th>
                    <th>Ngày Nhận Voucher</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="voucher" items="${vouchers}">
                    <tr>
                        <td>${voucher.name}</td>
                        <td>${voucher.discountPercentage}%</td>
                        <td>${voucher.validFrom}</td>
                        <td>${voucher.validUntil}</td>
                        <td>${voucher.userVoucherStatus}</td>
                        <td>${voucher.receivedAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <!-- Bootstrap JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
