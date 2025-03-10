<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Voucher</title>
        <link rel="stylesheet" href="../components/styles.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
        <style>
            .table-hover tbody tr:hover {
                background-color: #f1f1f1;
            }
            h1 {
                color: #333;
            }
            .btn-custom {
                margin: 0 5px;
            }
            .btn-send {
                background-color: #007bff;
                color: white;
            }
        </style>
    </head>
    <body class="container py-4">

        <!-- Nút Back -->
        <div class="mb-3">
            <a href="/adminView.jsp" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Quay lại
            </a>
        </div>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Danh sách Voucher</h1>
            <a href="/Voucher/createVoucherManager" class="btn btn-primary">
                <i class="fas fa-plus"></i> Thêm Voucher
            </a>
        </div>

        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên Voucher</th>
                    <th>Giảm giá (%)</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Số lượng</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="voucher" items="${vouchers}">
                    <tr>
                        <td>${voucher.voucherId}</td>
                        <td>${voucher.name}</td>
                        <td>${voucher.discountPercentage}%</td>
                        <td>${voucher.validFrom}</td>
                        <td>${voucher.validUntil}</td>
                        <td>${voucher.soLuong}</td>
                        <td>
                            <span class="badge 
                                ${voucher.status == 'Active' ? 'bg-success' : 'bg-secondary'}">
                                ${voucher.status}
                            </span>
                        </td>
                        <td>
                            <!-- Nút Sửa -->
                            <a href="${pageContext.request.contextPath}/Voucher/updateVoucherManager?id=${voucher.voucherId}" 
                               class="btn btn-warning btn-sm btn-custom">
                                <i class="fas fa-edit"></i> Sửa
                            </a>
                            
                            <!-- Nút Ẩn -->
                            <form action="${pageContext.request.contextPath}/Voucher/hidenVoucher" method="post" 
                                  style="display:inline;">
                                <input type="hidden" name="voucherId" value="${voucher.voucherId}" />
                                <button type="submit" class="btn btn-danger btn-sm btn-custom" 
                                        onclick="return confirm('Bạn có chắc chắn muốn ẩn voucher này không?')">
                                    <i class="fas fa-trash-alt"></i> Ẩn
                                </button>
                            </form>

                            <!-- Nút Gửi -->
                            <a href="${pageContext.request.contextPath}/User/viewCustomer?voucherId=${voucher.voucherId}" 
                               class="btn btn-send btn-sm btn-custom">
                                <i class="fas fa-paper-plane"></i> Gửi
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
