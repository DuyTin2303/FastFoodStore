<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style>
        .user-menu {
            position: relative;
            display: inline-block;
        }
        .user-menu-content {
            display: none;
            position: absolute;
            background-color: white;
            box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
            min-width: 200px;
            z-index: 1;
        }
        .user-menu-content a {
            display: block;
            padding: 10px;
            text-decoration: none;
            color: black;
        }
        .user-menu-content a:hover {
            background-color: #f1f1f1;
        }
        .user-menu:hover .user-menu-content {
            display: block;
        }
    </style>
</head>
<body>
    <header>
        <% 
            String username = (String) session.getAttribute("username");
            if (username != null) {
        %>
            <div class="user-menu">
                <span>Xin chào, <%= username %></span>
                <div class="user-menu-content">
                    <a href="#">Quản lý hồ sơ</a>
                    <a href="#">Quản lý đơn hàng</a>
                    <a href="#">Quản lý giỏ hàng</a>
                    <a href="#">Quản lý voucher</a>
                    <a href="#">Quản lý phản hồi</a>
                    <a href="logout.jsp">Đăng xuất</a>
                </div>
            </div>
        <% } else { %>
            <a href="login.jsp">Đăng nhập</a> | <a href="register.jsp">Đăng ký</a>
        <% } %>
    </header>
</body>
</html>
