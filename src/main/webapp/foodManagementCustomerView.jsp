

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.FoodItem"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản Lý Món Ăn</title>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            .food-img {
                width: 100px;
                height: 100px;
                object-fit: cover;
                border-radius: 5px;
            }
            .food-card {
                border: 1px solid #ddd;
                border-radius: 8px;
                overflow: hidden;
                margin-bottom: 20px;
                transition: transform 0.3s;
            }
            .food-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }
            .food-card-img {
                height: 200px;
                object-fit: cover;
            }
            .grid-view {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
                gap: 20px;
            }
            .btn-add-cart {
                background-color: #FF4500; /* OrangeRed from your color palette */
                color: white;
                transition: all 0.3s;
            }
            .btn-add-cart:hover {
                background-color: #FF8C00; /* DarkOrange from your color palette */
                transform: scale(1.05);
                color: white;
            }
            .card-footer {
                background-color: white;
                border-top: none;
                padding: 0.75rem 1.25rem 1.25rem;
            }
            .review-link {
                cursor: pointer;
                color: #FF7F50; /* Coral from your color palette */
                text-decoration: underline;
            }
            .review-link:hover {
                color: #FF6347; /* Tomato from your color palette */
            }
            .review-content {
                white-space: pre-line;
                max-height: 400px;
                overflow-y: auto;
            }
            .btn-home {
                background-color: #FFA500; /* Orange from your color palette */
                color: white;
                transition: all 0.3s;
            }
            .btn-home:hover {
                background-color: #FF8C00; /* DarkOrange from your color palette */
                transform: scale(1.05);
                color: white;
            }
            .btn-primary {
                background-color: #FF4500; /* OrangeRed */
                border-color: #FF4500;
            }
            .btn-primary:hover {
                background-color: #FF6347; /* Tomato */
                border-color: #FF6347;
            }
            .btn-success {
                background-color: #FF7F50; /* Coral */
                border-color: #FF7F50;
            }
            .btn-success:hover {
                background-color: #FF6347; /* Tomato */
                border-color: #FF6347;
            }
            .btn-warning {
                background-color: #FFA500; /* Orange */
                border-color: #FFA500;
                color: white;
            }
            .btn-warning:hover {
                background-color: #FF8C00; /* DarkOrange */
                border-color: #FF8C00;
                color: white;
            }
            .btn-outline-secondary {
                color: #FF7F50; /* Coral */
                border-color: #FF7F50;
            }
            .btn-outline-secondary:hover {
                background-color: #FF7F50; /* Coral */
                border-color: #FF7F50;
                color: white;
            }
            .table-dark {
                background-color: #FF8C00; /* DarkOrange */
            }
            .badge.bg-secondary {
                background-color: #FF7F50 !important; /* Coral */
            }
            .text-warning {
                color: #FFA500 !important; /* Orange */
            }
            .img-detail {
                max-width: 100%;
                max-height: 300px;
                object-fit: contain;
            }
            .modal-lg {
                max-width: 800px;
            }
            .review-modal-img {
                width: 100%;
                height: 250px;
                object-fit: cover;
                border-radius: 8px;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">
                    <i class="fas fa-home me-2"></i>Trang Chủ
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <h5 class="nav-link mb-0">Quản Lý Món Ăn</h5>
                        </li>
                    </ul>

                    <div class="d-flex">
                        <div class="dropdown me-2">
                            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="filterDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                Bộ lọc
                            </button>
                            <div class="dropdown-menu p-3" style="width: 300px;">
                                <!-- Bộ lọc danh mục -->
                                <form action="FoodManagementController" method="get" class="mb-3">
                                    <div class="mb-2">
                                        <select name="category" class="form-select form-select-sm">
                                            <option value="">Chọn danh mục</option>
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category}" ${param.category == category ? 'selected' : ''}>${category}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <button type="submit" name="action" value="filterCategory" class="btn btn-success btn-sm w-100">Lọc danh mục</button>
                                </form>

                                <!-- Bộ lọc theo giá -->
                                <form action="FoodManagementController" method="get">
                                    <div class="mb-2">
                                        <input type="number" name="maxPrice" class="form-control form-control-sm" placeholder="Nhập giá tối đa" required value="${maxPrice}">
                                    </div>
                                    <button type="submit" name="action" value="filterPrice" class="btn btn-warning btn-sm w-100">Lọc theo giá</button>
                                </form>
                            </div>
                        </div>

                        <!-- Thanh tìm kiếm -->
                        <form action="FoodManagementController" method="get" class="d-flex">
                            <input type="text" name="name" class="form-control me-2" placeholder="Tìm kiếm món ăn..." required>
                            <button type="submit" name="action" value="search" class="btn btn-primary">Tìm kiếm</button>
                        </form>

                        <!-- Chuyển đổi chế độ xem -->
                        <div class="btn-group ms-2">
                            <button id="tableViewBtn" class="btn btn-outline-secondary">
                                <i class="fas fa-list"></i>
                            </button>
                            <button id="gridViewBtn" class="btn btn-outline-secondary">
                                <i class="fas fa-th"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </nav>

        <!-- Hiển thị thông báo lỗi (nếu có) -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Hiển thị thông báo (nếu có) -->
        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>

        <!-- Hiển thị các thông tin tìm kiếm/lọc (nếu có) -->
        <c:if test="${not empty searchTerm}">
            <div class="alert alert-info">
                Kết quả tìm kiếm cho: <strong>${searchTerm}</strong>
                <a href="FoodManagementController" class="float-end">Xóa bộ lọc</a>
            </div>
        </c:if>

        <c:if test="${not empty param.category}">
            <div class="alert alert-info">
                Lọc theo danh mục: <strong>${param.category}</strong>
                <a href="FoodManagementController" class="float-end">Xóa bộ lọc</a>
            </div>
        </c:if>

        <c:if test="${not empty maxPrice}">
            <div class="alert alert-info">
                Lọc theo giá tối đa: <strong>${maxPrice} VND</strong>
                <a href="FoodManagementController" class="float-end">Xóa bộ lọc</a>
            </div>
        </c:if>

        <!-- Hiển thị dạng bảng -->
        <div id="tableView">
            <c:choose>
                <c:when test="${empty foodList}">
                    <div class="alert alert-warning">Không có món ăn nào được tìm thấy.</div>
                </c:when>
                <c:otherwise>
                    <table class="table table-bordered table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Hình ảnh</th>
                                <th>Tên món</th>
                                <th>Danh mục</th>
                                <th>Giá</th>
                                <th>Đánh giá</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="food" items="${foodList}">
                                <tr>
                                    <td>${food.id}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty food.imageUrl}">
                                                <img src="${food.imageUrl}" alt="${food.name}" class="food-img">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="images/default-food.jpg" alt="Default" class="food-img">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${food.name}</td>
                                    <td>${food.category}</td>
                                    <td>${food.price} VND</td>
                                    <td>
                                        <span class="review-link" data-bs-toggle="modal" data-bs-target="#reviewModal${food.id}">
                                            <i class="fas fa-star text-warning me-1"></i>
                                            ${food.review.length() > 50 ? food.review.substring(0, 50).concat("...") : food.review}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="loginView.jsp" class="btn btn-add-cart btn-sm">
                                            <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ hàng
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Hiển thị dạng lưới -->
        <div id="gridView" class="grid-view" style="display: none;">
            <c:choose>
                <c:when test="${empty foodList}">
                    <div class="alert alert-warning">Không có món ăn nào được tìm thấy.</div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="food" items="${foodList}">
                        <div class="food-card card">
                            <c:choose>
                                <c:when test="${not empty food.imageUrl}">
                                    <img src="${food.imageUrl}" alt="${food.name}" class="card-img-top food-card-img">
                                </c:when>
                                <c:otherwise>
                                    <img src="images/default-food.jpg" alt="Default" class="card-img-top food-card-img">
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <h5 class="card-title">${food.name}</h5>
                                <p class="card-text">
                                    <span class="badge bg-secondary">${food.category}</span>
                                    <strong class="d-block mt-2">${food.price} VND</strong>
                                    <span class="d-block mt-2 review-link" data-bs-toggle="modal" data-bs-target="#reviewModal${food.id}">
                                        <i class="fas fa-star text-warning me-1"></i>
                                        ${food.review.length() > 30 ? food.review.substring(0, 30).concat("...") : food.review}
                                    </span>
                                </p>
                            </div>
                            <div class="card-footer text-center">
                                <a href="loginView.jsp" class="btn btn-add-cart w-100">
                                    <i class="fas fa-shopping-cart me-2"></i> Thêm vào giỏ hàng
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Modals cho đánh giá chi tiết -->
        <c:forEach var="food" items="${foodList}">
            <div class="modal fade" id="reviewModal${food.id}" tabindex="-1" aria-labelledby="reviewModalLabel${food.id}" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="reviewModalLabel${food.id}">Chi tiết món ăn: ${food.name}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-5">
                                    <c:choose>
                                        <c:when test="${not empty food.imageUrl}">
                                            <img src="${food.imageUrl}" alt="${food.name}" class="review-modal-img">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="images/default-food.jpg" alt="Default" class="review-modal-img">
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="food-info mb-3">
                                        <h5>${food.name}</h5>
                                        <span class="badge bg-secondary mb-2">${food.category}</span>
                                        <div class="price fs-5 fw-bold text-danger">${food.price} VND</div>
                                    </div>
                                    <a href="loginView.jsp" class="btn btn-add-cart w-100 mb-3">
                                        <i class="fas fa-shopping-cart me-2"></i> Thêm vào giỏ hàng
                                    </a>
                                </div>
                                <div class="col-md-7">
                                    <div class="card">
                                        <div class="card-header bg-light">
                                            <h6 class="mb-0"><i class="fas fa-star text-warning me-2"></i>Đánh giá chi tiết</h6>
                                        </div>
                                        <div class="card-body">
                                            <div class="review-content">
                                                ${not empty food.review ? food.review : 'Chưa có đánh giá cho món ăn này.'}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Script for view switching -->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Set default view based on localStorage or default to table
            const savedView = localStorage.getItem('preferredView') || 'table';
            if (savedView === 'grid') {
                document.getElementById('tableView').style.display = 'none';
                document.getElementById('gridView').style.display = 'grid';
            } else {
                document.getElementById('tableView').style.display = 'block';
                document.getElementById('gridView').style.display = 'none';
            }

            // View switching event handlers
            document.getElementById('tableViewBtn').addEventListener('click', function () {
                document.getElementById('tableView').style.display = 'block';
                document.getElementById('gridView').style.display = 'none';
                localStorage.setItem('preferredView', 'table');
            });

            document.getElementById('gridViewBtn').addEventListener('click', function () {
                document.getElementById('tableView').style.display = 'none';
                document.getElementById('gridView').style.display = 'grid';
                localStorage.setItem('preferredView', 'grid');
            });

            // Auto-open modal if there is a selectedFood (from a previous action)
        <c:if test="${not empty selectedFood}">
            var reviewModal = new bootstrap.Modal(document.getElementById('reviewModal${selectedFood.id}'));
            reviewModal.show();
        </c:if>
        });
    </script>
</body>
</html>

