<%@ page import="java.util.List" %>
<%@ page import="model.Delivery" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Track Delivery Status for Manager</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        h1 {
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>Delivery Status Overview</h1>
    
    <table>
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Amount</th>
                <th>Payment Method</th>
                <th>Delivery Address</th>
                <th>Status</th>
                <th>Status Updated At</th>
                <th>Order Created At</th>
            </tr>
        </thead>
        <tbody>
            <!-- Loop through the list of orders and display them -->
            <c:forEach var="delivery" items="${ordersWithStatus}">
                <tr>
                    <td>${delivery.orderId}</td>
                    <td>${delivery.totalAmount}</td>
                    <td>${delivery.paymentMethod}</td>
                    <td>${delivery.deliveryAddress}</td>
                    <td>${delivery.status}</td>
                    <td>${delivery.statusUpdatedAt}</td>
                    <td>${delivery.orderCreatedAt}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</body>
</html>
