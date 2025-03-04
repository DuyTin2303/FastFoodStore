<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Cập Nhật Lịch Làm Việc</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                padding: 20px;
                background-color: #EDF5E1;
            }
            .container {
                width: 50%;
                margin: auto;
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            }
            label {
                font-weight: bold;
            }
            input, select {
                width: 100%;
                padding: 8px;
                margin: 5px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            button {
                background-color: #379683;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
            }
            button:hover {
                background-color: #05386B;
            }
            .message {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Cập Nhật Lịch Làm Việc</h2>

            <% String error = request.getParameter("error");
           if (error != null) { %>
            <p class="message">Lỗi: <%= error %></p>
            <% } %>

            <form action="updateStaffSchedule" method="POST">

                <!-- shiftId được truyền ẩn để cập nhật đúng bản ghi -->
                <input type="hidden" name="shiftId" value="<%= request.getParameter("shiftId") %>">

                <label for="employeeName">Nhân viên:</label>
                <input type="text" id="employeeName" name="employeeName" value="<%= request.getParameter("employeeName") %>" required>

                <label for="shiftDate">Ngày làm:</label>
                <input type="date" id="shiftDate" name="shiftDate" value="<%= request.getParameter("shiftDate") %>" required>

                <label for="shiftTime">Ca làm:</label>
                <select id="shiftTime" name="shiftTime" required>
                    <option value="8h-13h" <%= "8h-13h".equals(request.getParameter("shiftTime")) ? "selected" : "" %>>8h-13h</option>
                    <option value="13h-18h" <%= "13h-18h".equals(request.getParameter("shiftTime")) ? "selected" : "" %>>13h-18h</option>
                    <option value="18h-23h" <%= "18h-23h".equals(request.getParameter("shiftTime")) ? "selected" : "" %>>18h-23h</option>
                </select>

                <label for="status">Trạng thái:</label>
                <select id="status" name="status" required>
                    <option value="Chưa bắt đầu" <%= "Chưa bắt đầu".equals(request.getParameter("status")) ? "selected" : "" %>>Chưa bắt đầu</option>
                    <option value="Đang làm" <%= "Đang làm".equals(request.getParameter("status")) ? "selected" : "" %>>Đang làm</option>
                    <option value="Đã hoàn thành" <%= "Đã hoàn thành".equals(request.getParameter("status")) ? "selected" : "" %>>Đã hoàn thành</option>
                    <option value="Vắng mặt" <%= "Vắng mặt".equals(request.getParameter("status")) ? "selected" : "" %>>Vắng mặt</option>
                </select>

                <label for="replacementEmployeeName">Nhân viên thay thế:</label>
                <input type="text" id="replacementEmployeeName" name="replacementEmployeeName" value="<%= request.getParameter("replacementEmployeeName") %>">

                <label for="notes">Ghi chú:</label>
                <input type="text" id="notes" name="notes" value="<%= request.getParameter("notes") %>">

                <label for="managerName">Quản lý:</label>
                <input type="text" id="managerName" name="managerName" value="<%= request.getParameter("managerName") %>" required>

                <button type="submit">Cập nhật</button>
                <button type="button" onclick="window.location.href = 'staffSchedule.jsp'">Quay lại</button>
            </form>
        </div>
    </body>
</html>
