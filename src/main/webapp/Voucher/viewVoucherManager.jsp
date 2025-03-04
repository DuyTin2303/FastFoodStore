<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Voucher</title>
        <link rel="stylesheet" href="../components/styles.css">
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                padding: 8px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #f2f2f2;
            }
            tr:hover {
                background-color: #f5f5f5;
            }
            h1 {
                color: #333;
            }
            .action-buttons a {
                margin: 0 5px;
                padding: 5px 10px;
                color: white;
                text-decoration: none;
                border-radius: 4px;
            }
            .edit-btn {
                background-color: #4CAF50;
            }
            .delete-btn {
                background-color: #f44336;
            }
            .add-btn {
                background-color: #008CBA;
                color: white;
                padding: 10px 15px;
                text-decoration: none;
                border-radius: 4px;
                display: inline-block;
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <h1>Danh sách Voucher</h1>

        <!-- Nút Thêm Voucher -->
        <a href="/Voucher/createVoucherManager" class="add-btn">Thêm Voucher</a>

        <table>
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

            <!-- Hiển thị dữ liệu Voucher -->
            <c:forEach var="voucher" items="${vouchers}">
                <tr>
                    <td>${voucher.voucherId}</td>
                    <td>${voucher.name}</td>
                    <td>${voucher.discountPercentage}</td>
                    <td>${voucher.validFrom}</td>
                    <td>${voucher.validUntil}</td>
                    <td>${voucher.soLuong}</td>
                    <td>${voucher.status}</td>
                    <td class="action-buttons">
                        <!-- Nút Sửa -->
                        <a href="${pageContext.request.contextPath}/Voucher/updateVoucherManager?id=${voucher.voucherId}" class="edit-btn">Sửa</a>

                        <form action="${pageContext.request.contextPath}/Voucher/hidenVoucher" method="post" style="display:inline;">
                            <input type="hidden" name="voucherId" value="${voucher.voucherId}" />
                            <button type="submit" class="delete-btn" 
                                    onclick="return confirm('Bạn có chắc chắn muốn ẩn voucher này không?')">
                                Ẩn
                            </button>
                        </form>



                        <a href="${pageContext.request.contextPath}/User/viewProfileSendVoucher?voucherId=${voucher.voucherId}" 
                           class="add-btn">
                            Gửi
                        </a>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
