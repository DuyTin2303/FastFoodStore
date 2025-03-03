<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <img src="img/logo1.png" alt="FastFoodStore Logo" width="50" height="50" class="me-2">
    <strong>FastFoodStore</strong>
    <title>Đăng Ký - FastFoodStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Đăng Ký Tài Khoản</h2>
       <form action="register" method="post">

            <div class="mb-3">
                <label class="form-label">Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" id="password" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Nhập lại mật khẩu</label>
                <input type="password" name="confirm_password" id="confirm_password" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Họ và tên</label>
                <input type="text" name="fullname" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Số điện thoại</label>
                <input type="tel" name="phone" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Địa chỉ</label>
                <input type="text" name="address" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Đăng Ký</button>
        </form>
    </div>
    <footer class="footer mt-5 bg-dark text-white py-4">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <h5>CÔNG TY TNHH FASTFOODSTORE VIỆT NAM</h5>
                    <p>Địa chỉ: 123 ABC, Phường XYZ, Quận ABC, TP.HCM</p>
                    <p>Điện thoại: 1900-1533</p>
                    <p>Email: info@fastfoodstore.com</p>
                </div>
                <div class="col-md-4">
                    <h5>LIÊN HỆ</h5>
                    <ul class="list-unstyled">
                        <li><a href="#" class="text-white">Chính sách và quy định chung</a></li>
                        <li><a href="#" class="text-white">Chính sách thanh toán</a></li>
                        <li><a href="#" class="text-white">Chính sách bảo mật thông tin</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
</body>
</html>
