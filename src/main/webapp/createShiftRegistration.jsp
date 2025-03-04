<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tạo Đơn Đăng Ký Ca Làm</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4">Tạo Đơn Đăng Ký Ca Làm</h2>
            <form action="createShiftRegistration" method="POST" class="p-4 border rounded bg-light">
                
                <!-- Nhập tên nhân viên thay vì chọn từ danh sách -->
                <div class="mb-3">
                    <label for="employeeName" class="form-label">Tên Nhân Viên</label>
                    <input type="text" id="employeeName" name="employeeName" class="form-control" 
                           placeholder="Nhập tên nhân viên" required>
                </div>

                <div class="mb-3">
                    <label for="shiftDate" class="form-label">Ngày Đăng Ký</label>
                    <input type="date" id="shiftDate" name="shiftDate" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="shiftTime" class="form-label">Khung Giờ</label>
                    <select id="shiftTime" name="shiftTime" class="form-select" required>
                        <option value="" disabled selected>Chọn khung giờ</option>
                        <option value="8h-13h">8h-13h</option>
                        <option value="13h-18h">13h-18h</option>
                        <option value="18h-23h">18h-23h</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="notes" class="form-label">Ghi Chú</label>
                    <textarea id="notes" name="notes" class="form-control" rows="3"></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Tạo Đơn Đăng Ký</button>
                <a href="staffSchedule.jsp" class="btn btn-secondary">Hủy</a>
            </form>

            <c:if test="${not empty message}">
                <div class="alert alert-info mt-4">${message}</div>
            </c:if>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
