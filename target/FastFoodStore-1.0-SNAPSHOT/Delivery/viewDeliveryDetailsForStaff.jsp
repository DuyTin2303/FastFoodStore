<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Đơn Hàng Giao</title>
    <link rel="stylesheet" href="../styles.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

    <h1>Danh Sách Đơn Hàng Giao</h1>

    <c:if test="${not empty deliveries}">
        <table border="1" cellpadding="10">
            <thead>
                <tr>
                    <th>Mã Đơn Hàng</th>
                    <th>Ngày Tạo</th>
                    <th>Khách Hàng</th>
                    <th>Địa Chỉ Giao</th>
                    <th>Ngày Dự Kiến Giao</th>
                    <th>Phương Thức Thanh Toán</th>
                    <th>Trạng Thái Giao</th>
                    <th>Mã Món Ăn</th>
                    <th>Tên Món Ăn</th>
                    <th>Số Lượng</th>
                    <th>Giá Bán</th>
                    <th>Giảm Giá</th>
                    <th>Giá Gốc</th>
                    <th>Phí Vận Chuyển</th>
                    <th>Tổng Tiền</th> <!-- Tổng tiền đứng trước cập nhật trạng thái -->
                    <th>Cập Nhật Trạng Thái</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="delivery" items="${deliveries}">
                    <tr>
                        <td>${delivery.orderId}</td>
                        <td>${delivery.orderCreatedAt}</td>
                        <td>${delivery.customerName}</td>
                        <td>${delivery.deliveryAddress}</td>
                        <td>${delivery.estimatedDeliveryDate}</td>
                        <td>${delivery.paymentMethod}</td>
                        <td>${delivery.status}</td>
                        <td>${delivery.dishId}</td>
                        <td>${delivery.dishName}</td>
                        <td>${delivery.quantity}</td>
                        <td>${delivery.sellingPrice}</td>
                        <td>${delivery.discount}</td>
                        <td>${delivery.originalPrice}</td>
                        <td>${delivery.shippingFee}</td>
                        <td>${delivery.totalAmount}</td> <!-- Tổng tiền ở trước cập nhật trạng thái -->

                        <!-- Form cập nhật trạng thái đơn hàng -->
                        <td>
                            <form class="update-status-form" data-order-id="${delivery.orderId}">
                                <select name="newStatus" required>
                                    <option value="" disabled selected>Chọn trạng thái</option>
                                    <option value="Pending">Pending</option>
                                    <option value="Processing">Processing</option>
                                    <option value="Accepted">Accepted</option>
                                    <option value="In Transit">In Transit</option>
                                    <option value="Delivered">Delivered</option>
                                    <option value="Failed">Failed</option>
                                </select>
                                <button type="submit">Cập Nhật</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div id="message"></div>

    <script>
        $(document).ready(function () {
            $(".update-status-form").submit(function (event) {
                event.preventDefault();
                var form = $(this);
                var orderId = form.data("order-id");
                var newStatus = form.find("select[name='newStatus']").val();

                $.ajax({
                    url: "../Delivery/updateStatusOderforStaff",
                    type: "POST",
                    data: {
                        orderId: orderId,
                        newStatus: newStatus
                    },
                    success: function (response) {
                        $("#message").html("<p>Cập nhật trạng thái đơn hàng " + orderId + " thành công!</p>");
                    },
                    error: function (xhr, status, error) {
                        $("#message").html("<p>Lỗi khi cập nhật trạng thái đơn hàng " + orderId + ".</p>");
                    }
                });
            });
        });
    </script>

</body>
</html>
