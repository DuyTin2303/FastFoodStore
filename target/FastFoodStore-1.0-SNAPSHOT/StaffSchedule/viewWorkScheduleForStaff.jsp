<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Work Schedule for Staff</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Work Schedule</h1>

    <!-- Check if the schedules attribute exists -->
    <c:if test="${not empty schedules}">
        <table>
            <thead>
                <tr>
                    <!-- Removed Shift ID column from the header -->
                    <th>Employee Name</th>
                    <th>Shift Date</th>
                    <th>Shift Time</th>
                    <th>Status</th>
                    <th>Notes</th>
                    <th>Manager Name</th>
                    <th>Replacement Employee</th>
                </tr>
            </thead>
            <tbody>
                <!-- Loop through schedules -->
                <c:forEach var="schedule" items="${schedules}">
                    <tr>
                        <!-- Removed the Shift ID column from the body -->
                        <td>${schedule.employeeName}</td>
                        <td>${schedule.shiftDate}</td>
                        <td>${schedule.shiftTime}</td>
                        <td>${schedule.status}</td>
                        <td>${schedule.notes}</td>
                        <td>${schedule.managerName}</td>
                        <td>${schedule.replacementEmployeeName}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty schedules}">
        <p>No schedules found for this staff member.</p>
    </c:if>
</body>
</html>
