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
            font-weight: bold;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
        }
        .error {
            background-color: #FFCCCC;
            color: #CC0000;
        }
        .success {
            background-color: #CCFFCC;
            color: #008000;
        }
        #replacementEmployeeNameContainer {
            display: none;
        }
    </style>
    <script>
        function toggleReplacementEmployeeField() {
            const status = document.getElementById('status').value;
            const replacementField = document.getElementById('replacementEmployeeNameContainer');
            if (status === 'Vắng mặt') {
                replacementField.style.display = 'block';
            } else {
                replacementField.style.display = 'none';
                document.getElementById('replacementEmployeeName').value = '';
            }
        }

        window.onload = function () {
            toggleReplacementEmployeeField();
        };
    </script>
</head>
<body>
    <div class="container">
        <h2>Cập Nhật Lịch Làm Việc</h2>

        <%-- Hiển thị thông báo từ servlet --%>
        <% 
            String message = (String) request.getAttribute("message");
            String messageType = (String) request.getAttribute("messageType");
            if (message != null) { 
        %>
            <p class="message <%= messageType %>"><%= message %></p>
        <% } %>

        <%-- Lấy scheduleId từ request và hiển thị --%>
        <%
            String scheduleId = (String) request.getAttribute("scheduleId");
        %>

        <form action="updateStaffSchedule" method="POST">
            <label for="scheduleId">ID Lịch Làm Việc:</label>
            <input type="text" id="scheduleId" name="scheduleId" value="<%= scheduleId != null ? scheduleId : "" %>" readonly>

            <label for="employeeName">Nhân viên:</label>
            <input type="text" id="employeeName" name="employeeName" value="<%= request.getAttribute("employeeName") %>" required>

            <label for="shiftDate">Ngày làm:</label>
            <input type="date" id="shiftDate" name="shiftDate" value="<%= request.getAttribute("shiftDate") %>" required>

            <label for="shiftTime">Ca làm:</label>
            <select id="shiftTime" name="shiftTime" required>
                <option value="8h-13h" <%= "8h-13h".equals(request.getAttribute("shiftTime")) ? "selected" : "" %>>8h-13h</option>
                <option value="13h-18h" <%= "13h-18h".equals(request.getAttribute("shiftTime")) ? "selected" : "" %>>13h-18h</option>
                <option value="18h-23h" <%= "18h-23h".equals(request.getAttribute("shiftTime")) ? "selected" : "" %>>18h-23h</option>
            </select>

            <label for="status">Trạng thái:</label>
            <select id="status" name="status" required onchange="toggleReplacementEmployeeField()">
                <option value="Chưa bắt đầu" <%= "Chưa bắt đầu".equals(request.getAttribute("status")) ? "selected" : "" %>>Chưa bắt đầu</option>
                <option value="Đang làm" <%= "Đang làm".equals(request.getAttribute("status")) ? "selected" : "" %>>Đang làm</option>
                <option value="Đã hoàn thành" <%= "Đã hoàn thành".equals(request.getAttribute("status")) ? "selected" : "" %>>Đã hoàn thành</option>
                <option value="Vắng mặt" <%= "Vắng mặt".equals(request.getAttribute("status")) ? "selected" : "" %>>Vắng mặt</option>
            </select>

            <div id="replacementEmployeeNameContainer">
                <label for="replacementEmployeeName">Nhân viên thay thế:</label>
                <input type="text" id="replacementEmployeeName" name="replacementEmployeeName" value="<%= request.getAttribute("replacementEmployeeName") != null ? request.getAttribute("replacementEmployeeName") : "" %>">
            </div>

            <label for="notes">Ghi chú:</label>
            <input type="text" id="notes" name="notes" value="<%= request.getAttribute("notes") != null ? request.getAttribute("notes") : "" %>">

            <label for="managerName">Quản lý:</label>
            <input type="text" id="managerName" name="managerName" value="<%= request.getAttribute("managerName") != null ? request.getAttribute("managerName") : "" %>" required>

            <button type="submit">Cập nhật</button>
            <button type="button" onclick="window.location.href = 'staffSchedule'">Quay lại</button>
        </form>
    </div>
</body>
</html>
