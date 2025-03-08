<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order History</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .accordion-button {
                display: flex;
                justify-content: space-between;
                align-items: center;
                &::after {
                    margin-left: 10px;
                }
                &.collapsed {
                    background-color: #cfe2ff;
                }
                &:not(.collapsed) {
                    background-color: #cfe2cc;
                }
            }
            .order-header {
                display: flex;
                justify-content: space-between;
                width: 100%;
            }
            .step-circle {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 14px;
                font-weight: bold;
                position: relative;
            }
            .step-content {
                max-width: 6rem;
                overflow: hidden;
            }
            .step:not(:last-child) .step-circle::after {
                content: "";
                position: absolute;
                width: 14rem;
                height: 8px;
                background: rgb(13,110,253);
                top: 22px;
                left: 90%;
            }
        </style>
    </head>
    <body class="container mt-4">

        <h2 class="mb-4">Order History</h2>

        <div class="accordion d-flex flex-column gap-1" id="orderHistory">
            <c:forEach items="${orders}" var="order" varStatus="i">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="order${i.index}">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${i.index}">
                            <div class="order-header">
                                <span><strong>Qty:</strong> ${order.totalQuantity}</span>
                                <span><strong>Total:</strong> $${order.totalAmount}</span>
                                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${order.createdAt}" var="formattedTime"/>
                                <span><strong>Created:</strong> <fmt:formatDate pattern="HH:mm dd/MM/yyyy" type="both" value="${formattedTime}"/></span>
                                <span><strong>Status:</strong> 
                                    <span class="badge rounded-pill text-uppercase
                                          <c:choose>
                                              <c:when test="${order.lastStatus.status == 'Cancelled' || order.lastStatus.status == 'Failed' || order.lastStatus.status == 'Returned'}"> text-bg-danger</c:when>
                                              <c:when test="${order.lastStatus.status == 'Pending'}"> text-bg-info</c:when>
                                              <c:when test="${order.lastStatus.status == 'Confirmed'}"> text-bg-warning</c:when>
                                              <c:when test="${order.lastStatus.status == 'Shipped' || order.lastStatus.status == 'Delivered'}"> text-bg-success</c:when>
                                              <c:otherwise> text-bg-secondary</c:otherwise>
                                          </c:choose>">
                                        ${order.lastStatus.status}
                                    </span>
                                </span>
                            </div>
                        </button>   
                    </h2>
                    <div id="collapse${i.index}" class="accordion-collapse collapse" data-bs-parent="#orderHistory">
                        <div class="accordion-body d-flex flex-wrap">
                            <div class="d-flex flex-column w-75">
                                <p><strong>Payment Method:</strong> ${order.paymentMethod}</p>
                                <p><strong>Delivery Address:</strong> ${order.deliveryAddress}</p>
                                <p><strong>Estimated Delivery:</strong> ${order.estimatedDeliveryDate}</p>
                            </div>
                            <div class="d-flex flex-column gap-2 w-25">
                                <button onclick="window.location.href = '/order/detail?orderId=${order.orderId}'" class="btn btn-primary">View detail</button>
                                <c:if test="${order.paymentMethod=='Online' && order.lastStatus.status=='Pending'}">
                                    <button onclick="window.location.href = '/order/repay?orderId=${order.orderId}'" class="btn btn-success">Pay order</button>
                                </c:if>
                                <c:if test="${order.lastStatus.status=='Pending'}">
                                    <button onclick="window.location.href = '/order/cancel?orderId=${order.orderId}'" class="btn btn-danger">Cancel order</button>
                                </c:if>
                            </div>
                            <div id="order-progress" class="w-100">
                                <p><strong>Order Progress:</strong></p>
                                <div class="status-progress d-flex flex-row justify-content-start flex-wrap" style="column-gap: 10rem; row-gap: 2rem;">
                                    <c:forEach items="${order.orderStatuses}" var="status" varStatus="j">
                                        <div class="step d-flex flex-column align-items-center">
                                            <div class="step-circle bg-primary text-white">${j.index + 1}</div>
                                            <div class="step-content text-center mt-2">
                                                <strong>${status.status}</strong>
                                                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${status.updatedAt}" var="formattedTime"/>
                                                <div class="text-muted"><fmt:formatDate pattern="HH:mm dd/MM/yyyy" type="both" value="${formattedTime}"/></div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>