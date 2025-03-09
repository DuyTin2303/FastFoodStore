<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Đơn Đăng Ký Ca Làm</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4">Danh Sách Đơn Đăng Ký Ca Làm</h2>

            <div id="message" class="alert d-none"></div>

            <table class="table table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tên Nhân Viên</th>
                        <th>Ngày Bắt Đầu</th>
                        <th>Ngày Kết Thúc</th>
                        <th>Khung Giờ</th>
                        <th>Ngày Trong Tuần</th>
                        <th>Trạng Thái</th>
                        <th>Người Duyệt</th>
                        <th>Ngày Duyệt</th>
                        <th>Ngày Tạo</th>
                        <th>Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reg" items="${registrations}">
                        <tr id="row-${reg.registrationId}">
                            <td>${reg.registrationId}</td>
                            <td>${reg.employeeName}</td>
                            <td>${reg.startDate}</td>
                            <td>${reg.endDate}</td>
                            <td>${reg.shiftTime}</td>
                            <td>${reg.weekdays}</td>
                            <td class="status">${reg.requestStatus}</td>
                            <td>${reg.managerName != null ? reg.managerName : 'Chờ duyệt'}</td>
                            <td>${reg.approvalDate != null ? reg.approvalDate : 'N/A'}</td>
                            <td>${reg.createdDate}</td>
                            <td>
                                <button class="btn btn-success approve-btn" data-id="${reg.registrationId}" 
                                    ${reg.requestStatus == 'Đã duyệt' ? 'disabled' : ''}>
                                    Duyệt
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script>
            $(document).ready(function () {
                $('.approve-btn').on('click', function () {
                    const registrationId = $(this).data('id');
                    const managerId = '${sessionScope.userId}';  // Get managerId from session
                    const $row = $('#row-' + registrationId);

                    if (!managerId) {
                        alert("Bạn cần đăng nhập với quyền quản lý.");
                        return;
                    }

                    $.ajax({
                        url: '${pageContext.request.contextPath}/StaffSchedule/approveShiftRegistration', // Call the servlet
                        method: 'POST',
                        data: {
                            registrationId: registrationId,
                            managerId: managerId
                        },
                        success: function (response) {
                            // Show success message
                            $('#message').text('Duyệt thành công!').removeClass('d-none').removeClass('alert-danger').addClass('alert-success');
                            $row.find('.status').text('Đã duyệt');
                            $row.find('.approve-btn').prop('disabled', true);
                            // Optionally, highlight the row
                            $row.addClass('approved');
                        },
                        error: function () {
                            // Show error message
                            $('#message').text('Lỗi khi duyệt ca làm!').removeClass('d-none').removeClass('alert-success').addClass('alert-danger');
                        }
                    });
                });
            });
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <style>
            /* CSS to highlight the approved rows */
            tr.approved {
                background-color: #d4edda;  /* Green for approved */
            }
        </style>
    </body>
</html>
