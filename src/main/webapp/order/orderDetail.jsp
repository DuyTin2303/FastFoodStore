<%-- 
    Document   : orderDetail
    Created on : Mar 5, 2025, 4:06:36 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Details</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .order-summary {
                background-color: #f8f9fa;
                padding: 15px;
                border-radius: 10px;
            }
            .dish-image {
                width: 80px;
                height: 80px;
                object-fit: cover;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <div class="card">
                <h2 class="card-header text-center">Order Details</h2>
                <div class="card-body p-3 shadow-sm">
                    <p><strong>Order ID:</strong> ${order.orderId}</p>
                    <p><strong>Payment Method:</strong> ${order.paymentMethod}</p>
                    <p><strong>Delivery Address:</strong> ${order.deliveryAddress}</p>
                    <p><strong>Estimated Delivery Date:</strong> ${order.estimatedDeliveryDate}</p>
                    <p><strong>Total Amount:</strong> <span class="text-success">$${order.totalAmount}</span></p>
                    <hr class="border border-primary"/>
                    <p class="ps-4 text-body-secondary"><strong>Original Price:</strong> <span class="text-primary">$${order.originalPrice}</span></p>
                    <p class="ps-4 text-body-secondary"><strong>Voucher Discount:</strong> <span class="text-danger">$${order.discountPrice}</span></p>
                    <p class="ps-4 text-body-secondary"><strong>Shipping Fee:</strong> <span class="text-primary">$${order.shippingFee}</span></p>
                </div>
            </div>
            <h3 class="mt-4">Ordered Dishes</h3>
            <div class="table-responsive">
                <table class="table table-bordered table-hover mt-3">
                    <thead class="table-dark">
                        <tr>
                            <th>Dish Name</th>
                            <th>Category</th>
                            <th>Image</th>
                            <th>Quantity</th>
                            <th>Original Price</th>
                            <th>Discount</th>
                            <th>Final Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${order.orderDetails}" var="orderDetail" >
                            <tr>
                                <td>${orderDetail.dish.dishName}</td>
                                <td>${orderDetail.dish.category.categoryName}</td>
                                <td>
                                    <img src="${orderDetail.dish.imageUrl}" class="dish-image img-thumbnail" alt="${orderDetail.dish.dishName}">
                                </td>
                                <td>${orderDetail.quantity}</td>
                                <td class="text-danger">$${orderDetail.originalPrice}</td>
                                <td class="text-primary">$${orderDetail.discount}</td>
                                <td class="text-success fw-bold">$${orderDetail.sellingPrice}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <a href="/order" class="btn btn-secondary mt-3">Back to Orders</a>
        </div>

        <!-- Bootstrap JS Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>