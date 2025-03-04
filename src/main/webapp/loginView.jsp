<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            body {
                background-color: #FFC0A0; /* Orange */
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 20px;
                color: #333;
            }

            .login-container {
                display: flex;
                max-width: 1000px;
                background-color: rgba(255, 255, 255, 0.05);
                border-radius: 15px;
                overflow: hidden;
                box-shadow: 0 10px 30px rgba(255, 165, 0, 0.3);
            }

            .login-image {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                background-color: rgba(255, 140, 0, 0.2); /* DarkOrange with transparency */
                padding: 40px;
                flex: 1;
                display: none;
            }

            @media (min-width: 992px) {
                .login-image {
                    display: flex;
                }
            }

            .login-form {
                background-color: white;
                padding: 40px;
                border-radius: 10px;
                width: 100%;
                max-width: 450px;
            }

            .login-heading {
                color: #FF4500; /* OrangeRed */
                margin-bottom: 30px;
                font-weight: 300;
                font-size: 28px;
                text-align: center;
            }

            .form-floating {
                margin-bottom: 20px;
            }

            .form-control:focus {
                border-color: #FF7F50; /* Coral */
                box-shadow: 0 0 0 0.25rem rgba(255, 127, 80, 0.25);
            }

            .btn-login {
                background-color: #FF4500; /* OrangeRed */
                border: none;
                padding: 12px;
                font-weight: 600;
                width: 100%;
                margin-top: 15px;
                border-radius: 6px;
                color: #fff;
            }

            .btn-login:hover {
                background-color: #FF6347; /* Tomato */
            }

            .forgot-password {
                color: #FF8C00; /* DarkOrange */
                text-decoration: none;
                font-size: 14px;
            }.forgot-password:hover {
                text-decoration: underline;
                color: #FF4500; /* OrangeRed */
            }

            .sign-up-link {
                color: #FF4500; /* OrangeRed */
                text-decoration: none;
                font-weight: 600;
            }

            .sign-up-text {
                text-align: center;
                margin-top: 20px;
                font-size: 14px;
                color: #333;
            }

            .error-message {
                color: #e74c3c;
                background-color: #fadbd8;
                padding: 10px;
                margin-bottom: 20px;
                border-radius: 5px;
                text-align: center;
                font-size: 14px;
            }

            .form-floating label {
                color: #FF8C00; /* DarkOrange */
            }

            .form-check-label {
                color: #333;
                font-size: 14px;
            }

            .login-with-google {
                margin-top: 20px;
                text-align: center;
            }

            .login-with-google a {
                color: #FF8C00; /* DarkOrange */
                text-decoration: none;
                font-size: 14px;
            }

            .login-with-google a:hover {
                text-decoration: underline;
            }

            .input-icon {
                color: #FF7F50; /* Coral */
            }

            .help-text {
                color: white;
                font-size: 32px;
                font-weight: 300;
                text-align: center;
                margin-bottom: 30px;
            }

            .home-button {
                text-align: center;
                margin-top: 20px;
            }

            .btn-home {
                background-color: #FF7F50; /* Coral */
                border: none;
                padding: 10px 15px;
                border-radius: 6px;
                color: #fff;
                text-decoration: none;
                font-size: 14px;
                display: inline-block;
            }

            .btn-home:hover {
                background-color: #FF6347; /* Tomato */
                color: #fff;
            }

            .btn-home i {
                margin-right: 5px;
            }

            /* Thêm style cho remember me checkbox */
            .form-check {
                margin-bottom: 15px;
            }

            .form-check-input:checked {
                background-color: #FF7F50;
                border-color: #FF7F50;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <div class="login-image">
                <h2 class="help-text">Serve your good meal</h2>
                <!-- Simplified representation of person lying down -->
                <!-- Simplified representation of person lying down -->
                <div style="width: 120px; height: 60px; background-color: #FF4500; border-radius: 30px; position: relative; margin-bottom: 40px;">
                    <div style="position: absolute; top: -10px; left: 20px; width: 30px; height: 30px; background-color: #FF8C00; border-radius: 50%;"></div>
                    <div style="position: absolute; bottom: -10px; right: -20px; width: 30px; height: 15px; background-color: #FF7F50; border-radius: 10px;"></div>
                </div>

                <div style="font-size: 14px; color: white; opacity: 0.7; text-align: center; max-width: 300px;">
                    Let create your order
                </div>
            </div>

            <div class="login-form">
                <h2 class="login-heading">Đăng nhập</h2>

                <!-- Hiển thị thông báo lỗi nếu có -->
                <% if(request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>

                <form action="LoginController" method="post">
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="username" name="username" placeholder="Username" 
                               value="<%= request.getAttribute("savedUsername") != null ? request.getAttribute("savedUsername") : "" %>">
                        <label for="username"><i class="fas fa-user input-icon me-2"></i> Tên tài khoảng</label>
                    </div>

                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                        <label for="password"><i class="fas fa-lock input-icon me-2"></i> Mật khẩu</label>
                    </div>

                    <!-- Thêm checkbox "Ghi nhớ đăng nhập" -->
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                        <label class="form-check-label" for="rememberMe">Ghi nhớ đăng nhập</label>
                    </div>

                    <button type="submit" class="btn btn-login">ĐĂNG NHẬP</button>
                </form>

                

                <div class="sign-up-text">
                    Bạn chưa có tài khoảng? <a href="RegisterController" class="sign-up-link">Đăng ký</a>
                </div>

                <!-- Nút quay về trang chủ mới -->
                <div class="home-button">
                    <a href="index.jsp" class="btn-home">
                        <i class="fas fa-home"></i> Quay về trang chủ
                    </a>
                </div>
            </div>
        </div>

        <!-- Bootstrap Bundle with Popper --><script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>