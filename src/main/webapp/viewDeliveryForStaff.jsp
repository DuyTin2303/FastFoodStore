<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Danh sách giao hàng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            body {
                background-color: #5CDB95;
                color: #05386B;
                font-family: Arial, sans-serif;
            }
            .container {
                background-color: #EDF5E1;
                padding: 20px;
                border-radius: 10px;
                margin-top: 30px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            }
            .table {
                background-color: #FFFFFF;
                color: #05386B;
            }
            .table th {
                background-color: #8EE4AF;
                color: #05386B;
                font-weight: bold;
            }
            .form-control, .btn {
                background-color: #EDF5E1;
                border: 1px solid #05386B;
                color: #05386B;
            }
            .form-control::placeholder {
                color: #379683;
            }
            .btn:hover {
                background-color: #8EE4AF;
                color: #05386B;
            }
            .error {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center">Danh sách đơn hàng giao</h2>

            <c:if test="${not empty error}">
                <p class="error text-center">${error}</p>
            </c:if>

            <form action="viewDeliveryForStaff" method="GET" class="mb-4">
                <div class="input-group">
                    <input type="text" class="form-control" name="staffName" placeholder="Nhập tên nhân viên giao hàng" required>
                    <button type="submit" class="btn">Tìm kiếm</button>
                </div>
            </form>

            <div class="table-responsive">
                <table class="table table-bordered text-center">
                    <thead>
                        <tr>
                            <th>ID Gán Nhân Viên</th>
                            <th>ID Đơn Hàng</th>
                            <th>Tên Nhân Viên</th>
                            <th>Thời Gian Giao</th>
                            <th>Trạng Thái</th>
                            <th>Địa Chỉ Giao Hàng</th>
                            <th>Tên Người Đặt Hàng</th>
                            <th>Số Điện Thoại Nhận Hàng</th>
                            <th>Ngày Giao Dự Kiến</th>
                            <th>Phương Thức Thanh Toán</th>
                            <th>Tổng Tiền</th>
                            <th>Phí Giao Hàng</th>
                            <th>Tên Món Ăn</th>
                            <th>Số Lượng</th>
                            <th>Cập Nhật</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty deliveries}">
                                <c:forEach var="d" items="${deliveries}">
                                    <tr id="row-${d.assignmentId}">
                                        <td>${d.assignmentId}</td>
                                        <td>${d.orderId}</td>
                                        <td>${d.deliveryStaffName}</td>
                                        <td>${d.assignedAt}</td>
                                        <td class="status-text" data-id="${d.assignmentId}">${d.status}</td>
                                        <td>${d.deliveryAddress}</td>
                                        <td>${d.customerName}</td>
                                        <td>${d.customerPhone}</td>
                                        <td>${d.estimatedDeliveryDate}</td>
                                        <td>${d.paymentMethod}</td>
                                        <td>${d.totalAmount}</td>
                                        <td>${d.shippingFee}</td>
                                        <td>${d.dishName}</td>
                                        <td>${d.quantity}</td>
                                        <td>
                                            <select class="status-select" data-id="${d.assignmentId}">
                                                <option value="Assigned" ${d.status == 'Assigned' ? 'selected' : ''}>Assigned</option>
                                                <option value="Accepted" ${d.status == 'Accepted' ? 'selected' : ''}>Accepted</option>
                                                <option value="In Transit" ${d.status == 'In Transit' ? 'selected' : ''}>In Transit</option>
                                                <option value="Delivered" ${d.status == 'Delivered' ? 'selected' : ''}>Delivered</option>
                                                <option value="Failed" ${d.status == 'Failed' ? 'selected' : ''}>Failed</option>
                                            </select>


                                        </td>

                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="15" class="text-center">Không tìm thấy dữ liệu</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                $(".status-select").change(function () {
                    var assignmentId = $(this).data("id");
                    var newStatus = $(this).val();

                    $.ajax({
                        type: "POST",
                        url: "updateStatusDelivery",
                        data: {assignmentId: assignmentId, status: newStatus},
                        dataType: "json",
                        success: function (response) {
                            if (response.status) {
                                $("#row-" + assignmentId + " .status-text").text(response.status);
                                alert(response.message);
                            } else {
                                alert("Cập nhật thất bại: " + response.message);
                            }
                        },
                        error: function (xhr) {
                            alert("Lỗi cập nhật: " + xhr.responseText);
                        }
                    });
                });
            });
        </script>
    </body>
</html>
