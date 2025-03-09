<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi Tiết Đơn Hàng Giao</title>
        <link rel="stylesheet" href="../styles.css"> <!-- Đảm bảo đường dẫn đúng -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- Thêm jQuery -->
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
                        <th>Cập Nhật Trạng Thái</th> <!-- Thêm cột cập nhật trạng thái -->
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

        <!-- Hiển thị thông báo -->
        <div id="message"></div>

        <script>
            $(document).ready(function () {
                // Khi submit form cập nhật trạng thái
                $(".update-status-form").submit(function (event) {
                    event.preventDefault(); // Ngừng hành động mặc định của form

                    var form = $(this);
                    var orderId = form.data("order-id");
                    var newStatus = form.find("select[name='newStatus']").val();

                    // Gửi AJAX request
                    $.ajax({
                        url: "../Delivery/updateStatusOderforStaff",
                        type: "POST",
                        data: {
                            orderId: orderId,
                            newStatus: newStatus
                        },
                        success: function (response) {
                            // Hiển thị thông báo thành công
                            $("#message").html("<p>Cập nhật trạng thái đơn hàng " + orderId + " thành công!</p>");
                        },
                        error: function (xhr, status, error) {
                            // Hiển thị thông báo lỗi
                            $("#message").html("<p>Lỗi khi cập nhật trạng thái đơn hàng " + orderId + ".</p>");
                        }
                    });
                });
            });
        </script>

    </body>
</html>
