<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-4">
            <h2 class="text-center">Order Management</h2>

            <!-- Filter Section -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <label for="statusFilter" class="fw-bold">Filter by Status:</label>
                    <select id="statusFilter" class="form-select w-auto d-inline-block" onchange="updateURL(1)">
                        <option value="" ${choosenStatus == "" ? "selected" : ""}>All</option>
                        <c:forEach items="${listStatus}" var="status">
                            <option value="${status}" ${status == choosenStatus ? "selected" : ""}>${status}</option>
                        </c:forEach>

                    </select>
                </div>
            </div>

            <!-- Orders Table -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Order ID</th>
                            <th>Created At</th>
                            <th>Total Amount</th>
                            <th>Payment Method</th>
                            <th>Delivery Address</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Static Sample Data -->
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <td>#${order.orderId}</td>
                                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${order.createdAt}" var="formattedTime"/>
                                <td><fmt:formatDate pattern="HH:mm dd/MM/yyyy" type="both" value="${formattedTime}"/></td>
                                <td>$${order.totalAmount}</td>
                                <td>${order.paymentMethod}</td>
                                <td>${order.deliveryAddress}</td>
                                <td> <span class="badge rounded-pill text-uppercase
                                           <c:choose>
                                               <c:when test="${order.lastStatus.status == 'Cancelled' || order.lastStatus.status == 'Failed' || order.lastStatus.status == 'Returned'}"> text-bg-danger</c:when>
                                               <c:when test="${order.lastStatus.status == 'Pending'}"> text-bg-info</c:when>
                                               <c:when test="${order.lastStatus.status == 'Confirmed'}"> text-bg-warning</c:when>
                                               <c:when test="${order.lastStatus.status == 'Shipped' || order.lastStatus.status == 'Delivered'}"> text-bg-success</c:when>
                                               <c:otherwise> text-bg-secondary</c:otherwise>
                                           </c:choose>">
                                        ${order.lastStatus.status}
                                    </span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Pagination & Page Size -->
            <div class="ms-3">
                <label for="pageSize" class="fw-bold me-2">Page Size:</label>
                <select id="pageSize" class="form-select w-auto d-inline-block" onchange="updateURL(1)">
                    <% 
                        java.util.List<Integer> pageSizes = java.util.Arrays.asList(5,10,20);
                        request.setAttribute("pageSizes", pageSizes); 
                    %>
                    <c:forEach items="${pageSizes}" var="pageSize" >
                        <option value="${pageSize}" ${pageSize == choosenPageSize ? "selected" : ""}>${pageSize}</option>
                    </c:forEach>
                </select>
            </div>
            <nav class="text-center">
                <ul class="pagination mb-0 justify-content-center">
                    <li class="page-item ${currentPage <= 1 ? "disabled" : ""}"><a class="page-link" href="#" onclick="updateURL(${currentPage-1})">Previous</a></li>
                        <c:forEach begin="1" end="${endPage}" var="page">
                        <li class="page-item ${currentPage == page ? "active" : ""}"><a class="page-link" href="#" onclick="updateURL(${page})">${page}</a></li>
                        </c:forEach>
                    <li class="page-item ${currentPage >= endPage ? "disabled" : ""}"><a class="page-link" href="#" onclick="updateURL(${currentPage + 1})">Next</a></li>
                </ul>
            </nav>
        </div>

        <!-- JavaScript for updating URL -->
        <script>
            function updateURL(page) {
                setTimeout(() => {
                    const pageSize = document.getElementById("pageSize").value || 10;
                    const status = document.getElementById("statusFilter").value;
                    window.location.href = '/orderManagement?pageSize=' + pageSize + '&currentPage=' + page + '&status=' + encodeURIComponent(status);
                }, 10);
            }
        </script>

        <!-- Bootstrap JS Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

