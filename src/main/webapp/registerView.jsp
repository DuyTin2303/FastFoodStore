<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Ký - FastFoodStore</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background: #FFA500; /* Orange */
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 20px 0;
            }

            .signup-container {
                background: none;
                max-width: 1000px;
                width: 100%;
                margin: auto;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            }

            .left-panel {
                background: #FF4500; /* OrangeRed */
                color: white;
                padding: 40px;
                position: relative;
            }

            .left-panel::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-image: url('https://via.placeholder.com/500x800/FF4500/FF4500');
                background-size: cover;
                opacity: 0.15;
                z-index: -1;
            }

            .right-panel {
                background-color: #f5f5f5;
                padding: 40px;
            }

            .logo {
                display: flex;
                align-items: center;
                margin-bottom: 30px;
            }

            .tagline {
                margin-top: 60px;
                margin-bottom: 20px;
            }

            .subtitle {
                font-style: italic;
                opacity: 0.8;
            }

            .form-control {
                padding: 12px;
                margin-bottom: 20px;
                border: 1px solid #ddd;
            }

            .btn-primary {
                background-color: #FF8C00; /* DarkOrange - Updated to #FF8C00 */
                border: none;
                padding: 12px;
                font-weight: bold;
            }

            .btn-primary:hover {
                background-color: #FF7F50; /* Coral - Updated to #FF7F50 */
            }

            select.form-select {
                padding: 12px;
                height: auto;
                margin-bottom: 20px;
            }

            .home-link {
                color: #FF6347; /* Tomato - Updated to #FF6347 */
                text-decoration: none;
                font-weight: bold;
                margin-top: 15px;
                display: inline-block;
            }

            .home-link:hover {
                text-decoration: underline;
                color: #FF8C00; /* DarkOrange - Updated to #FF8C00 */
            }

            h1 {
                color: #FF4500; /* OrangeRed - Updated to #FF4500 */
            }

            .form-label {
                color: #FF8C00; /* DarkOrange - Updated to #FF8C00 */
                font-weight: 500;
            }
        </style>
    </head>
    <body>
        <% if(request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>
        <div class="container-fluid p-0">
            <div class="signup-container row g-0">
                <!-- Left Panel with Logo and Tagline -->
                <div class="col-lg-5 left-panel d-flex flex-column justify-content-between">
                    <div>
                        <div class="logo">
                            <img src="img/logo1.png" alt="FastFoodStore Logo" width="50" height="50" class="me-2">
                            <strong style="font-size: 32px;">FastFoodStore</strong>
                        </div>

                        <div class="tagline">
                            <h2>FastFoodStore là cách an toàn, dễ dàng để đặt và thanh toán món ăn.</h2>
                            <p class="subtitle mt-4">Đặt hàng với tốc độ ánh sáng.</p>
                        </div>
                    </div>
                </div>

                <!-- Right Panel with Form -->
                <div class="col-lg-7 right-panel">
                    <h1 class="mb-4">Tạo tài khoản</h1>

                    <!-- Registration Form -->
                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="mb-3">
                            <label class="form-label">Tên đăng nhập</label>
                            <input type="text" name="username" class="form-control" required placeholder="Tên đăng nhập" 
                                   value="${username != null ? username : ''}">
                            <c:if test="${not empty usernameError}">
                                <div class="text-danger">${usernameError}</div>
                            </c:if>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" required placeholder="Email" 
                                   value="${email != null ? email : ''}">
                            <c:if test="${not empty emailError}">
                                <div class="text-danger">${emailError}</div>
                            </c:if>
                        </div>

                        <div class="mb-3"><label class="form-label">Mật khẩu</label>
                            <input type="password" name="password" id="password" class="form-control" required placeholder="Tạo mật khẩu">
                            <c:if test="${not empty passwordLengthError}">
                                <div class="text-danger">${passwordLengthError}</div>
                            </c:if>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Nhập lại mật khẩu</label>
                            <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" required placeholder="Xác nhận mật khẩu">
                            <c:if test="${not empty passwordError}">
                                <div class="text-danger">${passwordError}</div>
                            </c:if>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Họ và tên</label>
                            <input type="text" name="fullName" class="form-control" required placeholder="Họ và tên" 
                                   value="${fullName != null ? fullName : ''}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input type="tel" name="phoneNumber" class="form-control" required placeholder="Số điện thoại" 
                                   value="${phoneNumber != null ? phoneNumber : ''}">
                            <c:if test="${not empty phoneError}">
                                <div class="text-danger">${phoneError}</div>
                            </c:if>
                            <c:if test="${not empty phoneFormatError}">
                                <div class="text-danger">${phoneFormatError}</div>
                            </c:if>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Địa chỉ</label>
                            <input type="text" name="address" class="form-control" required placeholder="Địa chỉ" 
                                   value="${address != null ? address : ''}">
                        </div>

                        <!-- Role selection has been removed -->
                        <!-- Hidden input to pass default role -->
                        <input type="hidden" name="role" value="customer">

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary w-100 mb-3">Tiếp Theo</button>
                        </div>

                        <div class="text-center">
                            <a href="index.jsp" class="home-link">
                                <i class="bi bi-house-door"></i> Trang chủ
                            </a></div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>