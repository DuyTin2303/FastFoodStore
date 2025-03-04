<%-- 
    Document   : logoutView
    Created on : 28-02-2025, 20:13:24
    Author     : a
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="3;url=index.jsp"> <!-- Redirect to index.jsp after 3 seconds -->
        <title>Đăng xuất - FastFoodStore</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            :root {
                --orange: #FFA500;
                --dark-orange: #FF8C00;
                --coral: #FF7F50;
                --tomato: #FF6347;
                --orange-red: #FF4500;
            }

            .logout-container {
                max-width: 500px;
                margin: 100px auto;
                padding: 30px;
                text-align: center;
                border-radius: 10px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
                background-color: #fff;
                border-top: 5px solid var(--orange);
            }
            .logo {
                margin-bottom: 20px;
            }
            h2 {
                color: var(--orange-red);
            }
            .message {
                font-size: 18px;
                margin: 20px 0;
                color: var(--dark-orange);
            }
            .redirect-text {
                color: var(--coral);
                font-size: 14px;
            }
            body {
                background-color: #f9f9f9;
                font-family: Arial, sans-serif;
            }
            .btn-primary {
                background-color: var(--orange);
                border-color: var(--orange);
            }
            .btn-primary:hover {
                background-color: var(--dark-orange);
                border-color: var(--dark-orange);
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="logout-container">
                <div class="logo">
                    <img src="img/logo1.png" alt="FastFoodStore Logo" width="80" height="80">
                </div>
                <h2>Đăng xuất thành công</h2>
                <div class="message">
                    <% 
                        String message = (String) request.getAttribute("message");
                        if(message != null) {
                            out.println(message);
                        } else {
                            out.println("Bạn đã đăng xuất thành công!");
                        }
                    %>
                </div>
                <div class="redirect-text">
                    Bạn sẽ được chuyển về trang chủ sau 3 giây...
                </div>
                <div class="mt-4">
                    <a href="index.jsp" class="btn btn-primary">Quay về trang chủ ngay</a>
                </div>
            </div>
        </div>
    </body>
</html>
